package mainStructure;
//import java.util.*;

/**
 * 
 */
public class Turist extends User {

    

	public Turist( String name, String surname, String JMBG, String address, String username, String password) {
		super( name, surname, JMBG, address, username, password);
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

}