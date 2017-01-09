package br.cefet.alc.metodosdiretos.compactados;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class GradienteModificadoComp implements Metodo {
	
	private static final double e = 0.01;
	
	private static final int t = 1;
	
	private static final int maxIter = 10000;
	
	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getX(matriz);
		
	}
	
	public double[] getX(double[][] matriz){
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		double[] x = new double[a.length];
		double[] aX = Util.get().multiplicacao(a, x);
		
		double[] r = Util.get().subtracao(b, aX);
		double[] s = r;
		
		double n = 0;
		
		int iteracao = 0;
		
		while(!convergeSolucao(s, iteracao) && iteracao <= maxIter){
			
			iteracao++;
			
			double[] aS = Util.get().multiplicacao(a, s);
			
			double l = Util.get().multiplicacaoVTV(r) / Util.get().multiplicacaoVetor(s, aS);
			
			double lS[] = Util.get().multiplicacao(s, l);
			
			x = Util.get().soma(x, lS);
			
			double[] lAS = Util.get().multiplicacao(aS, l);
			
			double[] rA = r;
			
			r = Util.get().subtracao(r, lAS);
			
			double[] y = Util.get().subtracao(r, rA);
			
			double lambda = 1;
			double beta = 1;
			
			if(Util.get().multiplicacaoVetor(r, s) > 0){
				
				lambda = 1 + ((Util.get().multiplicacaoVetor(r, s) / Util.get().multiplicacaoVetor(s, y) * (Util.get().multiplicacaoVetor(r, y) / Util.get().normaE(r))));
				
				double betaDHS = -1 * (Util.get().multiplicacaoVetor(rA, s) / Util.get().multiplicacaoVetor(s, y));
				
				double min = Util.get().normaE(rA) < n ? Util.get().normaE(rA) : n;
				
				double nk = -1 / (Util.get().normaE(s) * min);
				
				double betaD = betaDHS > n ? betaDHS : n;
				
				beta = betaD;
				
				n = nk;
				
			}else{
				
				double betaHS = -1 * t * ((Util.get().normaE(y) * Util.get().multiplicacaoVetor(r, s)) / Math.pow(Util.get().multiplicacaoVetor(s, y), 2));
				
				beta = betaHS;
				
			}
			
			s = Util.get().soma(Util.get().multiplicacao(r, lambda), Util.get().multiplicacao(s, beta));
			
			Util.get().escrever("");
			Util.get().escrever("x parcial ( " + iteracao + " ):");
			Util.get().escreveVetor(x);
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("Iterações: " + iteracao);
		Util.get().escrever("");
		Util.get().escrever("Solução vetor X: ");
		Util.get().escreveVetor(x);
		Util.get().escrever("");
		Util.get().escrever(" s: ");
		Util.get().escreveVetor(s);
		
		return x;
		
	}
	
	public boolean convergeSolucao(double[] r, int iteracao) {
		
		if(iteracao == 0)
			return false;
		
		double soma = 0;
		
		for(int i = 0; i < r.length; i++){
			
			soma += Math.pow(r[i], 2);
			
		}
		
		soma = Math.sqrt(soma);
		
		if(soma < e)
			return true;
		
		return false;
		
	}

}
