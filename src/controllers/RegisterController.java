package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import model.User;
import util.DBUtils;

import java.sql.SQLException;

public class RegisterController {

    private MainController mainController;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("Por favor, rellena todos los campos.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        try {
            boolean success = DBUtils.registerUser(name, email, password);
            if (success) {
                messageLabel.setText("Registro exitoso. Ahora puedes iniciar sesión.");
                mainController.showLoginView();
            } else {
                messageLabel.setText("Error: El usuario ya existe o no se pudo registrar.");
            }
        } catch (SQLException e) {
            messageLabel.setText("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
    }
    @FXML 
    public void goToLogin() {
    	mainController.showLoginView();
    }
}
