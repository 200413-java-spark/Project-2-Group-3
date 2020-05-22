package dataVisualization; // SQL, or s3 input connection

import java.util.Arrays;

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
	
	public double[] removeDuplicates(double[] xObs) {
		Arrays.sort(xObs); // no return, sorts itself
		int n = xObs.length;		
		
		// Return, if array is empty or contains a single element 
        if (n==0 || n==1) {
            return xObs; 
        }
        
        double[] temp = new double[n]; 
          
        // Start traversing elements 
        int j = 0; 
        for (int i=0; i<n-1; i++) 
            // If current element is not equal to next element then store that current element 
            if (xObs[i] != xObs[i+1]) 
                temp[j++] = xObs[i]; 
          
        // Store the last element as whether it is unique or repeated, it hasn't stored previously 
        temp[j++] = xObs[n-1];    
          
        // Modify original array 
        for (int i=0; i<j; i++) { 
            xObs[i] = temp[i]; 
        }
        
		this.xExp = xObs;
		return this.xExp;
	}
}
