package br.cefet.alc.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.cefet.alc.util.Util;

/**
 * @author rtavares
 */
public class MatrizCSR {
	
	public static final double ZERO = 0.00;
	
	private double[] A;
	
	private int[] JA;
	
	private int[] IA;
	
	private int[] IDIAG;
	
	private double[] B;
	
	private int n;
	
	private int nz;
	
	public MatrizCSR(int n){
		
		B = new double[]{};
		A = new double[]{};
		JA = new int[]{};
		IA = new int[]{};
		IDIAG = new int[]{};
		this.n = n;
		nz = 0;
		
	}
	
	public MatrizCSR(double[][] matrix){
		
		B = Util.get().getMatrizB(matrix);
		
		double[][] a = Util.get().getMatrizA(matrix);
		
		List<Double> tempA = new ArrayList<Double>();
		List<Integer> tempJA = new ArrayList<Integer>();
		List<Integer> tempIA = new ArrayList<Integer>();
		List<Integer> tempIDIAG = new ArrayList<Integer>();
		
		for(int i = 0; i < matrix.length; i++){
			
			n++;
			
			tempIA.add(tempA.size());
			
			for(int j = 0; j < a[i].length; j++){
				
				if(!Util.get().equalsZero(a[i][j])){
					
					nz++;
					
					tempA.add(a[i][j]);
					
					tempJA.add(j);
					
					if(i == j)
						tempIDIAG.add(tempA.size() - 1);
					
				}else{
					
					if(i == j)
						tempIDIAG.add(-1);
					
				}
				
			}
			
		}
		
		A = Util.get().getDoublePrimitive(tempA);
		JA = Util.get().getIntPrimitive(tempJA);
		IA = Util.get().getIntPrimitive(tempIA);
		IDIAG = Util.get().getIntPrimitive(tempIDIAG);
		
	}
	
	public double getValor(int i, int j){
		
		if((i >= IA.length) || (IA[i] > A.length))
			return ZERO;
		
		int limite = A.length;
		
		if(i < (n - 1) && (i + 1) < IA.length)
			limite = IA[i + 1];
		
		for(int r = IA[i]; r < limite; r++){
			
			//try{
			
			if(JA[r] == j)
				return A[r];
			
			/*
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
			*/
			
		}
		
		return ZERO;
		
	}
	
	public void setValor(int i, int j, double valor){
		
		/*
		System.out.println("");
		System.out.println("i = " + i + " j = " + j +  " valor = " + valor);
		System.out.println("A:");
		Util.get().escreveVetor(A);
		*/
		
		if(ZERO != getValor(i, j)){
			
			
			if(Util.get().equalsZero(valor)){
				
				List<Double> tempA = Util.get().getDoublePrimitive(A);
				List<Integer> tempJA = Util.get().getIntPrimitive(JA);
				
				int limit = i < (n - 1) ? IA[(i + 1)] : A.length;
				
				int qtdLinha = tempA.subList(IA[i], limit).size();
				
				tempA.remove(IA[i] + j);
				tempJA.remove(IA[i] + j);
				
				A = Util.get().getDoublePrimitive(tempA);
				JA = Util.get().getIntPrimitive(tempJA);
				
				if(qtdLinha > 1){
					
					for(int z = (i + 1); z < IA.length; z++)
						IA[z] -= 1;
					
				}else{
					
					IA[i] = tempA.size();
					
				}
				
				for(int z = (i + 1); z < IDIAG.length; z++)
					IDIAG[z] -= IDIAG[z] != -1 ? 1 : ZERO;
				
				if(i == j)
					IDIAG[i] = -1;
				
				nz--;
				
			}else{
			
				int limite = A.length;
				
				if(i < (n - 1))
					limite = IA[i + 1];
				
				for(int r = IA[i]; r < limite; r++){
					
					if(JA[r] == j){
						
						A[r] = valor;
						return;
						
					}
					
				}
				
			}
			
			
		}else if(!Util.get().equalsZero(valor)){
			
			List<Double> tempA = Util.get().getDoublePrimitive(A);
			List<Integer> tempJA = Util.get().getIntPrimitive(JA);
			List<Integer> tempIA = Util.get().getIntPrimitive(IA);
			List<Integer> tempIDIAG = Util.get().getIntPrimitive(IDIAG);
			
			if((i >= IA.length) || (IA[i] > A.length)){
				
				tempA.add(valor);
				tempJA.add(j);
				
				if(i >= tempIA.size()){
					
					LinkedList<Integer> nIA = new LinkedList<Integer>(Arrays.asList(new Integer[i + 1]));
					
					for(int r = 0; r < tempIA.size(); r++)
						nIA.set(r, tempIA.get(r));
					
					tempIA = nIA;
					
				}
				
				tempIA.set(i, tempA.size() - 1);
				
				if(i == j){
					
					if(i >= tempIDIAG.size()){
						
						LinkedList<Integer> nIDIAG = new LinkedList<Integer>(Arrays.asList(new Integer[i + 1]));
						
						for(int r = 0; r < tempIDIAG.size(); r++)
							nIDIAG.set(r, tempIDIAG.get(r));
						
						tempIDIAG = nIDIAG;
						
					}
					
					tempIDIAG.set(i, tempA.size() - 1);
					
				}
				
				IA = Util.get().getIntPrimitive(tempIA);;
				A = Util.get().getDoublePrimitive(tempA);
				JA = Util.get().getIntPrimitive(tempJA);
				IDIAG = Util.get().getIntPrimitive(tempIDIAG);
				
				nz++;
				
			}else{
				
				LinkedList<Double> preA = new LinkedList<Double>();
				LinkedList<Double> postA= new LinkedList<Double>();
				
				LinkedList<Integer> preJA = new LinkedList<Integer>();
				LinkedList<Integer> postJA = new LinkedList<Integer>();
				
				int limit = i < (IA.length - 1) ? IA[i + 1] : A.length;
				
				for(int r = IA[i]; r < limit; r++){
					
					boolean post = false;
					boolean pre = false;
					
					if((j - JA[r]) == 1)
						post = true;
						
					else if(JA[r] > j)
						pre = true;
					
					if(post || pre){
						
						preA.addAll(tempA.subList(0, r));
						preJA.addAll(tempJA.subList(0, r));
						
						Double cValor = tempA.get(r);
						Integer jValor = tempJA.get(r);
						
						if((r + 1) < tempA.size()){
							
							postA.addAll(tempA.subList(r + 1, tempA.size()));
							postJA.addAll(tempJA.subList(r + 1, tempA.size()));
							
						}
						
						tempA.clear();
						tempJA.clear();
						
						tempA.addAll(preA);
						tempJA.addAll(preJA);
						
						if(post){
							
							tempA.add(cValor);
							tempJA.add(jValor);
							
							tempA.add(valor);
							tempJA.add(j);
							
						}else{
							
							tempA.add(valor);
							tempJA.add(j);
							
							tempA.add(cValor);
							tempJA.add(jValor);
							
						}
						
						tempA.addAll(postA);
						tempJA.addAll(postJA);
						
						for(int z = (i + 1); z < IA.length; z++)
							IA[z] += 1;
						
						A = Util.get().getDoublePrimitive(tempA);
						JA = Util.get().getIntPrimitive(tempJA);
						
						if(i == j){
							
							if(i >= tempIDIAG.size()){
								
								LinkedList<Integer> nIDIAG = new LinkedList<Integer>(Arrays.asList(new Integer[i + 1]));
								
								for(int z = 0; z < tempIDIAG.size(); z++)
									nIDIAG.set(z, tempIDIAG.get(z));
								
								tempIDIAG = nIDIAG;
								
							}
							
							tempIDIAG.set(i, post ? (tempA.size() - 1) : (tempA.size() - 2));
							
						
						}
						
						for(int z = (i + 1); z < IDIAG.length; z++)
							tempIDIAG.set(z, tempIDIAG.get(z) + 1);
						
						IDIAG = Util.get().getIntPrimitive(tempIDIAG);
						
						nz++;
						
						break;
						
					}
						
				}
			
			}
			
		}
		
	}
	
	public double[] getLinha(int i){
		
		double[] ret = new double[n];
		
		for(int r = 0; r < n; r++){
			ret[r] = getValor(i, r);
		}
		
		return ret;
		
	}
	
	public int getQtdNaoZeros(){
		return nz;
	}
	
	public int getN(){
		return n;
	}
	
	public int getQtd(){
		return n * n;
	}
	
	public double[] getA(){
		return A;
	}
	
	public double[] getB(){
		return B;
	}
	
	public double[] getDiagonal(){
		
		double[] ret = new double[IDIAG.length];
		
		for(int i = 0; i < IDIAG.length; i++){
			
			ret[i] = IDIAG[i] != -1 ? A[IDIAG[i]] : ZERO;
			
		}
		
		return ret;
		
	} 
	
}
