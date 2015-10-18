import java.util.Arrays;

import Jama.Matrix;

//Classe que modela uma fun��o separadora para combina��o de n de caracter�stica
public class LinearSeparator {

	//caracter�stica que foram utilizados na separa��o
	private String[] attributes;
	//se a separa��o foi total (sem erros)
	private boolean total;
	
	//vetores das caracter�stica
	double[][] attData;
	Matrix matrixX;
	Matrix matrixY = new Matrix(DataTable.getLabelAsVector(),15);
	Matrix a;
	
	int numberOfAtt = DataTable.getAttData(0).length;
	
	private static int evaluated=0;
	private static int linearS=0;
	
	public static int getLinearS(){
		return linearS;
	}
	
	public static int getEvaluated(){
		return evaluated;
	}
	
	public static void metricReset(){
		evaluated=0;
		linearS=0;
	}
	
	public LinearSeparator(int n, int[] atts) {
		attData = new double[n+1][numberOfAtt];
		attributes = new String[n];
		for (int i=0;i<10;i++){
			attData[0][i]=1;
		}
		for (int i=0;i<n;i++){
			attData[i+1] =  DataTable.getAttData(atts[i]);
			attributes[i] = DataTable.getDataLabel(atts[i]);
		}
		this.evaluate();
		total=this.test();
		if(total)
			linearS++;
		if(evaluated%1000000==0){
			System.out.println("J� testadas "+(evaluated/1000000)+" milh�es combina��es ("+linearS+" separadoras lineares)");
		}
	}
	
	//Estima os valores da fun��o separadora
	private void evaluate(){
		//usando uma biblioteca para opera��es com matrizes
		matrixX = new Matrix(attData).transpose();
		Matrix matrixXt = matrixX.transpose();
		Matrix XtX = (matrixXt.times(matrixX));
		if (XtX.det()==0){
			evaluated++;
			return;
		}
		Matrix inverseXtX = XtX.inverse();
		a = inverseXtX.times(matrixXt).times(matrixY);
		evaluated++;
	}
	
	//Verifica se a fun��o classifica corretamente os elementos do conjunto de treinamento
	private boolean test(){
		if(a==null)
			return false;
		Matrix teste = matrixX.times(a).arrayTimes(matrixY);
		double[] resultados = teste.getColumnPackedCopy();
		for (double d:resultados){
			if (d<0)
				return false;
		}
		return true;
	}
	
	public boolean isTotal(){
		return total;
	}
	
	public String toString(){
		if(a==null){
			return "N�o encontrado vetor usando MSE";
		}
		else{
			return "Genes: "+Arrays.toString(attributes)+"\nPesos:"+Arrays.toString(a.getRowPackedCopy());
		}
	}
}
