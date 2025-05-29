package controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Book;
import model.User;
import util.DBUtils;

/**
 * Controlador per a la vista de gestió de préstecs.
 * Permet als usuaris (empleats o administradors) registrar un préstec d'un llibre.
 */
public class LoanController {

	private Book currentBook;
    private MainController mainController; 
    @FXML
    private DatePicker datePicker;
    private boolean isOnLoan;

    /**
     * Estableix el controlador principal per permetre la navegació entre vistes.
     * @param mainController El controlador principal de l'aplicació.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Estableix el llibre actual per al qual es gestionarà el préstec.
     * @param book L'objecte Book del llibre a prestar.
     */
    public void setLoan(Book book) {
    	currentBook = book;
    }
    
    /**
     * Indica si el llibre actual ja està prestat.
     * @param isOnLoan True si el llibre està prestat, false altrament.
     */
    public void isOnLoan(boolean isOnLoan) {
    	this.isOnLoan = isOnLoan;
    }
    
    /**
     * Gestiona l'acció de fer clic al botó de préstec.
     * Valida les dades, crea el préstec a la base de dades si el llibre no està prestat,
     * i mostra missatges d'èxit o error a l'usuari.
     */
    public void handleLoanButton() {
        Book book = currentBook;
        LocalDate date = datePicker.getValue();    
        User user = mainController.getLoggedUser(); 
        
        if (book == null || user == null || date == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos inválidos");
            alert.setContentText("Verifica que el libro, usuario y fecha estén seleccionados.");
            alert.showAndWait();
            return;
        }

        try {
            if (!isOnLoan) {
                boolean loanCreated = DBUtils.createLoan(user.getUserId(), book.getBookId(), date);
                if (loanCreated) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Préstamo exitoso");
                    alert.setHeaderText(null);
                    alert.setContentText("¡El préstamo se ha registrado correctamente!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error en el préstamo");
                    alert.setHeaderText(null);
                    alert.setContentText("No se pudo registrar el préstamo.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Libro en préstamo");
                alert.setHeaderText(null);
                alert.setContentText("Este libro ya está en préstamo.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error al registrar el préstamo.");
            alert.showAndWait();
        }
    }

}
