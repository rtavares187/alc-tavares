package br.cefet.alc.metodosdiretos;

import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class RegraDeCramer implements Metodo {

	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}

	@Override
	public double[] getX(double[][] matriz) throws Exception {
		
		double[][] a = Util.get().getMatrizA(matriz);
		
		double[] b = Util.get().getVetorB(matriz);
		
		Util.get().escrever("Matriz A: ");
		
		Util.get().escreveMatriz(a);
		
		Util.get().escrever("");
		Util.get().escrever("Vetor B: ");
		
		Util.get().escreveVetor(b);
		
		double dA = Util.get().getDeterminante(a);
		
		Util.get().escrever("");
		Util.get().escrever("Determinante de A: " + dA);
		
		double[] x = new double[a[0].length];
		
		for(int i = 0; i < a[0].length; i++) {
			
			double[][] aX = Util.get().getMatrizAX(a, b, i);
			
			Util.get().escrever("");
			Util.get().escrever("Matriz A" + i);
			Util.get().escreveMatriz(aX);
			
			double dX = Util.get().getDeterminante(aX);
			
			Util.get().escrever("");
			Util.get().escrever("Determinante de A" + i + " : " + dX);
			
			double vX = dX / dA;
			
			Util.get().escrever("");
			Util.get().escrever("x" + i + " = " + vX);
			
			x[i] = vX;
			
		}
		
		return x;
		
	}

}
