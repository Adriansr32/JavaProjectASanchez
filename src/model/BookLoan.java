package model;

/**
 * Representa la relació entre un préstec i un llibre.
 * Aquesta classe s'utilitza per gestionar els llibres associats a un préstec específic.
 */
public class BookLoan {
	private int loanId;
	private int bookId;

	/**
	 * Constructor per crear una nova instància de BookLoan.
	 * @param loanId L'identificador únic del préstec.
	 * @param bookId L'identificador únic del llibre.
	 */
	public BookLoan(int loanId, int bookId) {
		this.loanId = loanId;
		this.bookId = bookId;
	}

	/**
	 * Retorna l'identificador del préstec.
	 * @return L'ID del préstec.
	 */
	public int getLoanId() {
		return loanId;
	}

	/**
	 * Estableix l'identificador del préstec.
	 * @param loanId El nou ID del préstec.
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	/**
	 * Retorna l'identificador del llibre.
	 * @return L'ID del llibre.
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * Estableix l'identificador del llibre.
	 * @param bookId El nou ID del llibre.
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}
