package application;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdrianController {

	@FXML
	private FlowPane bookContainer;

	@FXML
	public void initialize() {
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT book_id, title, cover FROM Book")) {

			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				Blob blob = rs.getBlob("cover");
				if (blob != null) {
					InputStream inputStream = blob.getBinaryStream();
				
					VBox bookBox = new VBox(5);
					ImageView coverImage = new ImageView(new Image(inputStream));
					coverImage.setFitWidth(150);
					coverImage.setFitHeight(200);
					coverImage.setPreserveRatio(false);
					coverImage.setSmooth(true);

					Button bookBtn = new Button(title);
					bookBtn.setMaxWidth(150);
					bookBtn.setOnAction(e -> openDetailsWindow(id));

					bookBox.getChildren().addAll(coverImage, bookBtn);
					bookContainer.getChildren().add(bookBox);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void openDetailsWindow(int bookId) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookDetails.fxml"));
			Scene scene = new Scene(loader.load());

			BookDetailsController controller = loader.getController();
			controller.loadBookDetails(bookId);

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Detalles del Libro");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
