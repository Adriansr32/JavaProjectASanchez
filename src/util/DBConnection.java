package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitària per gestionar la connexió a la base de dades MySQL.
 * Proporciona mètodes per obtenir i tancar la connexió a la base de dades.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/digital_library";
    private static final String USER = "gestor";
    private static final String PASSWORD = "gestor123";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver no encontrado", e);
        }
    }

    /**
     * Obté una instància de la connexió a la base de dades.
     * Si la connexió no existeix o està tancada, en crea una de nova.
     * @return Una connexió a la base de dades.
     * @throws RuntimeException Si no es pot connectar a la base de dades.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar con la base de datos", e);
        }
        return connection;
    }

    /**
     * Tanca la connexió a la base de dades si està oberta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
