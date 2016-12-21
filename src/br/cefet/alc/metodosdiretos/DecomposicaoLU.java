package br.cefet.alc.metodosdiretos;

import br.cefet.alc.metodosdiretos.util.Util;


/**
 * @author rtavares
 */
public class DecomposicaoLU implements Metodo {

	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[] bL = Util.get().getMatrizB(matriz);
		double[][] L = getL(matriz, bL);
		
		double[] bU = Util.get().getMatrizB(matriz);
		double[][] U = getU(matriz, bU);
        
		// L * y = b
		
		//double[] b = Util.get().getMatrizB(matriz);
		
		double[] y = new double[L.length];
		
		for(int i = 0; i < L.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < L[i].length; j++){
				
				if(i == j)
					continue;
				
				soma = soma + (L[i][j] * y[j]);
				
			}
			
			y[i] = (bL[i] - soma) / L[i][i];
			
		}
		
		// U * x = y
		
		double[] x = new double[U.length];
		
		for(int i = (U.length - 1); i >= 0; i--){
			
			double soma = 0;
			
			for(int j = (U.length - 1); j >= 0; j--){
				
				if(i == j)
					continue;
				
				soma = soma + (U[i][j] * x[j]);
				
			}
			
			x[i] = (y[i] - soma) / U[i][i];
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("x: ");
		Util.get().escreveVetor(y);
		
		return y;
		
	}
	
	public double[][] getL(double[][] matriz, double[] b) {
		
		double[][] L = Util.get().getMatrizA(matriz);
		
		int qtdLinhas  = b.length;
		
        for (int p = (qtdLinhas - 1); p >= 0; p--) {
        	
        	// Localiza maior valor da coluna
        	
            int max = p;
            
            for (int i = (p - 1); i >= 0; i--) {
                
            	if (Math.abs(L[i][p]) > Math.abs(L[max][p])) {
                    max = i;
                }
            	
            }
            
            // Reordena a matriz
            
            double[] temp = L[p]; 
            L[p] = L[max]; 
            L[max] = temp;
            
            double t = b[p]; 
            b[p] = b[max]; 
            b[max] = t;

            for (int i = p - 1; i >= 0; i--) {
                
            	double fator = L[i][p] / L[p][p];
                
            	b[i] -= fator * b[p];
                
                for (int j = p; j >= 0; j--) {
                    L[i][j] -= fator * L[p][j];
                }
                
            }
            
        }
        
        // Preenche a diagonal com 1
		
 		for(int i = 0; i < L.length; i++){
 			
 			double fator = L[i][i];
 			
 			for(int j = 0; j < L[i].length; j++){
 				
 				L[i][j] = L[i][j] / fator;
 				
 			}
 			
 			b[i] = b[i] / fator;
 			
 		}
        
 		Util.get().escrever("");
 		Util.get().escrever("L: ");
        Util.get().escreveMatriz(L);
		
        return L;
		
	}
	
	public double[][] getU(double[][] matriz, double[] b) {
		
		double[][] U = Util.get().getMatrizA(matriz);
		
		int qtdLinhas  = b.length;
		
        for (int p = 0; p < qtdLinhas; p++) {
        	
        	// Localiza maior valor da coluna
        	
            int max = p;
            
            for (int i = p + 1; i < qtdLinhas; i++) {
                
            	if (Math.abs(U[i][p]) > Math.abs(U[max][p])) {
                    max = i;
                }
            	
            }
            
            // Reordena a matriz
            
            double[] temp = U[p]; 
            U[p] = U[max]; 
            U[max] = temp;
            
            double t = b[p]; 
            b[p] = b[max]; 
            b[max] = t;

            for (int i = p + 1; i < qtdLinhas; i++) {
                
            	double fator = U[i][p] / U[p][p];
                
            	b[i] -= fator * b[p];
                
                for (int j = p; j < qtdLinhas; j++) {
                    U[i][j] -= fator * U[p][j];
                }
                
            }
            
        }
        
        Util.get().escrever("");
        Util.get().escrever("U: ");
        Util.get().escreveMatriz(U);
		
        return U;
        
	}

}
