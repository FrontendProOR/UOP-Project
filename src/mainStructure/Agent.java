package mainStructure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

//import java.util.*;

/**
 * 
 */
public class Agent extends User {

    public Agent(String name, String surname, String JMBG, String address, String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super(name, surname, JMBG, address, username, password);
		super.setRole(Role.Agent);
		// TODO Auto-generated constructor stub
	}

	/**
     * Default constructor
     */
    public Agent() {
    }

    /**
     * 
     */
    protected Role role = Role.Agent;

	public Role getRole() {
		return role;
	}

//	public void setRole(Role role) {
//		this.role = role;
//	}

}