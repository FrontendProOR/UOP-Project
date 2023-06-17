package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TotalPrice {
    private String numberOfPassengers;
	private String tripDuration;
	private String unitPrice;
	private String fairDiscount;

	
	public double getTotalPrice(String arrangementId, long reservationIdLong) {
        String reservationId = String.valueOf(reservationIdLong);
        String csvFile = "src/data/reservations.csv";
        String csvFile1 = "src/data/arrangments.csv";
        String line;
        String line1;
        String csvSplitBy = "\\|";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                String rowId = values[0];
                if (rowId.equals(reservationId)) {
                    this.tripDuration = values[4];
                    this.numberOfPassengers = values[5];
                    
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
                if (rowId1.equals(arrangementId)) {
                    this.unitPrice = values1[8];
                    this.fairDiscount = values1[9];
                    
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tripDuration == null || numberOfPassengers == null || unitPrice == null || fairDiscount == null) {
            throw new IllegalArgumentException("Invalid data for calculation");
        }
        
        double tripDurationDouble = Double.valueOf(tripDuration);
        double numberOfPassengersDouble = Double.valueOf(numberOfPassengers);
        double unitPriceDouble = Double.valueOf(unitPrice);
        double fairDiscountDouble = Double.valueOf(fairDiscount);

        if (tripDurationDouble <= 0 || numberOfPassengersDouble <= 0 || unitPriceDouble <= 0) {
            throw new IllegalArgumentException("Invalid data for calculation");
        }

        double totalPrice = (tripDurationDouble * numberOfPassengersDouble * unitPriceDouble) * (1 - (fairDiscountDouble / 100));
        return totalPrice;
    }

	public TotalPrice(String numberOfPassengers, String tripDuration, String unitPrice, String fairDiscount) {
		this.numberOfPassengers = numberOfPassengers;
		this.tripDuration = tripDuration;
		this.unitPrice = unitPrice;
		this.fairDiscount = fairDiscount;
	}
	public TotalPrice() {
		this.numberOfPassengers = "0";
		this.tripDuration = "0";
		this.unitPrice = "0";
		this.fairDiscount = "0";
	}
}
