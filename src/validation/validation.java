package validation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Arrays;

public class validation {
	// Validating name,surname
	public static boolean IsValidNameSurname(String str) {
		String expression = "^[a-zA-Z\\s]+";
		return str.matches(expression);
	}

	// Validating JMBG
	public static boolean isValidJMBG(String jmbg) {
		Integer danInteger = Integer.valueOf(jmbg.substring(0, 2));
		Integer mesecInteger = Integer.valueOf(jmbg.substring(2, 4));
		Integer godinaInteger = Integer.valueOf(jmbg.substring(4, 7));
		if (godinaInteger > 920) {
			godinaInteger += 1000;
		} else if (godinaInteger < 20) {
			godinaInteger += 2000;
		}
		if (jmbg.length() == 13 && danInteger < 31 && mesecInteger <= 12
				&& (godinaInteger > 1920 && godinaInteger < Year.now().getValue())) {
			return true;
		} else {
			return false;
		}
	}

	// Validating adress
	public static boolean isValidAdress(String adress) {
		String expressionString = "[a-zA-Z0-9\\\\\\s.\\|'\"]+";
		return adress.matches(expressionString);
	}

	// Validating username
	public static boolean isValidUsername(String username) {
		String expressionString = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$";
		return username.matches(expressionString);
	}

	//validating phone number
	public static boolean isValidPhoneNumber(String phoneNumber) {
		String expressionString = "[0-9]{0,14}$";
		return phoneNumber.matches(expressionString);
	}
	
	// Validating password

	public static boolean IsValidPassword(String password) {
		String expressionString = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$";
		return password.matches(expressionString);
	}

	// Validating number any numeric value I dont have time to make more complex
	// stuff
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isNumeric(String str) {
		try {
			// Try parsing the string as a long
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e1) {
			try {
				// Try parsing the string as an int
				Integer.parseInt(str);
				return true;
			} catch (NumberFormatException e2) {
				try {
					// Try parsing the string as a float
					Float.parseFloat(str);
					return true;
				} catch (NumberFormatException e3) {
					try {
						// Try parsing the string as a double
						Double.parseDouble(str);
						return true;
					} catch (NumberFormatException e4) {
						// The string is not a valid number
						return false;
					}
				}
			}
		}
	}

}
