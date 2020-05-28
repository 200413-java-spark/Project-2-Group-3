package com.github.p2g3.dataVisualization;

import java.io.*; 
import java.util.*;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints; 
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction; // II


public class Fitter {
	private ArrayList<Double> x;
	private ArrayList<Double> y;
	//private ArrayList<Double> coeff;
	private double[] coeff;
	private double[] fittedCurve; //II
	
	
	public Fitter(ArrayList<Double> x, ArrayList<Double> y) {
		this.x = x;
		this.y = y;
	}
	
	public void curveFitter() {
		// Collect data.
		final WeightedObservedPoints obs = new WeightedObservedPoints();
		for(int i = 0; i<x.size(); i++) {
			obs.add( x.get(i), y.get(i) );
		}
		
		// Instantiate fitter
		final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
		
		// Compute Coefficients
		double[] coeffDouble = fitter.fit(obs.toList());
		coeff = coeffDouble;
	}
	
	public double[] discretize(double[] xExp) {
		PolynomialFunction function = new PolynomialFunction(this.coeff);

		double[] fitted = new double[xExp.length];

		for (int i = 0; i < xExp.length; i++) {
			fitted[i] = function.value(xExp[i]);
		}

		this.fittedCurve = fitted;
		return this.fittedCurve;
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

	public double[] getFittedCurve() { //II
		return fittedCurve;
	}

	public void setFittedCurve(double[] fittedCurve) { //II
		this.fittedCurve = fittedCurve;
	}
	
	
}
