package mainStructure;
//import java.util.*;

/**
 * 
 */
public class Administrator extends User {

    public Administrator(String name, String surname, String JMBG, String address, String username, String password) {
		super(name, surname, JMBG, address, username, password);
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
    protected Role role = Role.Administrator;

	public Role getRole() {
		return role;
	}

//	public void setRole(Role role) {
//		this.role = role;
//	}

}