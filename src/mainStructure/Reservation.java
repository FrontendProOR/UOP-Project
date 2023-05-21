package mainStructure;
import java.time.LocalDateTime;
import java.util.Random;
//import java.util.*;

/**
 * 
 */
public class Reservation {

    /**
     * Default constructor
     */
    public Reservation() {
    }

    /**
     * 
     */
    protected long id;

    public Reservation(int numPassangers, LocalDateTime dateAndTime, int tripDuration) {
    	long id = new Random().nextLong();
    	this.id = id;
		this.numPassangers = numPassangers;
		this.dateAndTime = dateAndTime;
		this.tripDuration = tripDuration;
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

	/**
     * 
     */
    protected int numPassangers;

    /**
     * 
     */
    protected LocalDateTime dateAndTime;

    /**
     * 
     */
    protected int tripDuration;

    /**
     * 
     */
    protected void totalPrice() {
        // TODO implement here
    }

}