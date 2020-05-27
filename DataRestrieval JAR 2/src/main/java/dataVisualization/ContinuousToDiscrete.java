package dataVisualization; // SQL, or s3 input connection

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class ContinuousToDiscrete {
	private double[] coeff;
	private double[] fittedCurve; //y
	private double[] xExp; //x no repeats

	public ContinuousToDiscrete(double[] coeff) {
		this.coeff = coeff;
	}

	/**
	 * NEED MORE WORK ON THIS 
	 * TRANSFORMING CONTINUOS TO DISCRETE
	 */
	public void discretize() {
		PolynomialFunction function = new PolynomialFunction(this.coeff);
		
		double[] fitted = new double[xExp.length];

		for (int i = 0; i < xExp.length; i++) { 
			fitted[i] = function.value(i);
		}

		this.fittedCurve = fitted;
	}
	
	public double[] removeDuplicates(ArrayList<Double> xObs) {
		Double min = Collections.min(xObs); ///////
		Double max = Collections.max(xObs);		
		double[] tempReturn = new double[(int) (max-min)];
		int cnt = 0;
		for (int i = min.intValue(); i<max.intValue(); i++) {
			tempReturn[cnt] = i;
			cnt++;
		}
		this.xExp = tempReturn;
		return this.xExp;
	}
}
