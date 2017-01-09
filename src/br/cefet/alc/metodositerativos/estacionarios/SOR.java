package br.cefet.alc.metodositerativos.estacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class SOR implements Metodo{
	
	private static final double E = 0.001;
	
	private double w = 1;
	
	public SOR(){}
	
	public SOR(double w){
		
		this.w = w;
		
	}
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		/*
		boolean convergeSolucao = convergeSolucao(matriz);
		
		if(!convergeSolucao)
			throw new Exception("Essa matriz n�o converge para a solu��o pelo m�todo de SOR - Sassenfeld.");
		*/
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
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
				
				xN[i] = ((1 - w) * x[i]) + ( w * ((b[i] + acum)) / a[i][i]);
				
			}
			
			Util.get().escrever("");
			Util.get().escrever("x parcial ( " + iteracoes + " ):");
			Util.get().escreveVetor(xN);
		
		}
		
		Util.get().escrever("");
		Util.get().escrever("Itera��es: " + iteracoes);
		Util.get().escrever("");
		Util.get().escrever("Solu��o vetor X: ");
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
	
	public boolean convergeSolucao(double[][] matriz){
		
		double[][] a = Util.get().getMatrizA(matriz);
		
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
