package br.cefet.alc.metodosdiretos;

import br.cefet.alc.util.Util;


/**
 * @author rtavares
 */
public class Cholesky implements Metodo {
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception{
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		double[][] L = getL(a);
		
		Util.get().escrever("");
 		Util.get().escrever("L: ");
        Util.get().escreveMatriz(L);
		
		double[][] Lt = Util.get().getMatrizTransposta(L);
		
		Util.get().escrever("");
		Util.get().escrever("Lt: ");
        Util.get().escreveMatriz(Lt);
		
        // L * y = b
		
        double[] y = new double[L.length];
		
		for(int i = 0; i < L.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < L[i].length; j++){
				
				if(i == j)
					continue;
				
				soma = soma + (L[i][j] * y[j]);
				
			}
			
			y[i] = (b[i] - soma) / L[i][i];
			
		}
		
		Util.get().escrever("");
 		Util.get().escrever("Y: ");
        Util.get().escreveVetor(y);
		
		// Lt * x = y
		
        double[] x = new double[Lt.length];
		
		for(int i = (Lt.length - 1); i >= 0; i--){
			
			double soma = 0;
			
			for(int j = (Lt.length - 1); j >= 0; j--){
				
				if(i == j)
					continue;
				
				soma = soma + (Lt[i][j] * x[j]);
				
			}
			
			x[i] = (y[i] - soma) / Lt[i][i];
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("X: ");
		Util.get().escreveVetor(x);
		
		return x;
		
	}
	
	public double[][] getL(double[][] a){
		
		int n = a.length;
		double[][] L = new double[n][n];
		boolean isqpd = a[0].length == n;
		
		for (int j = 0; j < n; j++) {
			
			double[] Llinhaj = L[j];
			double d = 0.0;
			
			for (int k = 0; k < j; k++) {
				
				double[] Llinhak = L[k];
				double s = 0.0;
				
				for (int i = 0; i < k; i++)
	               s += Llinhak[i]*Llinhaj[i];
	            
				Llinhaj[k] = s = (a[j][k] - s)/L[k][k];
	            d = d + s*s;
	            isqpd = isqpd & (a[k][j] == a[j][k]);
	            
	         }
			
	         d = a[j][j] - d;
	         isqpd = isqpd & (d > 0.0);
	         L[j][j] = Math.sqrt(Math.max(d,0.0));
	         
	         for (int k = j+1; k < n; k++)
	            L[j][k] = 0.0;
	         
		}
		
		return L;
		
	}
	
}
