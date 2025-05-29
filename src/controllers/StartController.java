package controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controlador per a la vista d'inici de l'aplicació.
 * Permet la navegació cap a les vistes de registre i d'inici de sessió.
 */
public class StartController {

    private Stage primaryStage;
    private MainController mainController;

    /**
     * Estableix el controlador principal per permetre la navegació entre vistes.
     * @param mainController El controlador principal de l'aplicació.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Estableix l'escenari principal (Stage) de l'aplicació.
     * @param stage L'escenari principal de l'aplicació.
     */
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Gestiona l'acció de mostrar la vista d'inici de sessió.
     * Es crida quan l'usuari fa clic al botó d'inici de sessió.
     */
    @FXML
    private void showLogin() {
        mainController.showLoginView();
    }

    /**
     * Gestiona l'acció de mostrar la vista de registre.
     * Es crida quan l'usuari fa clic al botó de registre.
     */
    @FXML
    private void showRegister() {
        mainController.showRegisterView();
    }
}
