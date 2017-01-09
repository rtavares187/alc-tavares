package br.cefet.alc.util;

/**
 * @author rtavares
 */
public class GaussResultado {
	
	private double[][] a;
	
	private double[] b;
	
	private double[] x;
	
	public GaussResultado(double[][] a, double[] b, double[] x){
		
		this.a = a;
		this.b = b;
		this.x = x;
		
	}

	public double[][] getA() {
		return a;
	}

	public double[] getB() {
		return b;
	}

	public double[] getX() {
		return x;
	}
	
}
