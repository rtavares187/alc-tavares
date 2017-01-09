package br.cefet.alc.metodositerativos.naoestacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class GMRESX implements Metodo {
	
	private static final double e = 0.01;
	private static final int iter = 100;
	
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matriz Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		int n = a[0].length;
		
		double[] x = new double[n];
		
		for(int g = 0; g < iter; g++){
		
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
			
			double[][] hh = new double[j + 2][j + 1];
			for(int i = 0; i <= j; i++){
				
				for(int c = 0; c < (j + 1); c++){
					
					hh[i][c] = h[i][c];
					
				}
				
			}
			
			Util.get().escrever("");
			Util.get().escrever("H:");
			Util.get().escreveMatriz(hh);
			
			double[] p = new double[j + 2];
			p[0] = beta;
			
			for(int i = 0; i < (j + 1); i++){
				
				double ro = Math.sqrt(Math.pow(hh[i][i], 2) + Math.pow(hh[i + 1][i], 2));
				
				double c = hh[i][i] / ro;
				
				double s = hh[i + 1][i] / ro;
				
				hh[i][i] = ro;
				hh[i + 1][i] = 0;
				
				p[i] = (c * p[i]) + (s * p[i + 1]);
				p[i + 1] = (-1 * s * p[i]) + (c * p[i + 1]);
				
			}
			
			Util.get().escrever("");
			Util.get().escrever("HR:");
			Util.get().escreveMatriz(hh);
			
			Util.get().escrever("");
			Util.get().escrever("PR:");
			Util.get().escreveVetor(p);
			
			double[][] hg = new double[j + 1][j + 1];
			double[] pg = new double[j + 1];
			
			for(int i = 0; i < (j + 1); i++){
				
				hg[i] = hh[i];
				pg[i] = p[i];
				
			}
			
			double[][] hI = Util.get().invertMatrix(hg);
			
			Util.get().escrever("");
			Util.get().escrever("HI:");
			Util.get().escreveMatriz(hI);
			
			double[] y = new double[j + 1];
			
			// Retrosubstituição
	        
			for (int i = (y.length - 1); i >= 0; i--) {
	            
	        	double soma = 0.0;
	            
	            for (int c = i + 1; c < hg[i].length; c++) {
	            	soma += hg[i][c] * y[c];
	            }
	            
	            y[i] = (pg[i] - soma) / hg[i][i];
	            
	        }
			
			Util.get().escrever("");
			Util.get().escrever("Y: ");
			Util.get().escreveVetor(y);
			
			vv = Util.get().getMatrizTransposta(vv);
			
			x = Util.get().soma(x, Util.get().multiplicacao(vv, y));
			
			Util.get().escrever("");
			Util.get().escrever("X: ");
			Util.get().escreveVetor(x);
			
			if(criterioParada(beta, hg, y))
				break;
			
		}
		
		return x;
		
	}
	
	private boolean criterioParada(double beta, double[][] h, double[] y){
		
		double[] e1 = new double[h.length];
		e1[0] = 1;
		
		double ee = Util.get().normaE(Util.get().subtracao(Util.get().multiplicacao(e1, beta), Util.get().multiplicacao(h, y)));
		
		if(ee <= e)
			return true;
		
		return false;
		
	}

}
