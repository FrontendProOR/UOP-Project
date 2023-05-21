package mainStructure;
//import java.util.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 
 */
public class Turist extends User {

    

	public Turist( String name, String surname, String JMBG, String address, String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super( name, surname, JMBG, address, username, password);
		super.setRole(Role.Turist);
		// TODO Auto-generated constructor stub
	}

	/**
     * Default constructor
     */
    public Turist() {
    }

    /**
     * 
     */
    protected Role role = Role.Turist;

	public Role getRole() {
		return role;
	}

//	public void setRole(Role role) {
//		this.role = role;
//	}

}