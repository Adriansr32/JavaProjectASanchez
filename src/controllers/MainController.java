package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Book;
import model.User;
import model.Valoration;
import util.DBUtils;

import java.io.IOException;
import java.util.List;

/**
 * Controlador principal de l'aplicació. Gestiona la navegació entre vistes i l'usuari connectat.
 */
public class MainController {
    /** Usuari actualment connectat. */
    private User loggedUser;

    /** Escenari principal de JavaFX. */
    private Stage primaryStage;

    /**
     * Estableix l'usuari connectat.
     * @param user L'usuari a establir
     */
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    /**
     * Retorna l'usuari connectat.
     * @return Usuari connectat
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Constructor del controlador principal.
     * @param stage Escenari principal
     */
    public MainController(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Mostra una alerta d'error amb el missatge proporcionat.
     * @param message Missatge d'error
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error de càrrega");
        alert.setHeaderText("No s'ha pogut carregar la vista");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mostra la vista d'inici.
     */
    public void showStartView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartView.fxml"));
            Parent root = loader.load();

            StartController startController = loader.getController();
            startController.setMainController(this);
            startController.setStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Benvingut");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error en carregar StartView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de registre d'usuari.
     */
    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
            Parent root = loader.load();

            RegisterController registerController = loader.getController();
            registerController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Registre");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error en carregar RegisterView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de login.
     */
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
            showErrorAlert("Error en carregar LoginView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de llista de llibres.
     */
    public void showBookListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookListView.fxml"));
            Parent root = loader.load();

            BookListController bookListController = loader.getController();
            bookListController.setMainController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Llista de llibres");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error en carregar BookListView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de detall d'un llibre.
     * @param book Llibre a mostrar
     */
    public void showBookDetailView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetailView.fxml"));
            Parent root = loader.load();
            BookDetailController controller = loader.getController();
            controller.setMainController(this);
            boolean isOnLoan = DBUtils.isBookOnLoan(book.getBookId());
            User user = getLoggedUser();
            controller.setUserRole(user.getRole());
            controller.setBook(book, isOnLoan);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Detall del llibre");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de valoracions d'un llibre.
     * @param book Llista de valoracions
     */
    public void showValorationView(List<Valoration> book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ValorationView.fxml"));
            Parent root = loader.load();

            ValorationController valorationController = loader.getController();
            valorationController.setMainController(this);
            valorationController.setValorations(book);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Valoracions");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error en carregar ValorationView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista de préstec d'un llibre.
     * @param book Llibre a prestar
     */
    public void showLoanView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoanView.fxml"));
            Parent root = loader.load();

            LoanController loanController = loader.getController();
            loanController.setMainController(this);
            loanController.setLoan(book);
            boolean isOnLoan = DBUtils.isBookOnLoan(book.getBookId());
            loanController.isOnLoan(isOnLoan);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Préstec");
            primaryStage.show();
        } catch (IOException e) {
            showErrorAlert("Error en carregar LoanView.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mostra la vista d'edició d'un llibre.
     * @param book Llibre a editar
     */
    public void showEditBookView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditBookView.fxml"));
            Parent editView = loader.load();

            EditBookController controller = loader.getController();
            controller.setMainController(this);
            controller.setBook(book);

            Stage stage = new Stage();
            stage.setTitle("Editar llibre");
            stage.setScene(new Scene(editView));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
