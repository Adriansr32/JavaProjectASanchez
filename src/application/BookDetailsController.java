package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.Blob;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.sql.*;

public class BookDetailsController {

    @FXML
    private Label titleLabel;
    
    @FXML
    private Label authorLabel;
    
    @FXML
    private Label isbnLabel;
    
    @FXML
    private Label editorialLabel;
    
    @FXML
    private Label yearPublicationLabel;
    
    @FXML
    private ImageView coverImageView;
    
    
    

    public void loadBookDetails(int bookId) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT title, author, isbn, editorial, year_publication, cover  FROM Book WHERE book_id = ?");
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	Blob blob = rs.getBlob("cover");
            	if (blob != null) {
            	    InputStream inputStream = blob.getBinaryStream();
            	    Image image = new Image(inputStream);
            	    coverImageView.setImage(image);
            	}
                titleLabel.setText("Título: " + rs.getString("title"));
                authorLabel.setText("Autor: " + rs.getString("author"));
                isbnLabel.setText("Isbn:" + rs.getString("isbn"));
                editorialLabel.setText("Editorial: " + rs.getString("editorial"));
                yearPublicationLabel.setText("Año de la publicacion: " + rs.getString("year_publication"));
                
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insert() {
    	
        String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Juan Pérez");
            stmt.setString(2, "juan@example.com");

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("¡Usuario insertado correctamente!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
