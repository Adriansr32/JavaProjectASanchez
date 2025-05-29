package util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 * Classe utilitària per a operacions de base de dades.
 * Proporciona mètodes per autenticar usuaris, registrar nous usuaris, gestionar llibres, préstecs i valoracions.
 */
public class DBUtils {

	/**
	 * Autentica un usuari basant-se en el seu correu electrònic i contrasenya.
	 * @param email El correu electrònic de l'usuari.
	 * @param password La contrasenya de l'usuari.
	 * @return L'objecte Usuari si l'autenticació és exitosa, altrament null.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static User authenticate(String email, String password) throws SQLException {
		String sql = "SELECT user_id, name, password_hash, role FROM Users WHERE email = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String hash = rs.getString("password_hash");
					String inputHash = Encrypt.hashPassword(password);
					if (inputHash.equals(hash)) {
						return new User(rs.getInt("user_id"), rs.getString("name"), email, hash, rs.getString("role"));
					}
				}
			}
		}
		return null;
	}

	/**
	 * Registra un nou usuari a la base de dades.
	 * @param name El nom de l'usuari.
	 * @param email El correu electrònic de l'usuari (ha de ser únic).
	 * @param password La contrasenya de l'usuari.
	 * @return true si el registre és exitós, false si el correu electrònic ja existeix.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean registerUser(String name, String email, String password) throws SQLException {
		String checkSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
		String insertSql = "INSERT INTO Users (name, email, password_hash) VALUES (?, ?, ?)";
		String hash = Encrypt.hashPassword(password);

		try (Connection c = DBConnection.getConnection(); PreparedStatement checkPs = c.prepareStatement(checkSql)) {

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

	/**
	 * Obté una llista de tots els llibres disponibles a la base de dades.
	 * @return Una llista d'objectes Book.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static List<Book> getAllBooks() throws SQLException {
		String sql = "SELECT book_id, title, author, isbn, cover, editorial, year_publication FROM Book";
		List<Book> list = new ArrayList<>();
		try (Connection c = DBConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
						rs.getString("isbn"), rs.getBytes("cover"), rs.getString("editorial"),
						rs.getDate("year_publication").toLocalDate()));
			}
		}
		return list;
	}

	/**
	 * Obté un llibre per la seva ID.
	 * @param bookId La ID del llibre.
	 * @return L'objecte Book si es troba, altrament null.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static Book getBookById(int bookId) throws SQLException {
		String sql = "SELECT * FROM Book WHERE book_id = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, bookId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
							rs.getString("isbn"), rs.getBytes("cover"), rs.getString("editorial"),
							rs.getDate("year_publication").toLocalDate());
				}
			}
		}
		return null;
	}

	/**
	 * Crea un nou préstec a la base de dades.
	 * @param userId La ID de l'usuari que realitza el préstec.
	 * @param bookId La ID del llibre que es presta.
	 * @param data_return La data de retorn prevista del llibre.
	 * @return true si el préstec es crea exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean createLoan(int userId, int bookId, LocalDate data_return) throws SQLException {
		String insertLoan = "INSERT INTO Loan (user_id, date_loan, date_return) VALUES (?, ?, ?)";
		String insertBookLoan = "INSERT INTO BookLoan (loan_id, book_id) VALUES (?, ?)";
		try (Connection c = DBConnection.getConnection()) {
			c.setAutoCommit(false);
			try (PreparedStatement ps1 = c.prepareStatement(insertLoan, Statement.RETURN_GENERATED_KEYS)) {
				ps1.setInt(1, userId);
				ps1.setDate(2, Date.valueOf(LocalDate.now()));
				ps1.setDate(3, Date.valueOf(data_return));
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

	/**
	 * Comprova si un usuari ja ha prestat un llibre específic.
	 * @param userId La ID de l'usuari.
	 * @param bookId La ID del llibre.
	 * @return true si l'usuari ha prestat el llibre, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean hasUserLoaned(int userId, int bookId) throws SQLException {
		String sql = """
				    SELECT 1
				      FROM Loan L
				      JOIN BookLoan BL ON L.loan_id = BL.loan_id
				     WHERE L.user_id = ? AND BL.book_id = ?
				""";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	/**
	 * Comprova si un llibre està actualment prestat.
	 * @param bookId La ID del llibre.
	 * @return true si el llibre està prestat, false altrament.
	 */
	public static boolean isBookOnLoan(int bookId) {
		String sql = "SELECT COUNT(*) FROM BookLoan bl JOIN Loan l ON bl.loan_id = l.loan_id WHERE bl.book_id = ? AND l.status = 'active'";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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

	/**
	 * Afegeix un nou llibre a la base de dades.
	 * @param book L'objecte Book a afegir.
	 * @return true si el llibre s'afegeix exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean addBook(Book book) throws SQLException {
		String sql = "INSERT INTO Book (title, author, isbn, cover, editorial, year_publication) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getIsbn());
			ps.setBytes(4, book.getCover());
			ps.setString(5, book.getEditorial());
			ps.setDate(6, Date.valueOf(book.getYearPublication()));
			return ps.executeUpdate() == 1;
		}
	}

	/**
	 * Obté totes les valoracions per a un llibre específic.
	 * @param bookId La ID del llibre.
	 * @return Una llista d'objectes Valoration.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static List<Valoration> getValorationsForBook(int bookId) throws SQLException {
		String sql = """
				    SELECT v.valoration_id, v.user_id, u.name, v.score, v.comments
				    FROM Valoration v
				    JOIN Users u ON v.user_id = u.user_id
				    WHERE v.book_id = ?
				""";

		List<Valoration> list = new ArrayList<>();
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, bookId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Valoration valoration = new Valoration(rs.getInt("valoration_id"), rs.getInt("user_id"), bookId,
							rs.getInt("score"), rs.getString("comments"), rs.getString("name"));
					list.add(valoration);
				}
			}
		}
		return list;
	}

	/**
	 * Crea una nova valoració per a un llibre.
	 * @param userId La ID de l'usuari que fa la valoració.
	 * @param bookId La ID del llibre valorat.
	 * @param score La puntuació de la valoració (1-5).
	 * @param comments Els comentaris de la valoració.
	 * @return true si la valoració es crea exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean createValoration(int userId, int bookId, int score, String comments) throws SQLException {
		String sql = "INSERT INTO Valoration (user_id, book_id, score, comments) VALUES (?, ?, ?, ?)";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.setInt(2, bookId);
			ps.setInt(3, score);
			ps.setString(4, comments);
			return ps.executeUpdate() == 1;
		}
	}

	/**
	 * Actualitza la informació d'un llibre existent.
	 * @param book L'objecte Book amb la informació actualitzada.
	 * @param userRole El rol de l'usuari que realitza l'actualització (només 'admin' pot actualitzar).
	 * @return true si el llibre s'actualitza exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades o si l'usuari no té permís.
	 */
	public static boolean updateBook(Book book, String userRole) throws SQLException {
		if (!"admin".equalsIgnoreCase(userRole))
			throw new SQLException("Permís denegat: només admins poden actualitzar llibres.");
		String sql = "UPDATE Book SET title = ?, author = ?, isbn = ?, cover = ?, editorial = ?, year_publication = ? WHERE book_id = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getIsbn());
			ps.setBytes(4, book.getCover());
			ps.setString(5, book.getEditorial());
			ps.setDate(6, Date.valueOf(book.getYearPublication()));
			ps.setInt(7, book.getBookId());
			return ps.executeUpdate() > 0;
		}
	}

	/**
	 * Elimina un llibre de la base de dades.
	 * @param bookId La ID del llibre a eliminar.
	 * @param userRole El rol de l'usuari que realitza l'eliminació (només 'admin' pot eliminar).
	 * @return true si el llibre s'elimina exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades o si l'usuari no té permís.
	 */
	public static boolean deleteBook(int bookId, String userRole) throws SQLException {
		String sql = "DELETE FROM Book WHERE book_id = ?";
		if (!"admin".equalsIgnoreCase(userRole))
			throw new SQLException("Permís denegat: només admins poden eliminar llibres.");
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, bookId);
			return ps.executeUpdate() > 0;
		}
	}

	/**
	 * Obté una llista de tots els préstecs actius.
	 * @return Una llista d'objectes Loan.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static List<Loan> getAllLoans() throws SQLException {
		String sql = "SELECT * FROM Loan";
		List<Loan> loans = new ArrayList<>();
		try (Connection c = DBConnection.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				loans.add(new Loan(rs.getInt("loan_id"), rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date_loan")), LocalDate.parse(rs.getString("date_return")),
						rs.getString("status")));
			}
		}
		return loans;
	}

	/**
	 * Actualitza l'estat d'un préstec a 'returned'.
	 * @param loanId La ID del préstec a actualitzar.
	 * @return true si el préstec s'actualitza exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean returnLoan(int loanId) throws SQLException {
		String sql = "UPDATE Loan SET status = 'returned' WHERE loan_id = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, loanId);
			return ps.executeUpdate() > 0;
		}
	}

	/**
	 * Obté una llista de tots els usuaris registrats.
	 * @return Una llista d'objectes User.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static List<User> getAllUsers() throws SQLException {
		String sql = "SELECT user_id, name, email, role FROM Users";
		List<User> users = new ArrayList<>();
		try (Connection c = DBConnection.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				users.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), null, // Password hash no es necesario
						rs.getString("role")));
			}
		}
		return users;
	}

	/**
	 * Actualitza el rol d'un usuari.
	 * @param userId La ID de l'usuari a actualitzar.
	 * @param newRole El nou rol de l'usuari.
	 * @return true si el rol s'actualitza exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean updateUserRole(int userId, String newRole) throws SQLException {
		String sql = "UPDATE Users SET role = ? WHERE user_id = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, newRole);
			ps.setInt(2, userId);
			return ps.executeUpdate() > 0;
		}
	}

	/**
	 * Elimina un usuari de la base de dades.
	 * @param userId La ID de l'usuari a eliminar.
	 * @return true si l'usuari s'elimina exitosament, false altrament.
	 * @throws SQLException Si ocorre un error d'accés a la base de dades.
	 */
	public static boolean deleteUser(int userId) throws SQLException {
		String sql = "DELETE FROM Users WHERE user_id = ?";
		try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, userId);
			return ps.executeUpdate() > 0;
		}
	}
}
