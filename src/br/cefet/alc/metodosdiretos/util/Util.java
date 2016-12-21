package br.cefet.alc.metodosdiretos.util;

import java.text.DecimalFormat;

import Jama.Matrix;
import Jama.QRDecomposition;

/**
 * @author rtavares
 */
public class Util {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private static final double ZERO_THRESHOLD_I = -0.00001;
	private static final double ZERO_THRESHOLD_F = +0.00001;
	
	private static boolean debug = true;
	
	private static Util instance;
	
	public static Util get(){
		
		if(instance == null)
			instance = new Util();
		
		return instance;
		
	}
	
	public boolean isMatrizReduzida(double[][] m){
		
		double[][] mT = getMatrizTransposta(m);
		double[][] mTA = getMatrizA(mT);
		
		for(int i = 0; i < mTA.length; i++){
			
			double[] l = mTA[i];
			
			for(int j = 0; j < l.length; j++){
				
				if(l[j] == 1.0){
					
					for(int k = (j + 1); k < l.length; k++){
						
						if(l[k] != 0.00)
							return false;
						
					}
					
					for(int r = 0; r < j; r++){
						
						if(l[r] != 0.00)
							return false;
						
					}
					
				}else if(l[j] != 0.00){
					
					return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public double[][] getMatrizA(double[][] m){
		
		double[][] mT = getMatrizTransposta(m);
		
		double[][] mA = new double[mT.length - 1][mT[0].length];
		
		for(int i = 0; i < (mT.length - 1); i++)
			mA[i] = mT[i];
		
		return getMatrizTransposta(mA);
		
	}
	
	public double[] getMatrizB(double[][] m){
		
		double[][] mT = getMatrizTransposta(m);
		return mT[mT.length - 1];
		
	}
	
	public void escreveMatriz(double[][] m){
		
		if(!debug)
			return;
		
		for(int i = 0; i < m.length; i++){
			
			String linha = "";
			
			for(int j = 0; j < m[i].length; j++){
				
				linha += df.format(m[i][j]) + "  ";
				
			}
			
			System.out.println(linha);
		
		}
		
	}
	
	public void escreveVetor(double[] v){
		
		if(!debug)
			return;
		
		if(v == null){
			
			System.out.println("null");
			return;
			
		}
		
		for(int i = 0; i < v.length; i++){
			
			System.out.println(df.format(v[i]));
			
		}
		
	}
	
	public void escreveValor(double v){
		
		if(!debug)
			return;
		
		System.out.println(df.format(v));
		
	}
	
	public void escrever(String s){
		
		if(!debug)
			return;
		
		System.out.println(s);
		
	}
	
	public String formataValor(double v){
		
		return df.format(v);
		
	}
	
	public double[][] getMatrizTransposta(double[][] m){
		
		double[][] mT = new double[m[0].length][m.length];
		
		for(int i = 0; i < m.length; i++)
			
			for(int j = 0; j < m[i].length; j++)
				mT[j][i] = m[i][j];
		
		return mT;
		
	}
	
	public double[] getVetorB(double[][] m) {
		
		double[][] mT = getMatrizTransposta(m);
		
		return mT[mT.length - 1];
		
	}
	
	public double[][] getMatrizAX(double[][] a, double[] b, int x) {
		
		double[][] aT = getMatrizTransposta(a);
		
		aT[x] = b;
		
		return getMatrizTransposta(aT);
		
	}
	
	public double getDeterminante(double[][] a) throws Exception {
		
		double dP = 1;
		double dS = 1;
		double det = 0;
		
		if(a.length == 2 && a[0].length == 2){
			
			for(int i = 0; i < a.length; i++)
				dP = dP * a[i][i];
			
			double[][] aTC = new double[a.length][a[0].length];
			aTC[0] = a[1];
			aTC[1] = a[0];
			
			for(int i = 0; i < aTC.length; i++){
				dS = dS * aTC[i][i];
						
			}
			
			det = dP - dS;
			
			return det;
			
		}
		
		if(a.length == 3 && a[0].length == 3){
			
			double[][] mD = new double[a.length + 2][a.length];
			
			double[][] mT = getMatrizTransposta(a);
			
			for(int i = 0; i < mT.length; i++)
				mD[i] = mT[i];
			
			for(int i = 0; i < (mT.length - 1); i++)
				mD[i + mT.length] = mT[i];
			
			double[][] mDC = getMatrizTransposta(mD);
			
			double dP2 = 1;
			double dP3 = 1;
			
			for(int i = 0; i < mDC.length; i++){
				
				dP = dP * mDC[i][i];
				dP2 = dP2 * mDC[i][i + 1];
				dP3 = dP3 * mDC[i][i + 2];
				
			}
			
			double dS2 = 1;
			double dS3 = 1;
			
			double[][] mDCD = new double[mDC.length][mDC[0].length];
			
			mDCD[0] = mDC[2];
			mDCD[1] = mDC[1];
			mDCD[2] = mDC[0];
			
			for(int i = 0; i < mDC.length; i++){
				
				dS = dS * mDCD[i][i];
				dS2 = dS2 * mDCD[i][i + 1];
				dS3 = dS3 * mDCD[i][i + 2];
				
			}
			
			det = ((dP + dP2 + dP3) - (dS + dS2 + dS3));
			
			return det;
				
		}
		
		throw new Exception("O método só calcula determinantes de matrizes 2 x 2 e 3 x 3.");
		
	}
	
	public boolean isMatrizUmaIncognita(double[][] m){
		
		for(int i = 0; i < m.length; i++){
			
			int qtdX = 0;
			
			for(int j = 0; j < m[i].length; j++){
				
				if(m[i][j] != 0)
					qtdX++;
				
			}
			
			if(qtdX == 1)
				return true;
			
		}
		
		return false;
		
	}
	
	public boolean isLinhaPivot(double[][] a, int i){
		
		int qtdZ = 0;
		
		for(int k = i + 1; k < a.length; k++){
			
			if(a[k][i] == 0)
				qtdZ++;
			
			if(qtdZ == a.length - 1)
				return true;
			
		}
		
		return false;
		
	}
	
	public double[][] multiplicacao(double[][] m, double c){
		
		double[][] ret = new double[m.length][m[0].length];
		
		for(int i = 0; i < m.length; i++){
			
			for(int j = 0; j < m[i].length; j++){
				
				ret[i][j] = c * m[i][j];
				
			}
			
		}
		
		return ret;
		
	}
	
	public double[] multiplicacao(double[] m, double c){
		
		double[] ret = new double[m.length];
		
		for(int i = 0; i < m.length; i++)
			ret[i] = c * m[i];
		
		return ret;
		
	}
	
	public double[] divisao(double[] m, double c){
		
		double[] ret = new double[m.length];
		
		for(int i = 0; i < m.length; i++)
			ret[i] = m[i] / c;
		
		return ret;
		
	}
	
	public double[] multiplicacao(double[][] m, double[] v){
		
		double[] ret = new double[m.length];
		
		for(int i = 0; i < m.length; i++){
			
			double soma = 0;
			
			for(int j = 0; j < m[i].length; j++){
				
				soma += m[i][j] * v[j];
				
			}
			
			ret[i] = soma;
			
		}
		
		return ret;
		
	}
	
	public double multiplicacaoVTV(double[] v){
		
		double ret = 0;
		
		for (int i = 0; i < v.length; i++)
			ret += v[i] * v[i];
		
		return ret;
	}
	
	public double multiplicacaoVetor(double[] v1, double[] v2){
		
		double ret = 0;
		
		for (int i = 0; i < v1.length; i++)
			ret += v1[i] * v2[i];
		
		return ret;
	}
	
	public double[] soma(double[] v1, double[] v2){
		
		double[] ret = new double[v1.length];
		
		for(int i = 0; i < v1.length; i++)
			ret[i] = v1[i] + v2[i];
		
		return ret;
		
	}
	
	public double[] subtracao(double[] v1, double[] v2){
		
		double[] ret = new double[v1.length];
		
		for(int i = 0; i < v1.length; i++)
			ret[i] = v1[i] - v2[i];
		
		return ret;
		
	}
	
	public double normaE(double[] v){
		
		double soma = 0;
		
		for(int i = 0; i < v.length; i++){
			
			soma += Math.pow(v[i], 2);
			
		}
		
		return Math.sqrt(soma);
		
	}
	
	public double produtoInterno(double[] v1, double[] v2){
		
		double ret = 0;
		
		for(int i = 0; i < v1.length; i++){
			
			ret += v1[i] * v2[i];
			
		}
		
		return ret;
		
	}
	
	public double[][] diagonal(double[][] a){
		
		double[][] ret = new double[a.length][a[0].length];
		
		for(int i = 0; i < a.length; i++)
			ret[i][i] = a[i][i];
		
		return ret;
		
	}
	
	public double[][] invertMatrix(double a[][]) throws Exception {
	    
		try{
		
			Matrix m = new Matrix(a);
			return m.inverse().getArray();
			
		}catch(Exception e){
			
			throw new Exception("Erro ao encontrar matriz inversa.");
			
		}
		
	}
	
	public double[][] leastSquare(double[][] a, double[] b) throws Exception {
	    
		try{
		
			return new QRDecomposition(new Matrix(a)).solve(new Matrix(b, b.length)).getArray();
			
		}catch(Exception e){
			
			throw new Exception("Erro ao executar leastSquare.");
			
		}
		
	}
	
	public boolean equalsZero(double v) throws Exception {
		
		return v > ZERO_THRESHOLD_I && v < ZERO_THRESHOLD_F;
		
	}
	
	public void setDebug(boolean d){
		
		debug = d;
		
	}
	
}
