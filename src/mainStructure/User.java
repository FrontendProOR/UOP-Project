package mainStructure;

import java.util.Random;

//import java.util.*;

/**
 * Add validation for short and incorrect parameters in field constructor
 */
public abstract class User {

    public User( String name, String surname, String JMBG, String address, String username, String password) {
		this.id = new Random().nextLong();
		this.name = name;
		this.surname = surname;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    protected long id;

    /**
     * 
     */
    protected String name;

    /**
     * 
     */
    protected String surname;

    /**
     * 
     */
    protected String JMBG;

    /**
     * 
     */
    protected String address;

    /**
     * 
     */
    protected String username;

    /**
     * 
     */
    protected String password;

}