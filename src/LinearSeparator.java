import java.util.Arrays;

import Jama.Matrix;

//Classe que modela uma função separadora para combinação de n de característica
public class LinearSeparator {

	//característica que foram utilizados na separação
	private String[] attributes;
	//se a separação foi total (sem erros)
	private boolean total;
	
	//vetores das característica
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
			System.out.println("Já testadas "+(evaluated/1000000)+" milhões combinações ("+linearS+" separadoras lineares)");
		}
	}
	
	//Estima os valores da função separadora
	private void evaluate(){
		//usando uma biblioteca para operações com matrizes
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
	
	//Verifica se a função classifica corretamente os elementos do conjunto de treinamento
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
			return "Não encontrado vetor usando MSE";
		}
		else{
			return "Genes: "+Arrays.toString(attributes)+"\nPesos:"+Arrays.toString(a.getRowPackedCopy());
		}
	}
}
