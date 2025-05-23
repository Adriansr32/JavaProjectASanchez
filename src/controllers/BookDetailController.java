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

 

    private MainController mainController;
    private Book currentBook;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setBook(Book book, boolean isOnLoan) {
        currentBook = book;

        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        isbnLabel.setText(book.getIsbn());
        editorialLabel.setText(book.getEditorial());
        yearLabel.setText(book.getYearPublication().toString());
        isOnLoanLabel.setText(isOnLoan ? "Sí" : "No");

        if (book.getCover() != null) {
            coverImage.setImage(new Image(new ByteArrayInputStream(book.getCover())));
        }
    }

    @FXML
    private void handleValorationButtonClick() {
        if (currentBook != null) {
            openValorationWindow(currentBook.getBookId());
        }
    }

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
    
    @FXML
    private void handleLoanButtonClick(){
        if (currentBook != null) {
            openLoanWindow(currentBook.getBookId());
        }
    }
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
}
