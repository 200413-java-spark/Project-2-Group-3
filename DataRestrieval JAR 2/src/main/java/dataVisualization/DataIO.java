package dataVisualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

public class DataIO {
	private String inputCSV;
	private ArrayList<Double> x = new ArrayList<Double>(0);
	private ArrayList<Double> y = new ArrayList<Double>(0);
	// want to access from another class, don't want new instance (plotter)
	static String xTitle; 
	static String yTitle;

	
	//need another class to extract S3 bucket CSV String location
	public DataIO(String inputCSV) {
		this.inputCSV = inputCSV;
	}

	public void CSVList() {
		File file = new File( this.inputCSV );
		int i = 0;
		Scanner inputStream;
		try {
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String line = inputStream.next();
				String[] values = line.split(",");
				if (i == 0) {
					DataIO.xTitle = values[0].toString();
					DataIO.yTitle = values[1].toString();
					i++;
				}else {   // this adds the currently parsed line to the 2-dimensional string array
					Double temp = Double.valueOf(values[0]);
					Double temp2 = Double.valueOf(values[1]);
					this.x.add( temp );
					this.y.add( temp2 ); // issue
				}
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	
	
	
	
	
	
	
	public ArrayList<Double> getX() {
		return this.x;
	}

	public void setX(ArrayList<Double> x) {
		this.x = x;
	}

	public ArrayList<Double> getY() {
		return this.y;
	}

	public void setY(ArrayList<Double> y) {
		this.y = y;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}
	
	
}
