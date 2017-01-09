package br.cefet.alc.metodosdiretos.compactados;

import br.cefet.alc.matrix.MatrizCSR;
import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;


/**
 * @author rtavares
 */
public class DecomposicaoLUComp implements Metodo {
	
	public static void main(String[] args){
		
		double[][] matriz = new double[][]{
				
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				};
		
		try{
			
			new DecomposicaoLUComp().executar(matriz);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matriz Original: ");
		Util.get().escreveMatriz(matriz);
		
		MatrizCSR cm = new MatrizCSR(matriz);
		
		Util.get().escrever("");
		Util.get().escrever("Matriz Compactada: ");
		Util.get().escreveMatrizCSR(cm);
		
		int m = cm.getN();
		
		int[] permut = new int[m];
		
		for (int i = 0; i < m; i++)
			permut[i] = i;
		
		double[] b = cm.getB();
		
		MatrizCSR LU = getLU(cm, permut);
		
		Util.get().escrever("");
 		Util.get().escrever("LU: ");
        Util.get().escreveMatrizCSR(LU);
		
        MatrizCSR L = getL(LU);
		
		Util.get().escrever("");
 		Util.get().escrever("L: ");
        Util.get().escreveMatrizCSR(L);
		
        MatrizCSR U = getU(LU);
        
		Util.get().escrever("");
        Util.get().escrever("U: ");
        Util.get().escreveMatrizCSR(U);
		
        double[] bPerm = new double[m];
        for(int i = 0; i < m; i++)
        	bPerm[i] = b[permut[i]];
        
		// L * y = b
		
		double[] y = new double[m];
		
		for(int i = 0; i < m; i++){
			
			double soma = 0;
			
			for(int j = 0; j < m; j++){
				
				if(i == j)
					continue;
				
				soma = soma + (L.getValor(i, j) * y[j]);
				
			}
			
			y[i] = (bPerm[i] - soma) / L.getValor(i, i);
			
		}
		
		Util.get().escrever("");
 		Util.get().escrever("Y: ");
        Util.get().escreveVetor(y);
		
		// U * x = y
		
		double[] x = new double[m];
		
		for(int i = (m - 1); i >= 0; i--){
			
			double soma = 0;
			
			for(int j = (m - 1); j >= 0; j--){
				
				if(i == j)
					continue;
				
				soma = soma + (U.getValor(i, j) * x[j]);
				
			}
			
			x[i] = (y[i] - soma) / U.getValor(i, i);
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("X: ");
		Util.get().escreveVetor(x);
		
		return x;
		
	}
	
	public MatrizCSR getLU(MatrizCSR cm, int[] permut){
		
		MatrizCSR LU = cm;
		
		int m = cm.getN();
		int n = cm.getN();
		
		int pivot = 1;
		double[] LUlinhai;
		double[] LUcolunaj = new double[m];

	    for (int j = 0; j < n; j++) {

	         for (int i = 0; i < m; i++)
	        	 LUcolunaj[i] = LU.getValor(i, j);
	        	 //LUcolunaj[i] = LU[i][j];
	         
	         for (int i = 0; i < m; i++) {
	        	 
	        	 LUlinhai = LU.getLinha(i);
	        	 //LUlinhai = LU[i];

	        	 int kmax = Math.min(i,j);
	        	 double soma = 0.0;
	            
	        	 for (int k = 0; k < kmax; k++)
	            	soma += LUlinhai[k]*LUcolunaj[k];
	            

	        	 LUlinhai[j] = LUcolunaj[i] -= soma;
	        	 
	         }
	   
	         int p = j;
	         
	         for (int i = j+1; i < m; i++) {
	            
	        	 if (Math.abs(LUcolunaj[i]) > Math.abs(LUcolunaj[p])) {
	               p = i;
	            }
	        	 
	         }
	         
	         if (p != j) {
	            
	        	 for (int k = 0; k < n; k++) {
	               
	        		 double t = LU.getValor(p, k);
	        		 //double t = LU[p][k]; 
	        		 
	        		 LU.setValor(p, k, LU.getValor(j, k));
	        		 //LU[p][k] = LU[j][k]; 
	        		 
	        		 LU.setValor(j, k, t);
	        		 //LU[j][k] = t;
	        		 
	            }
	            
	            int k = permut[p]; 
	            permut[p] = permut[j]; 
	            permut[j] = k;
	            pivot = -pivot;
	            
	         }

	         if (j < m & LU.getValor(j, j) != 0.0) {
	            
	        	 for (int i = j+1; i < m; i++)
	        		 LU.setValor(i, j, LU.getValor(i, j) / LU.getValor(j, j));
	        		 //LU[i][j] /= LU[j][j];
	            
	         }
	         
	      }
	    
	    return LU;
		
	}
	
	public MatrizCSR getL(MatrizCSR LU) {
		
		int n = LU.getN();
		
		MatrizCSR L = new MatrizCSR(n);
		
		for (int i = 0; i < n; i++) {
			
			for (int j = 0; j < n; j++) {
				
				if (i > j) {
					
					L.setValor(i, j, LU.getValor(i, j));
					
				} else if (i == j) {
					
					L.setValor(i, j, 1.0);
					
				}
				/*	
				} else {
					
					L.setValor(i, j, 0.0);
					
				}
				*/
			}
	  }
	  
	  return L;
		
	}
	
	public MatrizCSR getU(MatrizCSR LU) {
		
		int n = LU.getN();
		
		MatrizCSR U = new MatrizCSR(n);
		
		for (int i = 0; i < n; i++) {
			
			for (int j = 0; j < n; j++) {
				
				if (i <= j) {
					
					U.setValor(i, j, LU.getValor(i, j));
				
				}
				/*	
				} else {
					
					U[i][j] = 0.0;
					
				}
				*/
			}
		}
		
		return U;
        
	}

}
