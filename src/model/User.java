package model;

/**
 * Representa un usuari de l'aplicació.
 * Conté informació sobre l'identificador de l'usuari, nom, correu electrònic, hash de la contrasenya i rol.
 */
public class User {
	private int userId;
	private String name;
	private String email;
	private String passwordHash;
	private String role;

	/**
	 * Constructor per crear una nova instància de User.
	 * @param userId L'identificador únic de l'usuari.
	 * @param name El nom de l'usuari.
	 * @param email El correu electrònic de l'usuari (únic).
	 * @param passwordHash El hash de la contrasenya de l'usuari.
	 * @param role El rol de l'usuari (per exemple, "admin", "employee", "user").
	 */
	public User(int userId, String name, String email, String passwordHash, String role) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
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
	 * Retorna el nom de l'usuari.
	 * @return El nom de l'usuari.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Estableix el nom de l'usuari.
	 * @param name El nou nom de l'usuari.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retorna el correu electrònic de l'usuari.
	 * @return El correu electrònic de l'usuari.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Estableix el correu electrònic de l'usuari.
	 * @param email El nou correu electrònic de l'usuari.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorna el hash de la contrasenya de l'usuari.
	 * @return El hash de la contrasenya.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Estableix el hash de la contrasenya de l'usuari.
	 * @param passwordHash El nou hash de la contrasenya.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Retorna el rol de l'usuari.
	 * @return El rol de l'usuari.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Estableix el rol de l'usuari.
	 * @param role El nou rol de l'usuari.
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
