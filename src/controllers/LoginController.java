package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.User;
import util.DBUtils;

import java.sql.SQLException;

/**
 * Controlador de la vista de login. Gestiona l'autenticació d'usuaris.
 */
public class LoginController {

    /** Referència al controlador principal. */
    private MainController mainController;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    /**
     * Estableix el controlador principal.
     * @param mainController controlador principal
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Gestiona l'acció de login. Autentica l'usuari i mostra missatges d'error si cal.
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            User user = DBUtils.authenticate(email, password);
            if (user != null) {
                messageLabel.setText("Login exitoso. ¡Bienvenido " + user.getName() + "!");
                mainController.setLoggedUser(user);
                mainController.showBookListView();
            } else {
                messageLabel.setText("Usuario o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Navega a la vista de registre d'usuari.
     */
    @FXML 
    public void goToRegister() {
        mainController.showRegisterView();
    }
}
