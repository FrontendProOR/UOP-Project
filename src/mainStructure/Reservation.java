package mainStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;


public class Reservation {

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public Reservation() {
		long id = new Random().nextLong();
		this.id = id;
		this.numPassangers = 1;
		this.dateAndTime = LocalDateTime.now();
		this.tripDuration = 0;
		this.status = Status.Created;
		this.arrangmentId = "";
		this.sellerId = "";
	}

	
	protected long id;
	private String arrangmentId;
	private String sellerId;

	public Reservation(String arrangmentId,String sellerId, int numPassangers, LocalDateTime dateAndTime, int tripDuration) {
		long id = new Random().nextLong();
		this.id = id;
		this.numPassangers = numPassangers;
		this.dateAndTime = dateAndTime;
		this.tripDuration = tripDuration;
		this.status = Status.Created;
		this.arrangmentId = arrangmentId;
		this.sellerId = sellerId;
	}

	public String getData() {
		return this.getId() + "|" + this.getArrangmentId() + "|" +  this.getSellerId() + "|" + this.getStatus() + "|"
				+ this.getTripDuration() + "|" + this.getNumPassangers()  + "|" + this.getDateAndTime();
	}

	public int getNumPassangers() {
		return numPassangers;
	}

	public void setNumPassangers(int numPassangers) {
		this.numPassangers = numPassangers;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	private Status status;
	protected int numPassangers;

	
	protected LocalDateTime dateAndTime;

	
	protected int tripDuration;

	protected void priceUpdated() {
		//
	}
	
	protected void roomTaken(String arrangmentId) {
		//This function should decrement for arrangment one room after reservation status is completed
		//decrement in src\data\arrangments.csv
	}
	
	protected double getTotalPrice(long arrangmentRowId) {
		double totalPrice;
		String[] allValuesForCalculatingTotalPrice = this.getValuesForCalculations(this.arrangmentId); 
		int numberOfOvernightStays = Integer.parseInt(allValuesForCalculatingTotalPrice[0]);
//		int numberOfRooms = Integer.parseInt( allValuesForCalculatingTotalPrice[1]);
		double unitPrice = Double.parseDouble(allValuesForCalculatingTotalPrice[2]);
		double fairDiscount = Double.parseDouble(allValuesForCalculatingTotalPrice[3]);
		totalPrice = (numberOfOvernightStays * unitPrice) * (1 - (fairDiscount / 100));//* numberOfRooms ne ubacuje se jer predstavlja broj slobodnih soba
		return totalPrice;
	}
	
	protected String[] getValuesForCalculations(String id) {
	    String csvFile = "src\\data\\arrangments.csv";
	    String line;
	    String csvSplitBy = "\\|";
	    String[] lineStrings = {};

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        while ((line = br.readLine()) != null) {
	            String[] values = line.split(csvSplitBy);
	                String rowId = values[0];
	                if (rowId.equals(id)) {
	                    String numberOfOvernightStays = values[5];
	                    String numberOfRooms = values[6];
	                    String unitPrice = values[8];
	                    String fairDiscount = values[9];
	                    lineStrings = new String[]{numberOfOvernightStays, numberOfRooms, unitPrice, fairDiscount};
	                    break; 
	                }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return lineStrings;
	}


	
	
	public String getArrangmentId() {
		return arrangmentId;
	}

	public void setArrangmentId(String arrangmentId) {
		this.arrangmentId = arrangmentId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

}