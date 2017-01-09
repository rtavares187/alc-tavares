package br.cefet.alc.metodositerativos.estacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class GaussSeidelLinhas implements Metodo{
	
	private static final double E = 0.05;
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		boolean convergeSolucao = convergeSolucao(matriz);
		
		if(!convergeSolucao)
			throw new Exception("Essa matriz não converge para a solução pelo método de GaussSeidelLinhas.");
		
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
				
				xN[i] = (b[i] + acum) / a[i][i];
				
			}
			
			Util.get().escrever("");
			Util.get().escrever("x parcial ( " + iteracoes + " ):");
			Util.get().escreveVetor(xN);
		
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
	
	public boolean convergeSolucao(double[][] matriz){
		
		double[][] a = Util.get().getMatrizA(matriz);
		
		double maxL = 0;
		
		for(int i = 0; i < a.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < a[i].length; j++){
				
				if(j == i)
					continue;
				
				soma = soma + Math.abs(a[i][j]);
				
			}
			
			double lI = soma / Math.abs(a[i][i]);
			
			if(lI > maxL)
				maxL = lI;
			
		}
		
		if(maxL < 1)
			return true;
		
		return false;
		
	}
	
}
