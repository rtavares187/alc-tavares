package br.cefet.alc.metodositerativos.estacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class GaussSeidelSassenfeld implements Metodo{
	
	private static final double E = 0.05;
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		return getX(a, b);
		
	}
	
	public double[] getX(double[][] a, double[] b) throws Exception {
		
		return getX(a, b, null);
		
	}
	
	public double[] getX(double[][] a, double[] b, Integer maxIter) throws Exception {
		
		boolean convergeSolucao = convergeSolucao(a);
		
		if(!convergeSolucao)
			throw new Exception("Essa matriz não converge para a solução pelo método de GaussSeidelSassenfeld.");
		
		double[] x = new double[a.length];
		double[] xN = new double[a.length];
		
		int iteracoes = 0;
		
		while(!criterioParadaLinhas(x, xN, iteracoes)){
			
			iteracoes++;
			
			for(int i = 0; i < x.length; i++)
				x[i] = xN[i];
			
			for(int i = 0; i < a.length; i ++){
				
				Double acum = null;
				
				for(int j = 0; j < a[i].length; j++){
					
					if(i == j)
						continue;
					
					if(acum == null)
						acum = -1 * a[i][j] * xN[j];
					
					else
						acum = acum + (-1 * a[i][j] * xN[j]);
					
				}
				
				xN[i] = (b[i] + acum) / a[i][i];
				
			}
			
			Util.get().escrever("");
			Util.get().escrever("x parcial ( " + iteracoes + " ):");
			Util.get().escreveVetor(xN);
			
			if(maxIter != null && iteracoes >= maxIter)
				break;
		
		}
		
		Util.get().escrever("");
		Util.get().escrever("Iterações: " + iteracoes);
		Util.get().escrever("");
		Util.get().escrever("Solução vetor X: ");
		Util.get().escreveVetor(xN);
		
		return xN;
		
	}
	
	public boolean criterioParadaLinhas(double[] x, double[] xN, int iteracoes){
		
		if(iteracoes == 0)
			return false;
		
		double maxDif = 0;
		
		for(int i = 0; i < x.length; i++){
			
			double dif = Math.abs(x[i] - xN[i]);
			
			if(dif > maxDif)
				maxDif = dif;
			
		}
		
		if(maxDif < E)
			return true;
		
		return false;
		
	}
	
	public boolean convergeSolucao(double[][] a){
		
		double maxB = 0;
		
		Double[] B = new Double[a.length];
		
		for(int i = 0; i < a.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < a[i].length; j++){
				
				if(j == i)
					continue;
				
				double bFator = 1;
				
				if(B[j] != null)
					bFator = B[j];
				
				soma = soma + (bFator * Math.abs(a[i][j]));
				
			}
			
			double bI = soma / Math.abs(a[i][i]);
			
			if(bI > maxB)
				maxB = bI;
			
			B[i] = bI;
			
		}
		
		if(maxB < 1)
			return true;
		
		return false;
		
	}
	
}
