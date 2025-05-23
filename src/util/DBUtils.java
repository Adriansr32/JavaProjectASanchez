package util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class DBUtils {

    public static User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT user_id, name, password_hash, role FROM Users WHERE email = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password_hash");
                    String inputHash = Encrypt.simpleHash(password);
                    if (inputHash.equals(hash)) {
                        return new User(
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            email,
                            hash,
                            rs.getString("role")
                        );
                    }
                }
            }
        }
        return null;
    }

    public static boolean registerUser(String name, String email, String password) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        String insertSql = "INSERT INTO Users (name, email, password_hash) VALUES (?, ?, ?)";
        String hash = Encrypt.simpleHash(password);

        try (Connection c = DBConnection.getConnection();
             PreparedStatement checkPs = c.prepareStatement(checkSql)) {

            checkPs.setString(1, email);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            try (PreparedStatement insertPs = c.prepareStatement(insertSql)) {
                insertPs.setString(1, name);
                insertPs.setString(2, email);
                insertPs.setString(3, hash);
                return insertPs.executeUpdate() == 1;
            }
        }
    }


    public static List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT book_id, title, author, isbn, cover, editorial, year_publication FROM Book";
        List<Book> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getBytes("cover"),
                    rs.getString("editorial"),
                    rs.getDate("year_publication").toLocalDate()
                ));
            }
        }
        return list;
    }

    public static Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM Book WHERE book_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getBytes("cover"),
                        rs.getString("editorial"),
                        rs.getDate("year_publication").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    public static boolean createLoan(int userId, int bookId) throws SQLException {
        String insertLoan = "INSERT INTO Loan (user_id, date_loan) VALUES (?, ?)";
        String insertBookLoan = "INSERT INTO BookLoan (loan_id, book_id) VALUES (?, ?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(insertLoan, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setInt(1, userId);
                ps1.setDate(2, Date.valueOf(LocalDate.now()));
                ps1.executeUpdate();
                try (ResultSet keys = ps1.getGeneratedKeys()) {
                    if (keys.next()) {
                        int loanId = keys.getInt(1);
                        try (PreparedStatement ps2 = c.prepareStatement(insertBookLoan)) {
                            ps2.setInt(1, loanId);
                            ps2.setInt(2, bookId);
                            ps2.executeUpdate();
                        }
                        c.commit();
                        return true;
                    }
                }
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
        return false;
    }

    public static boolean hasUserLoaned(int userId, int bookId) throws SQLException {
        String sql = """
            SELECT 1
              FROM Loan L
              JOIN BookLoan BL ON L.loan_id = BL.loan_id
             WHERE L.user_id = ? AND BL.book_id = ?
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    public static boolean isBookOnLoan(int bookId) {
        String sql = "SELECT COUNT(*) FROM BookLoan bl JOIN Loan l ON bl.loan_id = l.loan_id WHERE bl.book_id = ? AND l.status = 'active'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static List<Valoration> getValorationsForBook(int bookId) throws SQLException {
        String sql = """
            SELECT v.valoration_id, v.user_id, u.name, v.score, v.comments
            FROM Valoration v
            JOIN Users u ON v.user_id = u.user_id
            WHERE v.book_id = ?
        """;

        List<Valoration> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Valoration valoration = new Valoration(
                        rs.getInt("valoration_id"),
                        rs.getInt("user_id"),
                        bookId,
                        rs.getInt("score"),
                        rs.getString("comments"),
                        rs.getString("name") 
                    );
                    list.add(valoration);
                }
            }
        }
        return list;
    }

    public static boolean createValoration(int userId, int bookId, int score, String comments) throws SQLException {
        String sql = "INSERT INTO Valoration (user_id, book_id, score, comments) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, score);
            ps.setString(4, comments);
            return ps.executeUpdate() == 1;
        }
    }

    
}

