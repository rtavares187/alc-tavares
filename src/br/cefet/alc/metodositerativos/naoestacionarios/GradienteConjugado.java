package br.cefet.alc.metodositerativos.naoestacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.metodosdiretos.util.Util;

/**
 * @author rtavares
 */
public class GradienteConjugado implements Metodo {
	
	private static final double e = 0.01;
	
	private static final int maxIter = 100;
	
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
			
			double beta = Util.get().multiplicacaoVTV(r) / Util.get().multiplicacaoVTV(rA);
			
			double[] betaS = Util.get().multiplicacao(s, beta);
			
			s = Util.get().soma(r, betaS);
			
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
