package mainStructure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Year;
import java.util.Random;

import java.lang.Integer;
import auth.Pbkdf2;

//Fix problem with separator in csv choose sentinel value to separate text or format all inputs 

public abstract class User {
	//Validating name,surname
	protected static boolean IsValidNameSurname(String str) {
	    String expression = "^[a-zA-Z\\s]+"; 
	    return str.matches(expression);        
	}
	//Validating JMBG
	protected static boolean isValidJMBG(String jmbg) {
		Integer danInteger = Integer.valueOf(jmbg.substring(0,2));
		Integer mesecInteger = Integer.valueOf(jmbg.substring(2,4));
		Integer godinaInteger = Integer.valueOf(jmbg.substring(4,7));
		if(godinaInteger > 920) {
			godinaInteger += 1000;
		}else if(godinaInteger<20) {
			godinaInteger += 2000;
		}
		if(jmbg.length() == 13 && danInteger < 31 && mesecInteger < 12 && (godinaInteger>1920 && godinaInteger < Year.now().getValue())) {
			return true;
		}else {
			return false;
		}
	}
	//Validating adress
	protected static boolean isValidAdress(String adress) {
		String expressionString = "[a-zA-Z0-9\\\\\\s.\\|'\"]+";
		return adress.matches(expressionString);
//		return true;
	}
	//Validating username
	protected static boolean isValidUsername(String username) {
		String expressionString = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$";
		return username.matches(expressionString);
	}
	//Validating password
	
	    protected static boolean IsValidPassword(String password) {
	    	String expressionString = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$";
	    	return password.matches(expressionString);
	    }
	    	
	protected byte[] hash;
    public Gender getGender() {
		return gender;
	}
	public User( String name, String surname, String JMBG, String address, String username, String password) {
		if(IsValidNameSurname(name) && IsValidNameSurname(surname)&&isValidUsername(username)&&isValidAdress(address)&&isValidJMBG(JMBG)&&IsValidPassword(password)) {
    	this.id = new Random().nextLong();
		this.name = name;
		this.surname = surname;
		this.gender = (Integer.valueOf(JMBG.substring(9,12)) < 500)?Gender.Male:Gender.Female;
		this.JMBG = JMBG;
		this.address = address;
		this.username = username;
		this.password = password;
		//delete line below just a test for hash
		this.hash = this.makeHash(this.password);
		}
	}
    protected byte[] makeHash(String password) {
    	byte[] hash = null;
		try {
			hash = Pbkdf2.getEncryptedPassword(password, "salt".getBytes());
		} catch (NoSuchAlgorithmException e) {
			hash = JMBG.getBytes();
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			hash = JMBG.getBytes();
			e.printStackTrace();
		}
    	return hash;
    }
    public byte[] getHash() {
    	return this.hash;
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
    /**
     * 
     */
    protected Gender gender;

}