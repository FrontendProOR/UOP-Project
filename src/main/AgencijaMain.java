package main;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

import mainStructure.Administrator;
import mainStructure.Arrangment;
import mainStructure.Reservation;
import mainStructure.Turist;

public class AgencijaMain {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
//few generated users add Role in 
		//ADMIN USERNAME I SIFRA: milan123 Sifra54321
		//jovan12345 Sifra54321
		Reservation letoGrckaReservation = new Reservation(2,LocalDateTime.now(),10);
		Arrangment aranzmaniGrckaArrangment = new Arrangment("src/image/Arrangments/arrangment1.jpg",LocalDateTime.now(),100,653.99,0.10);
		Turist djokaTurist = new Turist("Djordje","Cvarkov","1212973093222","Pejicevi Salasi 6 Novi Sad","djoks123","Sifra54321");
		System.out.println(djokaTurist.getName()+" "+ djokaTurist.getSurname() +" koji zivi u : "+ djokaTurist.getAddress());
		System.out.println(djokaTurist.authenticatePassword("Sifra5321"));
		System.out.println(djokaTurist);
		System.out.println(djokaTurist.getRole());
		Administrator kresoAdministrator = new Administrator("Nenad","Solajic","1010004123123","Brace Ribnikar 1 Novi Sad","kreso123","Sifra12345");
		System.out.println(kresoAdministrator.getRole()+" "+kresoAdministrator.authenticatePassword("Sifra12345"));
		
	}
	
}
