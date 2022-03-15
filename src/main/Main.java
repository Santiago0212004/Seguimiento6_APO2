package main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import model.Billboard;
import model.BillboardData;

public class Main {
	
	static String path = "src/Datos1.csv";
	static BillboardData data = new BillboardData(path);
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int choice;
		
		try {
			data.loadJSON();
			data.importCSV();
			data.saveJSON();
		} catch (ClassNotFoundException | IOException | ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("What do you want to do?: \n");
		
		System.out.println("(1) Add a new billboard.");
		
		System.out.println("(2) Show all billboards.");
		
		System.out.println("(3) See the list of dangerous billboards.");
		
		choice = sc.nextInt();
		sc.nextLine();
		
		if(choice == 1) {
			addBillboard();
		}
		else if(choice == 2) {
			showBillboards();
		}
		else if(choice == 3) {
			securityReport();
		}
		
	}
	
	public static void addBillboard() {
		System.out.println("\\nWrite billboard values separated by ++: \n");

		String billboardString = sc.nextLine();
		
		String[] billboard = billboardString.split("\\++");
		
		int width = Integer.parseInt(billboard[0]);
		
		int height = Integer.parseInt(billboard[1]);
		
		boolean inUse = false;
		
		if(billboard[2].equalsIgnoreCase("true")) {
			inUse = true;
		} else if(billboard[2].equalsIgnoreCase("false")) {
			inUse = false;
		}
		
		String brand = billboard[3];
		
		while(width<=0 || height<=0 || (!billboard[2].equalsIgnoreCase("true") && !billboard[2].equalsIgnoreCase("false"))) {
			System.out.println("\nWrite valid values separated by ++: ");
			
			billboardString = sc.nextLine();
			
			
			billboard = billboardString.split("++");
			
			width = Integer.parseInt(billboard[0]);
			
			height = Integer.parseInt(billboard[1]);
			
			inUse = false;
			
			if(billboard[2].equalsIgnoreCase("true")) {
				inUse = true;
			} else if(billboard[2].equalsIgnoreCase("false")) {
				inUse = false;
			}
			
			brand = billboard[3];
		}
		
		
		try {
			data.addBillboard(new Billboard(width,height,inUse,brand));
			data.saveJSON();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void showBillboards() {
		
		System.out.println("W        H     inUse     Brand\n");
		int i = 0;
		for(Billboard b : data.getBillboards()) {
			System.out.println(b.getWidth()+"     "+b.getHeight()+"     "+b.inUseString()+"     "+b.getBrand());
			i++;
		}
		System.out.println("Total: "+i+" billboards");
	}
	
	public static void securityReport() {
		
		System.out.println("--------------------------");
		System.out.println("DANGEROUS BILLBOARD REPORT");
		System.out.println("--------------------------");
		System.out.println("The dangerous billboard are: ");
		
		int i = 1;
		
		for(Billboard b : data.getBillboards()) {
			if((b.getWidth()*b.getHeight())>200000) {
				System.out.println(i+". Billboard "+b.getBrand()+" with area "+(b.getWidth()*b.getHeight())+" cm2");
				i++;
			}
		}
	}
}
