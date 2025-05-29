package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Book;
import model.Valoration;
import util.DBUtils;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador per a la vista de detalls d'un llibre.
 * Gestiona la visualització de la informació d'un llibre, així com les accions
 * per veure valoracions, realitzar préstecs i editar el llibre.
 */
public class BookDetailController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView coverImage;

    @FXML 
    private Label isbnLabel;

    @FXML
    private Label editorialLabel;

    @FXML 
    private Label yearLabel;

    @FXML
    private Label isOnLoanLabel;

    @FXML
    private Button editButton;

    private MainController mainController;
    private Book currentBook;

    private String userRole; 

    /**
     * Estableix el controlador principal per permetre la navegació entre vistes.
     * @param mainController El controlador principal de l'aplicació.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Estableix el llibre actual a mostrar i actualitza la interfície d'usuari amb la seva informació.
     * També gestiona la visibilitat del botó d'edició segons el rol de l'usuari.
     * @param book L'objecte Book amb la informació del llibre.
     * @param isOnLoan Indica si el llibre està prestat actualment.
     */
    public void setBook(Book book, boolean isOnLoan) {
        this.currentBook = book;

        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        isbnLabel.setText(book.getIsbn());
        editorialLabel.setText(book.getEditorial());
        yearLabel.setText(book.getYearPublication().toString());
        isOnLoanLabel.setText(isOnLoan ? "Sí" : "No");

        if (book.getCover() != null) {
            coverImage.setImage(new Image(new ByteArrayInputStream(book.getCover())));
        }

        if (userRole != null && (userRole.equals("admin") || userRole.equals("employee"))) {
            editButton.setVisible(true);
        } else {
            editButton.setVisible(false);
        }
    }

    /**
     * Estableix el rol de l'usuari actual.
     * @param role El rol de l'usuari (per exemple, "admin", "employee", "user").
     */
    public void setUserRole(String role) {
        this.userRole = role;
    }

    /**
     * Gestiona l'acció de fer clic al botó de valoracions.
     * Obre la finestra de valoracions per al llibre actual.
     */
    @FXML
    private void handleValorationButtonClick() {
        if (currentBook != null) {
            openValorationWindow(currentBook.getBookId());
        }
    }

    /**
     * Obre la finestra de valoracions i carrega les valoracions del llibre especificat.
     * @param bookId L'ID del llibre del qual es volen veure les valoracions.
     */
    private void openValorationWindow(int bookId) {
        try {
            List<Valoration> valorations = DBUtils.getValorationsForBook(bookId);
            if (valorations != null && mainController != null) {
                mainController.showValorationView(valorations); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestiona l'acció de fer clic al botó de préstec.
     * Obre la finestra de préstec per al llibre actual.
     */
    @FXML
    private void handleLoanButtonClick() {
        if (currentBook != null) {
            openLoanWindow(currentBook.getBookId());
        }
    }

    /**
     * Obre la finestra de préstec per al llibre especificat.
     * @param bookId L'ID del llibre que es vol prestar.
     */
    private void openLoanWindow(int bookId) {
        try {
            Book book = DBUtils.getBookById(bookId);
            if (book != null && mainController != null) {
                mainController.showLoanView(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestiona l'acció de fer clic al botó d'edició.
     * Obre la finestra d'edició per al llibre actual.
     */
    @FXML
    private void handleEditButtonClick() {
        if (currentBook != null && mainController != null) {
            mainController.showEditBookView(currentBook); 
        }
    }
}
