package main;

import mainStructure.*;

public class AgencijaMain {

	public static void main(String[] args) {
		// Hash table for user data --> csv file and from csv file takes salt and hashed password that is passed during registration
		// Make Gui for Sign in and Sign up 
		// Window for Client,Admin Page for Administrator and Agent
		
		mainStructure.Turist turist1 = new Turist("Pera","Peric","333","","perica32","password12345");
		mainStructure.Agent agent1 = new Agent("James","Bond","333","","","");
		mainStructure.Administrator admin1 = new Administrator("Djordje","Pekic","333","","","");
		System.out.println("Name is: "+turist1.getName() +"\nSurname is: "+ turist1.getSurname() +"\nUsername is: "+ turist1.getUsername());
	}

}
