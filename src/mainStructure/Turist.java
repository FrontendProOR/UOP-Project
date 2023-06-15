package mainStructure;
//import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;


public class Turist extends User {

    

	private List<String> listOfReservations;

	public Turist( String name, String surname, String JMBG, String address,String phoneNumber, String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super( name, surname, JMBG, address,phoneNumber, username, password);
		super.setRole(Role.Turist);
		this.listOfReservations = this.setListOfReservations(this.getId());
	}
	
	public Turist() {
	}
	
	
	public void updateListOfReservations(long turistId) {
		this.listOfReservations = this.setListOfReservations(turistId);
	}
	
	protected List<String> setListOfReservations(long turistId){
		List<String> listOfReservations = new ArrayList<>();
		String turistIdString = Long.toString(turistId);
		
//		turistIdString = "123456789";
		String csvFile = "src\\data\\reservations.csv";
        String line;
        String cvsSplitBy = "\\|";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] reservationData = line.split(cvsSplitBy);
                if (reservationData[1].equals(turistIdString)){ 
                	
                	String reservationString = reservationData[0];
                	listOfReservations.add(reservationString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return listOfReservations;
	}
	

	public Role getRole() {
		return role;
	}

	public List<String> getListOfReservations() {
		return listOfReservations;
	}
	
	public void setId(long idToSet) {
		super.setId(idToSet);
	}

	protected long getId() {
		return super.getId();
	}
	protected Role role = Role.Turist;

//	public void setListOfReservations(List<String> listOfReservations) {
//		this.listOfReservations = listOfReservations;
//	}

//	public void setRole(Role role) {
//		this.role = role;
//	}

}