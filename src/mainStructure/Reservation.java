package mainStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import main.AgencijaAdministratorWindow;
import main.TotalPrice;


public class Reservation {
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(util.Util.DATE_TIME_FORMAT);//dd.MM.yyyy HH:mm
	String finalDateAndTime = now.format(formatter);

	
	public Reservation() {
		long id = new Random().nextLong();
		this.id = id;
		this.numPassangers = 1;
		this.dateAndTime = finalDateAndTime;
		this.tripDuration = 0;
		this.status = Status.Created;
		this.arrangmentId = "";
		this.sellerId = "";
	}

	
	protected long id;
	private String arrangmentId;
	private String sellerId;
	private double totalPrice;
	private String turistId;
     
	public Reservation(String turistId,String arrangmentId,String sellerId, int numPassangers, int tripDuration) {
		long id = new Random().nextLong();
		this.id = id;
		this.numPassangers = numPassangers;
		this.dateAndTime = finalDateAndTime;
		this.tripDuration = tripDuration;
		this.status = Status.Created;
		this.arrangmentId = arrangmentId;
		this.sellerId = sellerId;
		this.setTuristId(turistId);
		this.totalPrice = calculateTotalPrice();
		AgencijaAdministratorWindow.decrementNumberOfRooms(arrangmentId);
	}

	public String getData() {
		return this.getId() + "|" + this.getArrangmentId() + "|" +  this.getSellerId() + "|" + this.getStatus() + "|"
				+ this.getTripDuration() + "|" + this.getNumPassangers()  + "|" + this.getDateAndTime()+"|"+this.turistId+"|"+this.totalPrice;
	}

	public int getNumPassangers() {
		return numPassangers;
	}
	
	public double calculateTotalPrice() {
		double totalPriceDouble = 0;
        String csvFile1 = "src/data/arrangments.csv";
        String line1;
        String csvSplitBy = "\\|";
        String unitPrice = "0";
        String fairDiscount = "0";
		try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile1))) {
            while ((line1 = br1.readLine()) != null) {
                String[] values1 = line1.split(csvSplitBy);
                String rowId1 = values1[0];
                if (rowId1.equals(this.arrangmentId)) {
                    unitPrice = values1[8];
                    fairDiscount = values1[9];
                    
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (unitPrice == null || fairDiscount == null) {
            throw new IllegalArgumentException("Invalid data for calculation");
        }
        double tripDurationDouble = Double.valueOf(this.tripDuration);
        double numberOfPassengersDouble = Double.valueOf(this.numPassangers);
        double unitPriceDouble = Double.valueOf(unitPrice);
        double fairDiscountDouble = Double.valueOf(fairDiscount);
        if (tripDurationDouble <= 0 || numberOfPassengersDouble <= 0 || unitPriceDouble <= 0) {
            throw new IllegalArgumentException("Invalid data for calculation");
        }

        totalPriceDouble = (tripDurationDouble * numberOfPassengersDouble * unitPriceDouble) * (1 - (fairDiscountDouble / 100));
		return totalPriceDouble;
	}

	public void setNumPassangers(int numPassangers) {
		this.numPassangers = numPassangers;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
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

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	private Status status;
	protected int numPassangers;

	
	protected String dateAndTime;

	
	protected int tripDuration;

	protected void priceUpdated() {
		//
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String arrangmentId,long reservationId) {
		main.TotalPrice totalPriceTemp = new TotalPrice();
		double totalPriceDouble = totalPriceTemp.getTotalPrice(arrangmentId, reservationId);
		this.totalPrice = totalPriceDouble;
	}

	public String getTuristId() {
		return turistId;
	}

	public void setTuristId(String turistId) {
		this.turistId = turistId;
	}

	
}
