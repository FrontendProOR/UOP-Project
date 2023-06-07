package mainStructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import main.AgencijaAdministratorWindow;


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
		AgencijaAdministratorWindow.decrementNumberOfRooms(arrangmentId);
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

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTuristId() {
		return turistId;
	}

	public void setTuristId(String turistId) {
		this.turistId = turistId;
	}

	
}
