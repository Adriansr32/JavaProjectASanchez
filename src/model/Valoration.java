package model;

/**
 * Representa una valoració d'un llibre feta per un usuari.
 * Conté informació sobre l'identificador de la valoració, l'usuari, el llibre, la puntuació,
 * el nom d'usuari i els comentaris.
 */
public class Valoration {
	private int valorationId;
	private int userId;
	private int bookId;
	private int score;
	private String username;
	private String comments;

	/**
	 * Constructor per crear una nova instància de Valoration.
	 * @param valorationId L'identificador únic de la valoració.
	 * @param userId L'identificador de l'usuari que va fer la valoració.
	 * @param bookId L'identificador del llibre valorat.
	 * @param score La puntuació donada al llibre (per exemple, de 1 a 5).
	 * @param comments Els comentaris o ressenya de la valoració.
	 * @param username El nom d'usuari de la persona que va fer la valoració.
	 */
	public Valoration(int valorationId, int userId, int bookId, int score, String comments, String username) {
		this.valorationId = valorationId;
		this.userId = userId;
		this.bookId = bookId;
		this.score = score;
		this.comments = comments;
		this.username = username;
	}
	
    /**
     * Retorna el nom d'usuari de la persona que va fer la valoració.
     * @return El nom d'usuari.
     */
    public String getUsername() {
        return username;
    }	

	/**
	 * Retorna l'identificador de la valoració.
	 * @return L'ID de la valoració.
	 */
	public int getValorationId() {
		return valorationId;
	}

	/**
	 * Estableix l'identificador de la valoració.
	 * @param valorationId El nou ID de la valoració.
	 */
	public void setValorationId(int valorationId) {
		this.valorationId = valorationId;
	}

	/**
	 * Retorna l'identificador de l'usuari que va fer la valoració.
	 * @return L'ID de l'usuari.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Estableix l'identificador de l'usuari que va fer la valoració.
	 * @param userId El nou ID de l'usuari.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Retorna l'identificador del llibre valorat.
	 * @return L'ID del llibre.
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * Estableix l'identificador del llibre valorat.
	 * @param bookId El nou ID del llibre.
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * Retorna la puntuació de la valoració.
	 * @return La puntuació.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Estableix la puntuació de la valoració.
	 * @param score La nova puntuació.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Retorna els comentaris de la valoració.
	 * @return Els comentaris.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Estableix els comentaris de la valoració.
	 * @param comments Els nous comentaris.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
