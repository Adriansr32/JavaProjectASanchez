package model;

import java.time.LocalDate;

/**
 * Representa un préstec d'un llibre a un usuari.
 * Conté informació sobre l'identificador del préstec, l'usuari, les dates de préstec i devolució,
 * i l'estat actual del préstec.
 */
public class Loan {
	private int loanId;
	private int userId;
	private LocalDate dateLoan;
	private LocalDate dateReturn;
	private String status;

	/**
	 * Constructor per crear una nova instància de Loan.
	 * @param loanId L'identificador únic del préstec.
	 * @param userId L'identificador de l'usuari que realitza el préstec.
	 * @param dateLoan La data en què es va realitzar el préstec.
	 * @param dateReturn La data prevista o real de devolució del llibre.
	 * @param status L'estat actual del préstec (per exemple, "actiu", "finalitzat").
	 */
	public Loan(int loanId, int userId, LocalDate dateLoan, LocalDate dateReturn, String status) {
		this.loanId = loanId;
		this.userId = userId;
		this.dateLoan = dateLoan;
		this.dateReturn = dateReturn;
		this.status = status;
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
	 * Retorna l'identificador de l'usuari.
	 * @return L'ID de l'usuari.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Estableix l'identificador de l'usuari.
	 * @param userId El nou ID de l'usuari.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Retorna la data de préstec.
	 * @return La data de préstec.
	 */
	public LocalDate getDateLoan() {
		return dateLoan;
	}

	/**
	 * Estableix la data de préstec.
	 * @param dateLoan La nova data de préstec.
	 */
	public void setDateLoan(LocalDate dateLoan) {
		this.dateLoan = dateLoan;
	}

	/**
	 * Retorna la data de devolució.
	 * @return La data de devolució.
	 */
	public LocalDate getDateReturn() {
		return dateReturn;
	}

	/**
	 * Estableix la data de devolució.
	 * @param dateReturn La nova data de devolució.
	 */
	public void setDateReturn(LocalDate dateReturn) {
		this.dateReturn = dateReturn;
	}

	/**
	 * Retorna l'estat del préstec.
	 * @return L'estat del préstec.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Estableix l'estat del préstec.
	 * @param status El nou estat del préstec.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
