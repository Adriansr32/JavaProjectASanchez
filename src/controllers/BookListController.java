package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.Book;
import util.DBUtils;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador per a la vista de llista de llibres.
 * S'encarrega de carregar i mostrar tots els llibres disponibles a l'aplicació,
 * així com de gestionar la navegació als detalls de cada llibre.
 */
public class BookListController {

    @FXML
    private FlowPane bookContainer;

    private MainController mainController;

    /**
     * Estableix el controlador principal per permetre la navegació entre vistes.
     * @param mainController El controlador principal de l'aplicació.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Mètode d'inicialització que es crida automàticament després que el FXML hagi estat carregat.
     * Carrega els llibres a la interfície.
     */
    @FXML
    public void initialize() {
        loadBooks();
    }

    /**
     * Carrega tots els llibres de la base de dades i els mostra a la interfície d'usuari.
     * Cada llibre es presenta amb la seva portada i un botó per veure'n els detalls.
     */
    private void loadBooks() {
        try {
            List<Book> books = DBUtils.getAllBooks();
            for (Book book : books) {
                VBox bookBox = new VBox(5);
                bookBox.setStyle("-fx-alignment: center;");

                byte[] coverBytes = book.getCover();
                if (coverBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(coverBytes));
                    ImageView coverImage = new ImageView(image);
                    coverImage.setFitWidth(120);
                    coverImage.setFitHeight(160);
                    coverImage.setPreserveRatio(true);
                    bookBox.getChildren().add(coverImage);
                }

                Button bookBtn = new Button(book.getTitle());
                bookBtn.setMaxWidth(120);
                bookBtn.setOnAction(e -> openDetailsWindow(book.getBookId()));
                bookBox.getChildren().add(bookBtn);

                bookContainer.getChildren().add(bookBox);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Obre la finestra de detalls per a un llibre específic.
     * @param bookId L'ID del llibre del qual es volen veure els detalls.
     */
    private void openDetailsWindow(int bookId) {
        try {
            Book book = DBUtils.getBookById(bookId);
            if (book != null && mainController != null) {
                mainController.showBookDetailView(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
