package dataVisualization;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class Visualization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		/**
		 * Test
		 */
		ArrayList<Double> var  = new ArrayList<Double>();
		var.add(12.0);
		var.add(0.0);
		var.forEach(System.out::println);
		/**
		 * end test
		 */

		// Display Scatter Graph
		String csvName = "C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/dataVisualization/sample.csv";
		String tableTitle = "Test Title";
		String xName = "x-axis";
		String yName = "y-axis";
		Plotter p = new Plotter(csvName, tableTitle, xName, yName);
		p.genScatter();

		// Fitter
		DataIO d = new DataIO(csvName);
		System.out.println("24");
		d.CSVList();
		System.out.println("26");//
		Fitter f = new Fitter(d.getX(), d.getY());
		System.out.println("27");
		f.curveFitter();
		System.out.println(Arrays.toString(f.getCoeff()) ); // double[]
		
		// Plot overlay trendline and scatterplot
		// Generate data for trendline
		
		// Compute correlation coefficient
		
		
	}
}
