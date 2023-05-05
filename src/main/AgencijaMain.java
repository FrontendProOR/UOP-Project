package main;

import mainStructure.*;

public class AgencijaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainStructure.Turist posetilacTurist = new Turist("Pera","Peric","333","","","");
		System.out.println(posetilacTurist.getName());
		
		mainStructure.Agent agent007 = new Agent("James","Bond","333","","","");
		System.out.println(agent007.getName());
		
		mainStructure.Administrator admin = new Administrator("Djordje","Pekic","333","","","");
		System.out.println(admin.getName()+" "+admin.getSurname());
	}

}
