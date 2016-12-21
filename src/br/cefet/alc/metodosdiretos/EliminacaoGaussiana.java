package br.cefet.alc.metodosdiretos;

import br.cefet.alc.metodosdiretos.util.GaussResultado;
import br.cefet.alc.metodosdiretos.util.Util;

/**
 * @author rtavares
 */
public class EliminacaoGaussiana implements Metodo {

	@Override
	public void executar(double[][] matriz) throws Exception {
		
		getEliminacaoGauss(matriz); 
		
	}
	
	public GaussResultado getEliminacaoGauss(double[][] matriz) throws Exception {
		
		Util.get().escrever("Matir Original: ");
		Util.get().escreveMatriz(matriz);
		
		double[][] a = Util.get().getMatrizA(matriz);
		double[] b = Util.get().getMatrizB(matriz);
		
		Util.get().escrever("");
		Util.get().escrever("A: ");
        Util.get().escreveMatriz(a);
        
        Util.get().escrever("");
        Util.get().escrever("B: ");
        Util.get().escreveVetor(b);
		
		int qtdLinhas  = b.length;

        for (int p = 0; p < qtdLinhas; p++) {
        	
        	// Localiza maior valor da coluna
        	
            int max = p;
            
            for (int i = p + 1; i < qtdLinhas; i++) {
                
            	if (Math.abs(a[i][p]) > Math.abs(a[max][p])) {
                    max = i;
                }
            	
            }
            
            // Reordena a matriz
            
            double[] temp = a[p]; 
            a[p] = a[max]; 
            a[max] = temp;
            
            double t = b[p]; 
            b[p] = b[max]; 
            b[max] = t;

            for (int i = p + 1; i < qtdLinhas; i++) {
                
            	double fator = a[i][p] / a[p][p];
                
            	b[i] -= fator * b[p];
                
                for (int j = p; j < qtdLinhas; j++) {
                    a[i][j] -= fator * a[p][j];
                }
                
            }
            
        }
        
        // Retrosubstituição
        
        double[] x = new double[qtdLinhas];
        
        for (int i = qtdLinhas - 1; i >= 0; i--) {
            
        	double soma = 0.0;
            
            for (int j = i + 1; j < qtdLinhas; j++) {
            	soma += a[i][j] * x[j];
            }
            
            x[i] = (b[i] - soma) / a[i][i];
            
        }
        
        Util.get().escrever("");
        Util.get().escrever("x: ");
        Util.get().escreveVetor(x);
        
        return new GaussResultado(a, b, x);
		
	}

	@Override
	public double[] getX(double[][] matriz) throws Exception {
		
		return getEliminacaoGauss(matriz).getX();
		
	}

}
