package util;

public class Encrypt {

	public static String simpleHash(String input) {
		int hash = 7;
		for (int i = 0; i < input.length(); i++) {
			hash = hash * 31 + input.charAt(i);
		}
		return Integer.toHexString(hash);
	}
}
