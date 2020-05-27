package dataVisualization;

import java.util.ArrayList;
import java.util.Arrays;

public class CorrCoef {
	/**
	 * Manual Method to compute correlation coefficient
	 */
	private ArrayList<Double> x;
	private ArrayList<Double> y;
	private double xBar;
	private double yBar;

	
	public double computeR2(ArrayList<Double> x, ArrayList<Double> y) {
		this.xBar = computeAverage(x);
		this.yBar = computeAverage(y);
		ArrayList<Double> xMinusXBar = tMinusTBar(x, xBar);
		ArrayList<Double> yMinusyBar = tMinusTBar(y, yBar);
		ArrayList<Double> xxBarProdyyBar = arrayProduct( xMinusXBar, yMinusyBar);
		ArrayList<Double> xxBar2 = arrayProduct( xMinusXBar, xMinusXBar);
		ArrayList<Double> yyBar2 = arrayProduct( yMinusyBar, yMinusyBar);
		double temp0 = sumArray( xxBarProdyyBar );
		double temp1 = sumArray( xxBar2 );
		double temp2 = sumArray( yyBar2 );
		if( (temp1*temp2) != 0 ) {
			double r  = temp0 / ( Math.sqrt(temp1*temp2) );
			return r*r;
		}
		return 0; // avoid undefined
	}
	
	public double computeAverage(ArrayList<Double> t) {
		double sum = 0;
		if (!t.isEmpty()) {
			for (double xx : t) {
				sum += xx;
			}
			return sum / t.size();
		}
		return sum;
	}

	public ArrayList<Double> tMinusTBar(ArrayList<Double> t, double tBar) {
		ArrayList<Double> diff = new ArrayList<Double>();
		for (int i = 0; i < t.size(); i++) {
			diff.add(i, t.get(i).doubleValue() - tBar);
		}
		return diff;
	}
	
	public ArrayList<Double> arrayProduct(ArrayList<Double> a, ArrayList<Double> b){
		ArrayList<Double> prod = new ArrayList<Double>();
		for (int i = 0; i < a.size(); i++) {
			prod.add(i, a.get(i).doubleValue() * b.get(i).doubleValue() );
		}
		return prod;	
	}

	public double sumArray(ArrayList<Double> t) {
		double sum = 0;
			for (double xx : t) {
				sum += xx;
			}
		return sum;
	}
}
