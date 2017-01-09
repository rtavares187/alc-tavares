package br.cefet.alc.metodositerativos.naoestacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class GMRES implements Metodo {
	
	private static final double e = 0.01;
	private static final int iter = 50;
	
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		int n = a[0].length;
		
		double[] x = new double[n];
		
		double[] aX = Util.get().multiplicacao(a, x);
		
		double[] r = Util.get().subtracao(b, aX);
		
		double beta = Util.get().normaE(r);
		
		double[] v0 = Util.get().divisao(r, beta);
		
		double[][] v = new double[iter + 1][n];
		v[0] = v0;
		
		double[][] h = new double[iter + 1][iter];
		
		int j = 0;
		
		while(j < iter){
			
			double[] w = Util.get().multiplicacao(a, v[j]);
			
			for(int i = 0; i <= j; i++){
				
				h[i][j] = Util.get().produtoInterno(w, v[i]);
				w = Util.get().subtracao(w, Util.get().multiplicacao(v[i], h[i][j]));
			}
			
			h[j + 1][j] = Util.get().normaE(w);
			
			if(Util.get().equalsZero(h[j + 1][j]))
				break;
			
			v[j + 1] = Util.get().divisao(w, h[j + 1][j]);
			
			j++;
			
		}
		
		double[][] vv = new double[j + 1][n];
		for(int i = 0; i < (j + 1); i++)
			vv[i] = v[i];
		
		Util.get().escrever("");
		Util.get().escrever("Base:");
		Util.get().escreveMatriz(vv);
		
		double[][] hh = new double[j + 1][j + 1];
		for(int i = 0; i <= j; i++){
			
			for(int c = 0; c < (j + 1); c++){
				
				hh[i][c] = h[i][c];
				
			}
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("H:");
		Util.get().escreveMatriz(hh);
		
		double[] p = new double[j + 1];
		p[0] = beta;
		
		double[][] ls = Util.get().leastSquare(hh, p);
		double[] y = new double[ls.length];
		for(int i = 0; i < y.length; i++)
			y[i] = ls[i][0];
		
		vv = Util.get().getMatrizTransposta(vv);
		
		x = Util.get().soma(x, Util.get().multiplicacao(vv, y));
		
		Util.get().escrever("");
		Util.get().escrever("X");
		
		Util.get().escreveVetor(x);
		
		return x;
		
	}
	
	public boolean convergence(double[] r){
		
		if(Util.get().normaE(r) <= e)
			return true;
		
		return false;
		
	}

}
