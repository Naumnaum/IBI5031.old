import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

//Classe que modela a tabela de genes
//Esta classe é acessada pela attCom para tentar combinações
public abstract class DataTable {

	private static String[] dataLabel;
	//Matriz com informações sobre o conjunto de treinamento
	//primeira chave se refere as característica, a segunda chave aos elementos
	//dessa forma data[0] é um vetor com a característica 0 de todos elementos
	private static double [][] data;
	//Vetor que identifica a classe dos elementos
	private static double [] label;
	
	//Carrega os dados no programa
	public static void load(){
		File file = new File("data_breast_cancer.csv");
		String line;
		BufferedReader br=null;
		LineNumberReader  lnr=null;
		try{
			lnr = new LineNumberReader(new FileReader(file));
			lnr.skip(Long.MAX_VALUE);
			int lines=lnr.getLineNumber();
			System.out.println("Found "+(lines+1)+" lines.");
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();			
			//String[] s = line.split(",");//linha de cabeçalho
			dataLabel = new String[lines];
			data = new double [lines][15];
			label = new double[15];
			int i=0;
			while ((line = br.readLine())!=null){
				parseData(i, line);
				i++;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			if (br!=null)
				try {
					br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			if (lnr!=null)
				try {
					lnr.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
		}
		System.out.println("Load completed.");
	}
	
	private static void parseData(int i, String line) {//hardcoded para braca_csv
		String[] s =line.split(",");
		dataLabel[i]=s[0];
		if (!dataLabel[i].isEmpty()){
			for(int j=1;j<11;j++){
				data[i][j-1]=Float.parseFloat(s[j]);
			}
			for(int j=18;j<23;j++){
				data[i][j-8]=Float.parseFloat(s[j]);
			}
		}else{
			for(int j=1;j<11;j++){
				int value = Integer.parseInt(s[j].substring(s[j].length()-1));
				if (value==1)
					label[j-1]=-1;
				else
					label[j-1]=1;
			}
			for(int j=18;j<23;j++){
				int value = Integer.parseInt(s[j].substring(s[j].length()-1));
				if (value==1)
					label[j-8]=-1;
				else
					label[j-8]=1;
			}
		}
	}
	
	public static String getDataLabel(int att){
		return dataLabel[att];
	}

	//Retorna um vetor com os valores de uma característica
	public static double[] getAttData (int att){
		return data[att].clone();
	}
	
	//Retorna a classe dado um elemento
	public static double getLabel (int element){
		return label[element];
	}
	
	//Retorna o vetor com os valores de classe
	public static double[] getLabelAsVector(){
		return label;
	}
	
	//Retorna o numero de características na tabela
	public static int getAttCount(){
		return data.length;
	}
	
	//Retorna o numero de elementos na tabela
	public static int getElementsCount(){
		return data[0].length;
	}
}
