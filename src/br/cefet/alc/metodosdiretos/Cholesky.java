package br.cefet.alc.metodosdiretos;

import br.cefet.alc.metodosdiretos.util.Util;


/**
 * @author rtavares
 */
public class Cholesky implements Metodo {

	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception{
		
		Util.get().escrever("Matriz original:");
		Util.get().escrever("");
		Util.get().escreveMatriz(matriz);
		
		Util.get().escrever("");
		Util.get().escrever("a: ");
		Util.get().escrever("");
		Util.get().escreveMatriz(Util.get().getMatrizA(matriz));
		
		DecomposicaoLU dlu = new DecomposicaoLU();
		
		double[] bL = Util.get().getVetorB(matriz);
		
		Util.get().escrever("");
		Util.get().escrever("b: ");
		Util.get().escrever("");
		Util.get().escreveVetor(Util.get().getVetorB(matriz));
		
		double[][] L = dlu.getL(matriz, bL);
		
		double[][] Lt = Util.get().getMatrizTransposta(L);
		
		Util.get().escrever("");
		Util.get().escrever("Lt: ");
        Util.get().escreveMatriz(Lt);
		
		// L * y = b
		
		double[] y = new double[L.length];
		
		//double[] b = Util.get().getVetorB(matriz);
		
		for(int i = 0; i < L.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < L[i].length; j++){
				
				if(i == j)
					continue;
				
				soma = soma + L[i][j] * y[j];
				
			}
			
			y[i] = (bL[i] - soma) / L[i][i];
			
		}
		
		// Lt * x = y
		
		double[] x = new double[Lt.length];
		
		for(int i = (Lt.length - 1); i >= 0; i--){
			
			double soma = 0;
			
			for(int j = (Lt.length - 1); j >= 0; j--){
				
				if(i == j)
					continue;
				
				soma = soma + Lt[i][j] * x[j];
				
			}
			
			x[i] = (y[i] - soma) / Lt[i][i];
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("x: ");
		Util.get().escreveVetor(y);
		
		return y;
		
	}
	
}
