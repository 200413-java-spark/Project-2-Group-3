package dataVisualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * This class runs the data visualization section of the big data pipeline with
 * the main method.
 * 
 * @author Revature Team 3 (Cynthia, Phuc, Christian, Brandon)
 * @version 0.1.0
 */
public class Visualization {
	/**
	 * The main method loads csv from Amazon AWS S3 bucket, uses a polynomial fitter
	 * to return coefficients for the dataset, and displays a overlay of the fitted
	 * trendline and raw data scatter plot. The polynomial fitter coefficients and
	 * the R^2 correlation correficient are persisted to SQL database.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		// Load CSV
		DataIO d = new DataIO();
		d.S3List("AKIAXMLIKAXCQDB67KVH", "okyNPlSBPJPKiBY6OaF0A7V2e4jX92/EMXJjik+2");//("<accessKey>", "<securityKey>");

		// Fit for Coefficients
		Fitter f = new Fitter(d.getX(), d.getY());
		f.curveFitter();
		double[] coeff = f.getCoeff(); // ************ OUTPUT TO POSTGRES
		System.out.println("b and m = " + Arrays.toString(coeff));

		// OBSERVED convert to double[]
		double[] xObs = arrayList2Array(d.getX());
		double[] yObs = arrayList2Array(d.getY());

		// EXPECTED convert to double[]
		ContinuousToDiscrete c2D = new ContinuousToDiscrete(coeff);
		double[] xExp = c2D.removeDuplicates(d.getX());
		double[] yExp = f.discretize(xExp);

		// Compute correlation coefficient
		CorrCoef cor = new CorrCoef();
		double r2 = cor.computeR2(d.getX(), d.getY()); ///////////// *******OUTPUT TO POSTGRES
		System.out.println("r2 = " + r2);

	}

	/**
	 * This method converts a Double Arraylist to a double array.
	 * 
	 * @param ArrayList typed list
	 * @return array typed list
	 */
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
