package br.cefet.alc.util;

import br.cefet.alc.matrix.MatrizCSR;


/**
 * @author rtavares
 */
public class Teste {

	public static void main(String[] args) {
		
		double[][] matriz = new double[][]{
				
				{ 5,-1, 0,-2, 0, 2.5},
				{-1, 4,-1, 0, 0, 1},
				{ 0,-1, 6, 0,-3, 0},
				{-2, 0, 0, 4, 0, 0},
				{ 0, 0,-3, 0, 5, 1.5}
				
		};
		
		MatrizCSR m = new MatrizCSR(matriz);
		
		m.setValor(0, 0, 1.0);
		m.setValor(0, 1, 2.0);
		m.setValor(0, 2, 3.0);
		m.setValor(0, 3, 4.0);
		m.setValor(0, 4, 5.0);
		
		m.setValor(1, 0, 1.1);
		m.setValor(1, 1, 2.2);
		m.setValor(1, 2, 3.3);
		m.setValor(1, 3, 4.4);
		m.setValor(1, 4, 5.5);
	
		m.setValor(2, 0, 4.0);
		m.setValor(2, 1, 5.0);
		m.setValor(2, 2, 6.0);
		m.setValor(2, 3, 7.0);
		m.setValor(2, 4, 8.0);
		
		m.setValor(3, 0, 9.0);
		m.setValor(3, 1, 10.0);
		m.setValor(3, 2, 11.0);
		m.setValor(3, 3, 12.0);
		m.setValor(3, 4, 13.0);
		
		m.setValor(4, 0, 14.0);
		m.setValor(4, 1, 15.0);
		m.setValor(4, 2, 16.0);
		m.setValor(4, 3, 17.0);
		m.setValor(4, 4, 18.0);
		
		System.out.println("      ");
		Util.get().escreveMatrizCSR(m);
		
		m.setValor(4, 4, 0.0);
		m.setValor(4, 1, 0.0);
		m.setValor(1, 2, 0.0);
		m.setValor(0, 1, 0.0);
		
		System.out.println("      ");
		Util.get().escreveMatrizCSR(m);

	}

}
