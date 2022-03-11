package main;
import java.io.IOException;

import model.Hoarding;
import model.HoardingData;

public class Main {
	public static void main(String[] args) {
		String path = "src/Datos1.csv";
		
		HoardingData data = new HoardingData(path);
		
		try {
			data.loadData();
			data.addHoarding(new Hoarding(100,200,true,"Santiago"));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		for(Hoarding h : data.getHoardings()) {
			System.out.println(h.getWidth()+"|"+h.getHeight()+"|"+h.isInUse()+"|"+h.getBrand());
		}
		
	}
}
