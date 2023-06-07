package mainStructure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

//import java.util.*;

/**
 * 
 */
public class Administrator extends User {

    public Administrator(String name, String surname, String JMBG, String address,String phoneNumber, String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super(name, surname, JMBG, address,phoneNumber, username, password);
		super.setRole(Role.Administrator);
		// TODO Auto-generated constructor stub
	}

	/**
     * Default constructor
     */
    public Administrator() {
    }

    /**
     * 
     */
//    protected Role role = Role.Administrator;

//	public Role getRole() {
//		return role;
//	}

//	public void setRole(Role role) {
//		this.role = role;
//	}

}