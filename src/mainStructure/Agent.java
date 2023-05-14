package mainStructure;
//import java.util.*;

/**
 * 
 */
public class Agent extends User {

    public Agent(String name, String surname, String JMBG, String address, String username, String password) {
		super(name, surname, JMBG, address, username, password);
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