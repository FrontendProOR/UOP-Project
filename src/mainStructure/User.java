package mainStructure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.lang.Integer;
import auth.Pbkdf2;

public abstract class User {

	protected byte[] hash;

	public Gender getGender() {
		return gender;
	}

	public User(String name, String surname, String JMBG, String address, String username, String password) {
		this.id = new Random().nextLong();
		this.name = name;
		this.surname = surname;
		this.gender = (Integer.valueOf(JMBG.substring(9, 12)) < 500) ? Gender.Male : Gender.Female;
		this.JMBG = JMBG;
		this.address = address;
		this.username = username;
		this.password = password;
		
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

//	public String getPassword() {
//		return password;
//	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Default constructor
	 */
	public User() {
	}

	
	protected long id;

	
	protected String name;

	
	protected String surname;

	
	protected String JMBG;

	
	protected String address;

	
	protected String username;

	
	protected String password;
	
	protected Gender gender;

}