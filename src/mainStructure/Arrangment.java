package mainStructure;

import java.time.LocalDateTime;
import java.util.Random;
//import java.util.*;

/**
 * 
 */
public class Arrangment {


	/**
	 * Default constructor
	 */
	public Arrangment() {
	}

	public Arrangment(String picture, LocalDateTime dateAndTime,String title,String description, int capacity, double price, double fairDiscount) {
		long id = new Random().nextLong();
		this.id = id;
		this.picture = picture;
		this.title = title;
		this.description = description;
		this.dateAndTime = dateAndTime;
		this.capacity = capacity;
		this.price = price;
		this.fairDiscount = fairDiscount;
	}

	public String getInfo() {
		return this.id+"|"+this.picture+"|"+this.dateAndTime+"|"+this.title+"|"+this.description+"|"+this.capacity+"|"+this.price+"|"+this.fairDiscount;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getFairDiscount() {
		return fairDiscount;
	}

	public void setFairDiscount(double fairDiscount) {
		this.fairDiscount = fairDiscount;
	}

	/**
	 * 
	 */
	protected long id;

	protected String title;
	
	protected String description;
	/**
	 * 
	 */
	protected String picture;

	/**
	 * 
	 */
	protected LocalDateTime dateAndTime;

	/**
	 * 
	 */
	protected int capacity;

	/**
	 * 
	 */
	protected double price;

	/**
	 * 
	 */
	protected double fairDiscount;

}