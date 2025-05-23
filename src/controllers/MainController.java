package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class MainController {

    private Stage primaryStage;

    public MainController(Stage stage) {
        this.primaryStage = stage;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de carga");
        alert.setHeaderText("No se pudo cargar la vista");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void showStartView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartView.fxml"));
            Parent root = loader.load();

            StartController startController = loader.getController();
            startController.setMainController(this);
            startController.setStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Bienvenido");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar StartView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
            Parent root = loader.load();

            RegisterController registerController = loader.getController();
            registerController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showBookListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookListView.fxml"));
            Parent root = loader.load();

            BookListController bookListController = loader.getController();
            bookListController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Book List");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar BookListView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void showBookDetailView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetailView.fxml"));
            Parent root = loader.load();

            BookDetailController bookDetailController = loader.getController();
            bookDetailController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Book Detail");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar BookDetailView.fxml: " + e.getMessage());
            e.printStackTrace();
        }	
    }
    
    public void showValorationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ValorationView.fxml"));
            Parent root = loader.load();

            ValorationController valorationController = loader.getController();
            valorationController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Valorations");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar ValorationView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
