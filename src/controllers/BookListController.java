package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Book;

public class BookListController {

    private MainController mainController;

    @FXML
    private ListView<Book> bookListView;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        bookListView.setItems(books);
        bookListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selected = bookListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    mainController.showBookDetailView(); 
                }
            }
        });
    }

    public void loadBooks(ObservableList<Book> bookList) {
        books.setAll(bookList);
    }
}
