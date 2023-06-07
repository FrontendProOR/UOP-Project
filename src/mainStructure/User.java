package mainStructure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.lang.Integer;

public abstract class User {

	private String phoneNumber;

	public User(String name, String surname, String JMBG, String address,String phoneNumber, String username, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[][] originalPassword = makeHashPassword(password);
		String passwordHexString = auth.Pbkdf2.bytesToHex(originalPassword[0]);
		String saltHexString = auth.Pbkdf2.bytesToHex(originalPassword[1]);
		this.id = new Random().nextLong();
		this.name = name;
		this.surname = surname;
		this.role = null;
		this.gender = (Integer.valueOf(JMBG.substring(9, 12)) < 500) ? Gender.Male : Gender.Female;
		this.JMBG = JMBG;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = passwordHexString;
		this.salt = saltHexString;
	}

	public String userInfo() {
		return this.id + "|" + this.role + "|" + this.name + "|" + this.surname + "|" + this.JMBG + "|" + this.gender
				+ "|" + this.address +"|"+this.getPhoneNumber()+ "|" + this.username + "|" + this.password + "|" + this.salt;
	}

	private byte[][] makeHashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[][] result = new byte[2][];
		byte[] salt = auth.Pbkdf2.generateSalt();
		result[0] = auth.Pbkdf2.getEncryptedPassword(password, salt);
		result[1] = salt;
		return result;
	}

	public static byte[] hexToBytes(String hexString) {
		int length = hexString.length();
		byte[] bytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return bytes;
	}

	public boolean authenticatePassword(String passedString) throws NoSuchAlgorithmException, InvalidKeySpecException {
		boolean isValid = false;
		if (auth.Pbkdf2.authenticate(passedString, hexToBytes(this.getPassword()), hexToBytes(this.getSalt()))) {
			isValid = true;
		}
		return isValid;
	}

	private String getSalt() {
		return salt;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String JMBG) {
		this.JMBG = JMBG;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Gender getGender() {
		return gender;
	}

	/**
	 * Default constructor
	 */
	public User() {
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	protected long id;

	protected String name;

	protected String surname;

	private Role role;

	protected String JMBG;

	protected String address;

	protected String username;

	protected String password;

	private String salt;

	protected Gender gender;

}