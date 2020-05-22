package dataVisualization;

import java.io.*; 
import java.util.*;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints; 

public class Fitter {
	private ArrayList<Double> x;
	private ArrayList<Double> y;
	//private ArrayList<Double> coeff;
	private double[] coeff;
	
	public Fitter(ArrayList<Double> x, ArrayList<Double> y) {
		this.x = x;
		this.y = y;
	}
	
	public void curveFitter() {
		// Collect data.
		System.out.println("inside curvefitter");
		final WeightedObservedPoints obs = new WeightedObservedPoints();
		
		for(int i = 0; i<x.size(); i++) {
			obs.add( x.get(i), y.get(i) );
			System.out.println("x.get(i) = " + x.get(i) );
		}
		
		// Instantiate fitter
		final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
		System.out.println("instantiated fitter");
		
		// Compute Coefficients
		double[] coeffDouble = fitter.fit(obs.toList());
		System.out.println("computed coefficients");
		
		// Extract Coefficients
//		for(int j = 0; j<coeffDouble.length; j++) {
//			System.out.println("coeffDouble[j]" + coeffDouble[j]);
//			Double temp = Double.valueOf(coeffDouble[j]);
//			System.out.println("here");
//			this.coeff.add( temp );
//			System.out.println("done cycle j = " + j);
//		}
		coeff = coeffDouble;
		System.out.println("This works");
	}
	
	
	
	
	public double[] getCoeff() {
		return coeff;
	}

	public void setCoeff( double[] coeff) {
		this.coeff = coeff;
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
}
