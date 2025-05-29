package model;

import java.time.LocalDateTime;

/**
 * Representa un registre d'activitat (log) relacionat amb un préstec.
 * Conté informació sobre l'identificador del registre, el préstec associat, l'usuari,
 * l'acció realitzada i la data/hora del registre.
 */
public class LoanLog {
	private int logId;
	private int loanId;
	private int userId;
	private String action;
	private LocalDateTime logTime;

	/**
	 * Constructor per crear una nova instància de LoanLog.
	 * @param logId L'identificador únic del registre.
	 * @param loanId L'identificador del préstec associat a aquest registre.
	 * @param userId L'identificador de l'usuari que va realitzar l'acció.
	 * @param action La descripció de l'acció realitzada (per exemple, "préstec creat", "préstec retornat").
	 * @param logTime La data i hora en què es va registrar l'acció.
	 */
	public LoanLog(int logId, int loanId, int userId, String action, LocalDateTime logTime) {
		this.logId = logId;
		this.loanId = loanId;
		this.userId = userId;
		this.action = action;
		this.logTime = logTime;
	}

	/**
	 * Retorna l'identificador del registre.
	 * @return L'ID del registre.
	 */
	public int getLogId() {
		return logId;
	}

	/**
	 * Estableix l'identificador del registre.
	 * @param logId El nou ID del registre.
	 */
	public void setLogId(int logId) {
		this.logId = logId;
	}

	/**
	 * Retorna l'identificador del préstec associat.
	 * @return L'ID del préstec.
	 */
	public int getLoanId() {
		return loanId;
	}

	/**
	 * Estableix l'identificador del préstec associat.
	 * @param loanId El nou ID del préstec.
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	/**
	 * Retorna l'identificador de l'usuari que va realitzar l'acció.
	 * @return L'ID de l'usuari.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Estableix l'identificador de l'usuari que va realitzar l'acció.
	 * @param userId El nou ID de l'usuari.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Retorna la descripció de l'acció realitzada.
	 * @return L'acció.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Estableix la descripció de l'acció realitzada.
	 * @param action La nova acció.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Retorna la data i hora del registre.
	 * @return La data i hora del registre.
	 */
	public LocalDateTime getLogTime() {
		return logTime;
	}

	/**
	 * Estableix la data i hora del registre.
	 * @param logTime La nova data i hora del registre.
	 */
	public void setLogTime(LocalDateTime logTime) {
		this.logTime = logTime;
	}
}
