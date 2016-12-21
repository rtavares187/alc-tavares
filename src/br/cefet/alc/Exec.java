package br.cefet.alc;

import br.cefet.alc.metodosdiretos.Cholesky;
import br.cefet.alc.metodosdiretos.DecomposicaoLU;
import br.cefet.alc.metodosdiretos.EliminacaoGaussiana;
import br.cefet.alc.metodosdiretos.Metodo;
import br.cefet.alc.metodosdiretos.RegraDeCramer;
import br.cefet.alc.metodositerativos.estacionarios.GaussSeidelLinhas;
import br.cefet.alc.metodositerativos.estacionarios.GaussSeidelSassenfeld;
import br.cefet.alc.metodositerativos.estacionarios.Jacobi;
import br.cefet.alc.metodositerativos.estacionarios.SOR;
import br.cefet.alc.metodositerativos.naoestacionarios.GMRES;
import br.cefet.alc.metodositerativos.naoestacionarios.GMRESX;
import br.cefet.alc.metodositerativos.naoestacionarios.GradienteConjugado;
import br.cefet.alc.metodositerativos.naoestacionarios.GradienteDescendente;
import br.cefet.alc.metodositerativos.naoestacionarios.GradienteModificado;
import br.cefet.alc.metodositerativos.naoestacionarios.MultiGrid;

/**
 * @author rtavares
 */
public class Exec {

	public static void main(String[] args) {
		
		try{
		
			// Regra de Cramer
			
			//regraDeCramer();
			
			// Eliminacao Gaussiana
			
			eliminacaoGaussiana();
			
			System.out.println("### ----------------------------------- ###");
			
			// Decomposicao LU
			
			//decomposicaoLU();
			
			// Cholesky
			
			//cholesky();
			
			//jacobi();
			
			//gaussSeidelSassenfeld();
			
			//System.out.println("### ----------------------------------- ###");
			
			//gaussSeidelLinhas();
			
			//sor();
			
			//gradienteDescendente();
			
			//gradienteConjugado();
			
			//GMRES();
			
			//System.out.println("### ----------------------------------- ###");
			
			GMRESX();
			
			//gradienteModificado();
			
			//multiGrid();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void regraDeCramer() throws Exception {
		
		double[][] matriz = new double[][]{
				{3, -2, 6}, 
				{-5, 4, 8}};
		
		/*
		double[][] matriz = new double[][]{
				{1, -2, -2, -1}, 
				{1, -1, 1, -2},
				{2, 1, 3, 1}};
		*/
		
		Metodo metodo = new RegraDeCramer();
		metodo.executar(matriz);
		
	}
	
	public static void eliminacaoGaussiana() throws Exception {
		
		// Eliminação Gaussiana
		
		double[][] matriz = new double[][]{
				/*
				{3, -2, 6}, // converge GS
				{-5, 4, 8}
				*/
				
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				
				/*
				{2, 2, 1, 1, 7}, 
				{1, -1, 2, -1, 1},
				{3, 2, -3, -2, 4},
				{4, 3, 2, 1, 12}
				*/
				/*
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				*/
				/*
				{ 5,-1, 0,-2, 0, 2.5}, // converge GS
				{-1, 4,-1, 0, 0, 1},
				{ 0,-1, 6, 0,-3, 0},
				{-2, 0, 0, 4, 0, 0},
				{ 0, 0,-3, 0, 5, 1.5}
				*/
				};
		
		Metodo metodo = new EliminacaoGaussiana();
		metodo.executar(matriz);
		
	}
	
	public static void decomposicaoLU() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{2, 2, 1, 1, 7}, 
				{1, -1, 2, -1, 1},
				{3, 2, -3, -2, 4},
				{4, 3, 2, 1, 12}
				};
				
				/*
				{-1, 2, 3, 1, 1},  
				{2, -4, -5, -1, 0},  
				{-3, 8, 8, 1, 2},  
				{1, 2, -6, 4, -1}};
				*/
				
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				};
				
		Metodo metodo = new DecomposicaoLU();
		metodo.executar(matriz);
		
	}
	
	public static void cholesky() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{2, 2, 1, 1, 7}, 
				{1, -1, 2, -1, 1},
				{3, 2, -3, -2, 4},
				{4, 3, 2, 1, 12}
				};
				
				/*
				{-1, 2, 3, 1, 1},  
				{2, -4, -5, -1, 0},  
				{-3, 8, 8, 1, 2},  
				{1, 2, -6, 4, -1}};
				*/
				
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				};
				
		Metodo metodo = new Cholesky();
		metodo.executar(matriz);
		
	}
	
	public static void jacobi() throws Exception {
		
		double[][] matriz = new double[][]{
				
				{10, 2, 1, 7},
				{1, 5, 1, -8},
				{2, 3, 10, 6}
				
				/*
				{4, -1, 1, 4},
				{1, 6, 2, 9},
				{-1, -2, 5, 2}
				*/
		};
		
		Metodo metodo = new Jacobi();
		metodo.executar(matriz);
		
	}
	
	public static void gaussSeidelSassenfeld() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{5, 1, 1, 5},
				{3, 4, 1, 6},
				{3, 3, 6, 0}
				*/
				
				{3, -2, 6}, 
				{-5, 4, 8}
				
		};
		
		Metodo metodo = new GaussSeidelSassenfeld();
		metodo.executar(matriz);
		
	}
	
	public static void gaussSeidelLinhas() throws Exception {
		
		double[][] matriz = new double[][]{
				
				{5, 1, 1, 5},
				{3, 4, 1, 6},
				{3, 3, 6, 0}
		};
		
		Metodo metodo = new GaussSeidelLinhas();
		metodo.executar(matriz);
		
	}
	
	public static void sor() throws Exception {
		
		double[][] matriz = new double[][]{
				
				{5, -4, 1, 0, 0},
				{-4, 6, -4, 1, 1},
				{1, -4, 6, -4, 0},
				{0, 1, -4, 5, 0}
		};
		
		double W = 1;
		
		Metodo metodo = new SOR(W);
		metodo.executar(matriz);
		
	}
	
	public static void gradienteDescendente() throws Exception {
		
		double[][] matriz = new double[][]{
				
				{5, -4, 1, 0, 0},
				{-4, 6, -4, 1, 1},
				{1, -4, 6, -4, 0},
				{0, 1, -4, 5, 0}
		
		};
		
		Metodo metodo = new GradienteDescendente();
		metodo.executar(matriz);
		
	}
	
	public static void gradienteConjugado() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				*/
				 { 5,-1, 0,-2, 0, 2.5},
				 {-1, 4,-1, 0, 0, 1},
				 { 0,-1, 6, 0,-3, 0},
				 {-2, 0, 0, 4, 0, 0},
				 { 0, 0,-3, 0, 5, 1.5}
				/*
				{5, -4, 1, 0, 0},
				{-4, 6, -4, 1, 1},
				{1, -4, 6, -4, 0},
				{0, 1, -4, 5, 0}
				*/
		
		};
		
		Metodo metodo = new GradienteConjugado();
		metodo.executar(matriz);
		
	}
	
	public static void GMRES() throws Exception {
		
		double[][] matriz = new double[][]{
				
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				
				/*
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				
				{1, 2, 3, 4}, 
				{5, 6, 7, 8},
				{6, 7, 8, 7},
				*/
				
		};
		
		Metodo metodo = new GMRES();
		metodo.executar(matriz);
		
	}
	
	public static void GMRESX() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				*/
				{ 5,-1, 0,-2, 0, 2.5},
				{-1, 4,-1, 0, 0, 1},
				{ 0,-1, 6, 0,-3, 0},
				{-2, 0, 0, 4, 0, 0},
				{ 0, 0,-3, 0, 5, 1.5}
				/*
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				
				{1, 2, 3, 4}, 
				{5, 6, 7, 8},
				{6, 7, 8, 7},
				
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				*/
		};
		
		Metodo metodo = new GMRESX();
		metodo.executar(matriz);
		
	}
	
	public static void gradienteModificado() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				*/
				
				 { 5,-1, 0,-2, 0, 2.5},
				 {-1, 4,-1, 0, 0, 1},
				 { 0,-1, 6, 0,-3, 0},
				 {-2, 0, 0, 4, 0, 0},
				 { 0, 0,-3, 0, 5, 1.5}
				
				/*
				{3, -2, 6}, 
				{-5, 4, 8}
				/*
				{2, -1, 4, 0, 5}, 
				{4, -1, 5, 1, 9},
				{-2, 2, -2, 3, 1},
				{0, 3, -9, 4, -2}
				
				{1, 2, 3, 4}, 
				{5, 6, 7, 8},
				{6, 7, 8, 7},
				*/
				/*
				{5, -4, 1, 0, 0},
				{-4, 6, -4, 1, 1},
				{1, -4, 6, -4, 0},
				{0, 1, -4, 5, 0}
				*/
		};
		
		Metodo metodo = new GradienteModificado();
		metodo.executar(matriz);
		
	}
	
	public static void multiGrid() throws Exception {
		
		double[][] matriz = new double[][]{
				/*
				{3, -2, 6}, 
				{-5, 4, 8}
				*/
				
				{ 5,-1, 0,-2, 0, 2.5},
				{-1, 4,-1, 0, 0, 1},
				{ 0,-1, 6, 0,-3, 0},
				{-2, 0, 0, 4, 0, 0},
				{ 0, 0,-3, 0, 5, 1.5}
				
				/*
				{3, -2, 4, 14}, 
				{-5, 4, 1, 10},
				{-1, 1, 3, 13}
				*/
		};
		
		Metodo metodo = new MultiGrid();
		metodo.executar(matriz);
		
	}

}
