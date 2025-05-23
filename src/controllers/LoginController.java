package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.User;
import util.DBUtils;

import java.sql.SQLException;

public class LoginController {

    private MainController mainController;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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

    @FXML 
    public void goToRegister() {
        mainController.showRegisterView();
    }
}
