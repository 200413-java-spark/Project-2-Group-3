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
	private String xTitle;
	private String yTitle;

	public DataIO(String inputCSV) {
		this.inputCSV = inputCSV;
	}

	public void CSVList() {
		File file = new File( inputCSV );
		int i = 0;
		Scanner inputStream;
		try {
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String line = inputStream.next();
				String[] values = line.split(",");
				if (i == 0) {
					this.xTitle = values[0].toString();
					this.yTitle = values[1].toString();
					i++;
					System.out.println("i = " + i);
					System.out.println("line 34 if, values = " + values[0] );
				}else {
					// this adds the currently parsed line to the 2-dimensional string array
					System.out.println("line 37 else, check Double cast ** = " + Double.valueOf(values[1]));
					Double temp = Double.valueOf(values[0]);
					Double temp2 = Double.valueOf(values[1]);
					System.out.println("here");
					this.x.add( temp );
					System.out.println("h");
					this.y.add( temp2 ); // issue
					System.out.println("line 40 after issue");
				}
				System.out.println("line 42 out" );
			}

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	
	public ArrayList<Double> getX() {
		return x;
	}

	public void setX(ArrayList<Double> x) {
		this.x = x;
	}

	public ArrayList<Double> getY() {
		return y;
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
