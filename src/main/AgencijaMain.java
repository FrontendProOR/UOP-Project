package main;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

import mainStructure.Administrator;
import mainStructure.Agent;
import mainStructure.Arrangment;
import mainStructure.Reservation;
import mainStructure.Turist;

public class AgencijaMain {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
//few generated users add Role in 
		//ADMIN USERNAME I SIFRA: milan123 Sifra54321
		//jovan12345 Sifra54321
//		Arrangment aranzmaniGrckaArrangment = new Arrangment("src/image/arrangment1.jpg",LocalDateTime.now(),"Greece","Description",100,653.99,0.10);
		Turist djokaTurist = new Turist("Djordje","Cvarkov","1212973093222","Pejicevi Salasi 6 Novi Sad","0038165123648","djoks123","Sifra54321");
//		System.out.println(djokaTurist.getName()+" "+ djokaTurist.getSurname() +" koji zivi u : "+ djokaTurist.getAddress());
//		System.out.println(djokaTurist.authenticatePassword("Sifra5321"));
//		System.out.println(djokaTurist);
//		System.out.println(djokaTurist.getRole());
//		Administrator kresoAdministrator = new Administrator("Nenad","Solajic","1010004123123","Brace Ribnikar 1 Novi Sad","kreso123","Sifra12345");
//		System.out.println(kresoAdministrator.getRole()+" "+kresoAdministrator.authenticatePassword("Sifra12345"));
//		System.out.println(aranzmaniGrckaArrangment.getInfo());
		Agent djordjeAgent = new Agent("Djordje","Cvarkov","1212973093222","Pejicevi Salasi 6 Novi Sad","0038165123648","djoks123","Sifra54321");
//		Reservation rezervacija = new Reservation("32423424324","23973286420934","2",8,7);
//		rezervacija.setId(-2275808568371275128L);
//		System.out.println(rezervacija.getDateAndTime());
//		System.out.println(rezervacija.getData());
//		System.out.println(rezervacija.getId());
//		System.out.println(rezervacija.getTotalPrice());
//		System.out.println(rezervacija.getStatus());
		System.out.println(djordjeAgent.getListOfArrangments());
		djokaTurist.setId(123456789L);
		System.out.println(djokaTurist.getListOfReservations());
		
		
		System.out.println(djordjeAgent.getUserData());
		
	}
	
}
