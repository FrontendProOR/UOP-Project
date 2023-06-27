package mainStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;


public class Agent extends User {

    private List<String> listOfArrangments;

	public Agent(String name, String surname, String JMBG, String address,String phoneNumberString, String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super(name, surname, JMBG, address,phoneNumberString, username, password);
		super.setRole(Role.Agent);
		this.listOfArrangments = this.setListOfArrangements(this.getId());
	}
	
    public Agent() {
    	
    }

    public String getUserData() {
        StringBuilder arrangementString = new StringBuilder();
        long agentId;
        String userInfo = this.userInfo();
        String[] values = userInfo.split("\\|");
        agentId = Long.parseLong(values[0]);
        
        this.listOfArrangments = this.setListOfArrangements(agentId);
        
        for (String arrangement : this.listOfArrangments) {
            arrangementString.append(arrangement).append("|");
        }

        if (!this.listOfArrangments.isEmpty()) {
            arrangementString.deleteCharAt(arrangementString.length() - 1);
        }

        return super.userInfo() +"|"+ arrangementString.toString();
    }

    
    public long getId() {
    	return super.getId();
    }
    
    protected void updateListOfArrangments() {
    	this.listOfArrangments = this.setListOfArrangements(this.getId());
    }

    protected List<String> setListOfArrangements(long sellerIdLong) {
    	String sellerId = Long.toString(sellerIdLong).trim();
        List<String> listOfArrangements = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\data\\arrangments.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arrangement = line.split("\\|");
                if (arrangement.length > 1 && arrangement[1].trim().equals(sellerId)) {
                    String inputString = arrangement[0];
                	listOfArrangements.add(inputString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return listOfArrangements;
    }
    
	public Role getRole() {
		return role;
	}

	public List<String> getListOfArrangments() {
		return this.listOfArrangments;
	}

	protected Role role = Role.Agent;

}