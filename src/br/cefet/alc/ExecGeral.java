package br.cefet.alc;

import br.cefet.alc.metodosdiretos.Cholesky;
import br.cefet.alc.metodosdiretos.DecomposicaoLU;
import br.cefet.alc.metodosdiretos.EliminacaoGaussiana;
import br.cefet.alc.metodosdiretos.Metodo;
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
import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class ExecGeral {
	
	public static void main(String[] args) {
		
		try{
			
			Util.get().setDebug(false);
			
			double[][] matriz = new double[][]{
					
					{ 5,-1, 0,-2, 0, 2.5},
					{-1, 4,-1, 0, 0, 1},
					{ 0,-1, 6, 0,-3, 0},
					{-2, 0, 0, 4, 0, 0},
					{ 0, 0,-3, 0, 5, 1.5}
					
					/*
					{3, -2, 4, 14}, 
					{-5, 4, 1, 10},
					{-1, 1, 3, 13}
					
					{5, -4, 1, 0, 0},
					{-4, 6, -4, 1, 1},
					{1, -4, 6, -4, 0},
					{0, 1, -4, 5, 0}
					
					*/
			};
			
			double[] xGauss = null;
			double[] xLU = null;
			double[] xCholesky = null;
			double[] xJacobi = null;
			double[] xGSS = null;
			double[] xGSL = null;
			double[] xSor = null;
			double[] xGD = null;
			double[] xGC = null;
			double[] xGMRES = null;
			double[] xGMRESX = null;
			double[] xGM = null;
			double[] xMG = null;
			
			Metodo metodo = null;
			
			try{
				
				metodo = new EliminacaoGaussiana();
				xGauss = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Eliminação Gaussiana");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new DecomposicaoLU();
				xLU = metodo.getX(matriz);
				
			}catch(Exception e){
				
				System.out.println("Erro: Decomposição LU");
				e.printStackTrace();
			}
			
			try{		
				
				metodo = new Cholesky();
				xCholesky = metodo.getX(matriz);
				
			}catch(Exception e){
				
				System.out.println("Erro: Cholesky");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new Jacobi();
				xJacobi = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Jacobi");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GaussSeidelSassenfeld();
				xGSS = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Gauss Seidel - Sassenfeld");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GaussSeidelLinhas();
				xGSL = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Gauss Seidel - Linhas");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new SOR();
				xSor = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: SOR");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GradienteDescendente();
				xGD = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Gradiente Descendente");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GradienteConjugado();
				xGC = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Gradiente Conjugado");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GMRES();
				xGMRES = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: GMRES");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new GMRESX();
				xGMRESX = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: GMRESX");
				e.printStackTrace();
			}
				
			try{	
				
				metodo = new GradienteModificado();
				xGM = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: Gradiente Modificado");
				e.printStackTrace();
			}
				
			try{
				
				metodo = new MultiGrid();
				xMG = metodo.getX(matriz);
			
			}catch(Exception e){
				
				System.out.println("Erro: MultiGrid");
				e.printStackTrace();
			}
			
			Util.get().escrever("");
			Util.get().escrever("");
			
			Util.get().setDebug(true);
			
			Util.get().escrever("--- Eliminação Gaussiana ---");
			Util.get().escreveVetor(xGauss);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Decomposição LU ---");
			Util.get().escreveVetor(xLU);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Cholesky ---");
			Util.get().escreveVetor(xCholesky);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Jacobi ---");
			Util.get().escreveVetor(xJacobi);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Gauss Seidel - Sassenfeld ---");
			Util.get().escreveVetor(xGSS);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Gauss Seidel - Linhas ---");
			Util.get().escreveVetor(xGSL);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- SOR ---");
			Util.get().escreveVetor(xSor);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Gradiente Descendente ---");
			Util.get().escreveVetor(xGD);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Gradiente Conjugado ---");
			Util.get().escreveVetor(xGC);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- GMRES ---");
			Util.get().escreveVetor(xGMRES);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- GMRESX ---");
			Util.get().escreveVetor(xGMRESX);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Gradiente Modificado ---");
			Util.get().escreveVetor(xGM);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
			Util.get().escrever("--- Multigrid ---");
			Util.get().escreveVetor(xMG);
			Util.get().escrever("----------------------------");
			Util.get().escrever("");
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}

}
