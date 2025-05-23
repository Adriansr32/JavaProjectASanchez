package controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StartController {

    private Stage primaryStage;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void showLogin() {
        mainController.showLoginView();
    }

    @FXML
    private void showRegister() {
        mainController.showRegisterView();
    }
}
