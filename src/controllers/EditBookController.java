package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Book;
import model.User;
import util.DBUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controlador per a la vista d'edició de llibres.
 * Permet als usuaris (administradors o empleats) modificar la informació d'un llibre existent,
 * incloent-hi el títol, l'autor, l'editorial, l'any de publicació i la portada.
 */
public class EditBookController {

    @FXML
    private TextField titleField, authorField, editorialField, yearField;

    @FXML
    private ImageView coverPreview;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private Book book;
    private MainController mainController;

    private byte[] newCoverBytes = null;

    /**
     * Estableix el controlador principal per permetre la navegació entre vistes.
     * @param mainController El controlador principal de l'aplicació.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Estableix el llibre que s'ha d'editar i carrega la seva informació als camps del formulari.
     * @param book L'objecte Book que conté la informació del llibre a editar.
     */
    public void setBook(Book book) {
        this.book = book;

        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        editorialField.setText(book.getEditorial());
        yearField.setText(book.getYearPublication().toString());

        if (book.getCover() != null) {
            coverPreview.setImage(new Image(new java.io.ByteArrayInputStream(book.getCover())));
        }
    }

    /**
     * Gestiona l'acció de seleccionar una nova portada per al llibre.
     * Obre un diàleg de selecció de fitxers i previsualitza la imatge seleccionada.
     */
    @FXML
    private void handleChooseCover() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una nova portada");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                newCoverBytes = new FileInputStream(file).readAllBytes();
                coverPreview.setImage(new Image(new FileInputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gestiona l'acció de guardar els canvis del llibre.
     * Actualitza la informació del llibre a la base de dades i tanca la finestra d'edició.
     */
    @FXML
    private void handleSave() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String editorial = editorialField.getText().trim();
        String yearText = yearField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || editorial.isEmpty() || yearText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Tots els camps són obligatoris.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
            if (year < 1000 || year > LocalDate.now().getYear()) {
                showAlert(Alert.AlertType.ERROR, "Error", "L'any de publicació no és vàlid.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "L'any de publicació ha de ser un número vàlid.");
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setEditorial(editorial);
        book.setYearPublication(LocalDate.of(year, 1, 1)); 
        if (newCoverBytes != null) {
            book.setCover(newCoverBytes);
        }

        User user = mainController.getLoggedUser();
        if (user == null || (!"admin".equals(user.getRole()) && !"employee".equals(user.getRole()))) {
            showAlert(Alert.AlertType.ERROR, "Permís denegat", "Només els administradors poden editar llibres.");
            return;
        }

        try {
            DBUtils.updateBook(book, user.getRole());
            showAlert(Alert.AlertType.INFORMATION, "Èxit", "Llibre actualitzat correctament.");
            ((Stage) saveButton.getScene().getWindow()).close();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No s'ha pogut actualitzar el llibre.");
            showAlert(Alert.AlertType.ERROR, "Error", "Error en actualitzar el llibre: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        User user = mainController.getLoggedUser();
        if (user == null || !"admin".equals(user.getRole())) {
            showAlert(Alert.AlertType.ERROR, "Permís denegat", "Només els administradors poden eliminar llibres.");
            return;
        }
        if (book == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se ha seleccionado ningún libro para eliminar.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmació d'eliminació");
        alert.setHeaderText(null);
        alert.setContentText("Estàs segur que vols eliminar aquest llibre?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.YES) {
                try {
                    boolean deleted = DBUtils.deleteBook(book.getBookId(), user.getRole());
                    if (deleted) {
                        showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Éxito", "Libro eliminado correctamente.");
                        ((Stage) deleteButton.getScene().getWindow()).close();
                    } else {
                        showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "No se pudo eliminar el libro.");
                    }
                } catch (Exception e) {
                    showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "Error en eliminar el llibre: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
