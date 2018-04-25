package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataUtil {
	public static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String returnHash(String originalString) {
		String hashedString = new String();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(
					originalString.getBytes(StandardCharsets.UTF_8));
			hashedString = bytesToHex(encodedhash);
			System.out.println("hashed String: " + hashedString);
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("Error! Algorithm for hash undefined!");
		}
		return hashedString;
	}
	
	public static int boolToInt(boolean b) {
		return b ? 1 : 0;
	}
}
