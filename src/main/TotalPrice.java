package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TotalPrice {
	public double calculateTotalPrice(String arrangmentRowIdString,long reservationId) {
		//ova funkcija vraca finalnu cenu prosledjuju joj se id aranzmana i rezervacija onda ona trazi u csv fajlu poklapanja
		double totalPrice;
		String reservationIdString = Long.toString(reservationId);
		String[] allValuesForCalculatingTotalPrice = getValuesForTotalPriceCalculations(arrangmentRowIdString,reservationIdString); 
		double tripDuration = Double.parseDouble(allValuesForCalculatingTotalPrice[0]);
		double numberOfPassengers = Double.parseDouble( allValuesForCalculatingTotalPrice[1]);
		double unitPrice = Double.parseDouble(allValuesForCalculatingTotalPrice[2]);
		double fairDiscount = Double.parseDouble(allValuesForCalculatingTotalPrice[3]);
		totalPrice = (tripDuration * numberOfPassengers * unitPrice) * (1 - (fairDiscount / 100));
		return totalPrice;
	}
	
	public String[] getValuesForTotalPriceCalculations(String arrangmentId,String reservationId) {
		//Ovde da bi racunalo total price mora da prelista csv fajl sa rezervacijama i aranzmanima 
	    String csvFile = "src/data/reservations.csv";
	    String csvFile1 = "src/data/arrangments.csv";
	    String line;
	    String line1;
	    String csvSplitBy = "\\|";
	    String[] lineStrings = {};
	    String[] lineStrings1 = {};
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        while ((line = br.readLine()) != null) {
	            String[] values = line.split(csvSplitBy);
	            String rowId = values[0];
	            if (rowId.equals(reservationId)) {
	                String tripDuration = values[4];
	                String numberOfPassengers = values[5];
	                lineStrings = new String[]{tripDuration, numberOfPassengers};
	                break;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile1))) {
	        while ((line1 = br1.readLine()) != null) {
	            String[] values1 = line1.split(csvSplitBy);
	            String rowId1 = values1[0];
	            if (rowId1.equals(arrangmentId)) {
	                String unitPrice = values1[8];
	                String fairDiscount = values1[9];
	                lineStrings1 = new String[]{unitPrice, fairDiscount};
	                break;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Combine lineStrings and lineStrings1 arrays
	    String[] combinedArray = new String[lineStrings.length + lineStrings1.length];
	    System.arraycopy(lineStrings, 0, combinedArray, 0, lineStrings.length);
	    System.arraycopy(lineStrings1, 0, combinedArray, lineStrings.length, lineStrings1.length);

	    // Convert the values to double and store them in a new array
	    double[] doubleValues = new double[4];
	    for (int i = 0; i < combinedArray.length; i++) {
	        try {
	            doubleValues[i] = Double.parseDouble(combinedArray[i]);
	        } catch (NumberFormatException e) {
	            // Set default value if the element is not a valid number
	            doubleValues[i] = 0.0;
	        }
	    }

	    // Convert the double values to strings and store them in the result array
	    String[] resultArray = new String[4];
	    for (int i = 0; i < doubleValues.length; i++) {
	        resultArray[i] = String.valueOf(doubleValues[i]);
	    }

	    return resultArray;
	}
}
