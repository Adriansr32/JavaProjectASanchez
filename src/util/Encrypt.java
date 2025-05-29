package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitària per a l'encriptació de contrasenyes.
 * Proporciona un mètode per generar un hash SHA-256 d'una cadena d'entrada.
 */
public class Encrypt {

	/**
	 * Genera un hash SHA-256 de la cadena d'entrada.
	 * @param input La cadena a la qual aplicar el hash (normalment una contrasenya).
	 * @return El hash SHA-256 de la cadena d'entrada com a String hexadecimal.
	 * @throws RuntimeException Si l'algorisme SHA-256 no es troba (poc probable).
	 */
	public static String hashPassword(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(input.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not found", e);
		}
	}
}
