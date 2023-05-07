package auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pbkdf2 {

	public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	}

	public static byte[] getEncryptedPassword(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String algorithm = "PBKDF2WithHmacSHA1";
		int derivedKeyLength = 512;
		int iterations = 4096;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();
	}

	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		byte[] salt = new byte[8];
		random.nextBytes(salt);

		return salt;
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static void main(String[] args) throws Exception {

		byte[] hash = getEncryptedPassword("password", "salt".getBytes());
//		System.out.println(Arrays.toString(hash));
		System.out.println(authenticate("password", hash, "salt".getBytes()));//Here is the line that will check if textbox entered password is valid with the one in csv file userdata.csv
		//Rest of the work here is making hash table with user data and two extra columns for "salt".getBytes() and hash which will be later used for 
		//authentication in two lines above in if else statement and that is it

	}

}