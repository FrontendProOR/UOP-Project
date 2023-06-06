package mainStructure;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

//import java.util.*;

/**
 * 
 */
public class Arrangment {

	private long sellerID;

	private TypeOfArrangement typeOfArrangment;

	private String availableDate;

	private boolean deleted;

	private TypeOfAccommodation accomodation;

	private String numberOfRooms;

	private String numberOfOvernightStays;

	public Arrangment() {
		this.id = 0;
		this.sellerID = 0;
		this.typeOfArrangment = TypeOfArrangement.LongJourneys;
		this.picture = "";
		this.numberOfOvernightStays = "0";
		this.availableDate = "01.01.2022";
		this.numberOfRooms = "0";
		this.accomodation = TypeOfAccommodation.Hotel;
		this.unitPrice = "0.0";
		this.fairDiscount = "0";
		this.deleted = false;
	}

	public Arrangment(long id,long sellerID,String picture, TypeOfArrangement typeOfArrangement,String availableDate, String price, String fairDiscount, boolean deleted, TypeOfAccommodation typeOfAccommodation, String numberOfRooms, String numberOfOvernightStays) {
		if(id == 0) {
			id = new Random().nextLong();
		}
		if(sellerID == 0) {
			sellerID = new Random().nextLong();
		}
		this.id = id;
		this.sellerID = sellerID;
		this.typeOfArrangment = typeOfArrangement;
		this.picture = picture;
		this.numberOfOvernightStays = numberOfOvernightStays;
		this.availableDate = availableDate;
		this.numberOfRooms = numberOfRooms;
		this.accomodation = typeOfAccommodation;
		this.unitPrice = price;
		this.fairDiscount = fairDiscount;
		this.deleted = deleted;
	}

	public Object getNumberOfOvernightStays() {
		return numberOfOvernightStays;
	}

	public void setNumberOfOvernightStays(String numberOfOvernightStays) {
		this.numberOfOvernightStays = numberOfOvernightStays;
	}

	public Object getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public Object getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(TypeOfAccommodation accomodation) {
		this.accomodation = accomodation;
	}

	public String getInfo() {
		return this.id+"|"+this.sellerID+"|"+this.typeOfArrangment.ordinal()+"|"+this.picture+"|"+this.availableDate+"|"+this.numberOfOvernightStays+"|"+this.numberOfRooms+"|"+this.accomodation.ordinal()+"|"+this.unitPrice+"|"+this.fairDiscount+"|"+this.deleted;
	}
	
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPicture() {
		return picture;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getPrice() {
		return unitPrice;
	}

	public void setPrice(String price) {
		this.unitPrice = price;
	}

	public String getFairDiscount() {
		return fairDiscount;
	}

	public void setFairDiscount(String fairDiscount) {
		this.fairDiscount = fairDiscount;
	}

	public TypeOfArrangement getTypeOfArrangment() {
		return typeOfArrangment;
	}

	public void setTypeOfArrangment(TypeOfArrangement typeOfArrangment) {
		this.typeOfArrangment = typeOfArrangment;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * 
	 */
	protected long id;

	public long getSellerID() {
		return sellerID;
	}

	public void setSellerID(long sellerID) {
		this.sellerID = sellerID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	protected String title;
	
	protected String description;
	/**
	 * 
	 */
	protected String picture;

	/**
	 * 
	 */

	/**
	 * 
	 */
	protected String capacity;

	/**
	 * 
	 */
	protected String unitPrice;

	/**
	 * 
	 */
	protected String fairDiscount;

}