package dataVisualization;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.*;
import java.util.List;

// AWS S3 Connections
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class Visualization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");

		/**
		 * Test
		 */
		ArrayList<Double> var = new ArrayList<Double>();
		var.add(12.0);
		var.add(0.0);
		var.forEach(System.out::println);
		/**
		 * end test
		 */

//		/**
//		 * Load from Amazon Bucket
//		 */
//		EntryPoint service = new EntryPoint();
//		AWSCredentials credentials = new BasicAWSCredentials(S3Constants.ACCESS_KEY_ID, S3Constants.ACCESS_SEC_KEY);
//		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
//				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
//		service.getObj(s3client);
//		/**
//		 * 
//		 */
		
		///////////////////////////////

//		// Display Scatter Graph
		String csvName = "C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/dataVisualization/sample.csv";
		String tableTitle = "Test Title";
		Plotter p = new Plotter(csvName, tableTitle);
//		p.genScatter();

		// Fitter
		DataIO d = new DataIO(csvName);
		d.CSVList();
		Fitter f = new Fitter(d.getX(), d.getY()); // raw data to fit to
		f.curveFitter();
		double[] coeff = f.getCoeff(); // double[]

		// Plot overlay trendline and scatterplot
		// Generate data for trendline - DONE

		// OBSERVED convert to double[]
		double[] xObs = arrayList2Array(d.getX());
		double[] yObs = arrayList2Array(d.getY());
		
		// EXPECTED convert to double[]
		ContinuousToDiscrete c2D = new ContinuousToDiscrete(coeff);
		double[] xExp = c2D.removeDuplicates(d.getX());
		double[] yExp = f.discretize(xExp);

		// Generate figure
		p.multiPlot(xObs, yObs, xExp, yExp); // double[] xObs, double[] yObs, double[] xExp, double[] yExp);

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
