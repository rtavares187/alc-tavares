package br.cefet.alc.metodositerativos.naoestacionarios;

import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.metodositerativos.estacionarios.GaussSeidelSassenfeld;
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class MultiGrid implements Metodo{
	
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
		
		// Suavizar com GS 2x
		
		GaussSeidelSassenfeld gs = new GaussSeidelSassenfeld();
		
		double[] x = gs.getX(a, b, 2);
		
		int iteracoes = 0;
		
		while(!criterioParada(a, b, x, iteracoes)){
			
			iteracoes++;
			
			// Restricao - 2h
			
			double[] aX = Util.get().multiplicacao(a, x);
			double[] r = Util.get().subtracao(b, aX);
			
			double[] e2h = gs.getX(a, r);
			
			double[] ae2h = Util.get().multiplicacao(a, e2h);
			
			double[] r2h = Util.get().subtracao(r, ae2h);
			
			// Restricao de R - 4h
			
			double[] e4h = gs.getX(a, r2h);
			
			// Prolongamento - 2h
			
			e2h = Util.get().soma(e2h, e4h);
			
			// Prolongamento - h
			
			x = Util.get().soma(x, e2h);
			
			Util.get().escrever("");
			Util.get().escrever("x parcial ( " + iteracoes + " ):");
			Util.get().escreveVetor(x);
			
		}
		
		Util.get().escrever("");
		Util.get().escrever("--- Fim Multigrid ---");
		Util.get().escrever("Iterações: " + iteracoes);
		Util.get().escrever("");
		Util.get().escrever("Solução vetor X: ");
		Util.get().escreveVetor(x);
		
		return x;
		
	}
	
	public boolean criterioParada(double[][] a, double[] b, double[] x, int iteracoes){
		
		if(iteracoes == 0)
			return false;
		
		double[] aX = Util.get().multiplicacao(a, x);
		double[] r = Util.get().subtracao(b, aX);
		double nE = Util.get().normaE(r);
		
		if(nE < E)
			return true;
		
		return false;
		
	}
	
}
