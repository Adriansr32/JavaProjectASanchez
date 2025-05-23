package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.Valoration;

import java.util.List;

public class ValorationController {

    private MainController mainController;

    @FXML
    private ListView<Valoration> valorationListView;

    @FXML
    private ChoiceBox<Integer> scoreChoiceBox;

    @FXML
    private TextArea commentsTextArea;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        scoreChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        // Mostrar valoraciones con formato personalizado
        valorationListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Valoration valoration, boolean empty) {
                super.updateItem(valoration, empty);
                if (empty || valoration == null) {
                    setText(null);
                } else {
                    setText("Usuario: " + valoration.getUsername() +
                            "\nPuntuación: " + valoration.getScore() +
                            "\nComentario: " + valoration.getComments());
                }
            }
        });
    }

    public void setValorations(List<Valoration> valorations) {
        valorationListView.getItems().setAll(valorations);
    }

    @FXML
    private void handleSubmitValoration() {
        Integer score = scoreChoiceBox.getValue();
        String comment = commentsTextArea.getText();

        if (score == null || comment == null || comment.trim().isEmpty()) {
            showAlert("Error", "Debes introducir una puntuación y un comentario.");
            return;
        }

        showAlert("Éxito", "Valoración enviada correctamente.");
        scoreChoiceBox.setValue(null);
        commentsTextArea.clear();
    }

    @FXML
    private void handleBack() {
        mainController.showBookListView();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
