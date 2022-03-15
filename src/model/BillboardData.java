package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class BillboardData {
	private ArrayList<Billboard> billboards;
	
	private String path;
	public BillboardData(String path) {
		billboards = new ArrayList<>();
		this.path = path;
		new File(path);
	}
	

	public void importCSV() throws IOException, ClassNotFoundException, ParseException {
		
		File file = new File(this.path);
		
		if(file.exists()) {
			FileReader fr = new FileReader(this.path);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			int i = 0;
			
			int width, height;
			boolean inUse;
			String brand;
			
			while((line = br.readLine()) != null) {
				
				if(i>0) {
					String[] billboardProp = line.split("\\|");
					
					width = Integer.parseInt(billboardProp[0]);
					height = Integer.parseInt(billboardProp[1]);
					
					if(billboardProp[2].equals("true")) {
						inUse = true;
					} else {
						inUse = false;
					}
					
					brand = billboardProp[3];
					
					Billboard addingBillboard = new Billboard(width, height, inUse, brand);
					billboards.add(addingBillboard);
					
				}
				
				i++;
			}
			
			
			if (br != null)br.close();
            if (fr != null)fr.close();
		}
	}
	
	public void addBillboard(Billboard billboard) throws ClassNotFoundException, IOException {
		billboards.add(billboard);
	}
	
	public void saveJSON() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(this);
			File file = new File("data.json");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(json.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadJSON() {
		
		FileInputStream fis;
		
		try {	
			fis = new FileInputStream(new File("data.json"));
			

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			String line;
			String json = "";
			while((line = reader.readLine())!=null) {
				json += line;
			}
				
			Gson gson = new Gson();
			
			BillboardData loadingData = gson.fromJson(json,BillboardData.class);
			
			if(loadingData!=null) {
				this.billboards = loadingData.billboards;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clear() {
		this.billboards.clear();
	}

	public ArrayList<Billboard> getBillboards() {
		return billboards;
	}
	
}
