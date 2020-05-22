package dataVisualization;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.*;
import java.util.List;

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

//		// Display Scatter Graph
		String csvName = "C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/dataVisualization/sample.csv";
		String tableTitle = "Test Title";
		Plotter p = new Plotter(csvName, tableTitle);
//		p.genScatter();

		// Fitter
		DataIO d = new DataIO(csvName);
		System.out.println("24");
		d.CSVList();
		System.out.println("26");//
		Fitter f = new Fitter(d.getX(), d.getY());
		System.out.println("27");
		f.curveFitter();
		double[] coeff = f.getCoeff() ; // double[]
		
		// Plot overlay trendline and scatterplot
		// Generate data for trendline - DONE
		
		// OBSERVED convert to double[] 
		double[] xObs = arrayList2Array( d.getX() );
		double[] yObs = arrayList2Array( d.getY() );
		
		// EXPECTED convert to double[]
		ContinuousToDiscrete c2D = new ContinuousToDiscrete( coeff );
		double[] xExp = c2D.removeDuplicates(xObs);
		double[] yExp = f.discretize();
		
		// Generate figure
		p.multiPlot(xObs, yObs, xExp, yExp); //double[] xObs, double[] yObs, double[] xExp, double[] yExp);		
		
		// Compute correlation coefficient
		
		
	}

	public static double[] arrayList2Array(ArrayList<Double> list) {
		Object[] array = list.toArray();
		double[] arrDouble = new double[list.size()];
		int indx = 0;

		for (Object o : array) {
			double s = (double) o;
			arrDouble[indx] = s;
			indx++;
		}
		
		return arrDouble;
	}
}
