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

		/**
		 * end test
		 */

	

//		// Display Scatter Graph
		String csvName = "part-00000-05cd7a32-dc86-4fc8-b6ab-8cc517fbb525-c000.csv";//"sample.csv";//"hello.csv/part-00000-4cd5130f-f229-45f3-bbb4-7849a969b26e-c000.csv"; //"C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/dataVisualization/sample.csv";
		String tableTitle = "Test Title"; // ******delete, make new constructor without title
		Plotter p = new Plotter(csvName, tableTitle);
//		p.genScatter();

		// Trendline Fitter
		DataIO d = new DataIO(csvName);
		
		d.S3List(csvName, "AKIAXMLIKAXCVRHQMZU3", "reHCYWJ6SIFbRCflBFRm5WGVOe0SjFlRmRpmKMut");//aKey, sKey); //////////////
		//d.CSVList();
		
		Fitter f = new Fitter(d.getX(), d.getY()); // raw data to fit to
		f.curveFitter();
		double[] coeff = f.getCoeff(); // double[]

		// OBSERVED convert to double[]
		double[] xObs = arrayList2Array(d.getX());
		double[] yObs = arrayList2Array(d.getY());
		
		// EXPECTED convert to double[]
		ContinuousToDiscrete c2D = new ContinuousToDiscrete(coeff);
		double[] xExp = c2D.removeDuplicates(d.getX());
		double[] yExp = f.discretize(xExp);

		// Generate figure overlay
		p.multiPlot(xObs, yObs, xExp, yExp); // double[] xObs, double[] yObs, double[] xExp, double[] yExp);

		// Compute correlation coefficient
		CorrCoef cor = new CorrCoef();
		double r2 = cor.computeR2(d.getX(), d.getY());
		System.out.println("r2 = "+ r2);

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
