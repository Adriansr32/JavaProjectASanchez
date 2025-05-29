package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.User;
import model.Valoration;
import util.DBUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador per a la vista de valoracions d'un llibre.
 * Permet als usuaris veure les valoracions existents i afegir-ne de noves.
 */
public class ValorationController {

	private MainController mainController;

	@FXML
	private ListView<Valoration> valorationListView;

	@FXML
	private ChoiceBox<Integer> scoreChoiceBox;

	@FXML
	private TextArea commentsTextArea;

	private Valoration valorations;

	/**
	 * Estableix el controlador principal per permetre la navegació entre vistes.
	 * @param mainController El controlador principal de l'aplicació.
	 */
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Mètode d'inicialització que es crida automàticament després que el FXML hagi estat carregat.
	 * Configura el ChoiceBox per a la puntuació i el CellFactory per a la ListView de valoracions.
	 */
	@FXML
	public void initialize() {
		scoreChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

		valorationListView.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(Valoration valoration, boolean empty) {
				super.updateItem(valoration, empty);
				valorations = valoration;
				if (empty || valoration == null) {
					setText(null);
				} else {
					setText("Usuario: " + valoration.getUsername() + "\nPuntuación: " + valoration.getScore()
							+ "\nComentario: " + valoration.getComments());
				}
			}
		});
	}

	/**
	 * Estableix la llista de valoracions a mostrar a la ListView.
	 * @param valorations La llista d'objectes Valoration.
	 */
	public void setValorations(List<Valoration> valorations) {
		valorationListView.getItems().setAll(valorations);
	}

	/**
	 * Gestiona l'acció de enviar una nova valoració.
	 * Valida la puntuació i el comentari, i si són vàlids, registra la valoració a la base de dades.
	 */
	@FXML
	private void handleSubmitValoration() {
		Integer score = scoreChoiceBox.getValue();
		String comment = commentsTextArea.getText();

		if (score == null || comment == null || comment.trim().isEmpty()) {
			showAlert("Error", "Debes introducir una puntuación y un comentario.");
			return;
		}

		User user = mainController.getLoggedUser();
		try {
			if (DBUtils.hasUserLoaned(user.getUserId(), valorations.getBookId())) {
				DBUtils.createValoration(user.getUserId(), valorations.getBookId(), score, comment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		showAlert("Éxito", "Valoración enviada correctamente.");
		scoreChoiceBox.setValue(null);
		commentsTextArea.clear();
	}

	/**
	 * Gestiona l'acció de tornar a la vista de llista de llibres.
	 */
	@FXML
	private void handleBack() {
		mainController.showBookListView();
	}

	/**
	 * Mostra una finestra d'alerta amb un títol i un missatge donats.
	 * @param title El títol de la finestra d'alerta.
	 * @param message El missatge a mostrar a l'alerta.
	 */
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
