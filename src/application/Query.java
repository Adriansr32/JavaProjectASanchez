package application;

import java.sql.*;

public class Query	 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tu_basededatos";
        String user = "tu_usuario";
        String password = "tu_contraseña";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            
            // UPDATE
            String update = "UPDATE usuarios SET nombre = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(update)) {
                stmt.setString(1, "Carlos");
                stmt.setInt(2, 1);
                stmt.executeUpdate();
            }

            // DELETE
            String delete = "DELETE FROM usuarios WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(delete)) {
                stmt.setInt(1, 2);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

