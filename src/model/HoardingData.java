package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HoardingData {
	private ArrayList<Hoarding> hoardings;
	
	private String path;
	private File data;
	
	public HoardingData(String path) {
		hoardings = new ArrayList<>();
		this.path = path;
		this.data = new File(path);
	}
	
	public void loadData() throws IOException, ClassNotFoundException {
		hoardings.clear();
		
		BufferedReader br = new BufferedReader(new FileReader(this.path));
		
		String line;
		
		int i = 0;
		int width, height;
		boolean inUse;
		String brand;
		
		while((line = br.readLine()) != null) {
			
			if(i>0) {
				String[] hoardingProp = line.split("\\|");
				
				width = Integer.parseInt(hoardingProp[0]);
				height = Integer.parseInt(hoardingProp[1]);
				
				if(hoardingProp[2].equals("true")) {
					inUse = true;
				} else {
					inUse = false;
				}
				
				brand = hoardingProp[3];
				
				Hoarding addingHoarding = new Hoarding(width, height, inUse, brand);
				hoardings.add(addingHoarding);
				
			}
			
			i++;
		}
		
	}
	
	public void addHoarding(Hoarding hoarding) throws ClassNotFoundException, IOException {
		hoardings.add(hoarding);
		
		FileWriter fw = new FileWriter(this.path,true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		pw.println(""+hoarding.getWidth()+"|"+hoarding.getHeight()+"|"+hoarding.isInUse()+"|"+hoarding.getBrand());
		pw.flush();
		pw.close();
	}

	public ArrayList<Hoarding> getHoardings() {
		return hoardings;
	}
	
}
