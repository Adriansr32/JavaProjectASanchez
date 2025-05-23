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

public class BookListController {

    @FXML
    private FlowPane bookContainer;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        loadBooks();
    }

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
