package br.cefet.alc.metodosdiretos;

/**
 * @author rtavares
 */
public interface Metodo {
	
	void executar(double[][] matriz) throws Exception;
	
	double[] getX(double[][] matriz) throws Exception;
	
}
