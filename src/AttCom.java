import java.util.ArrayList;

//Classe que tenta combinações de características que podem representar uma separação linear total das classes
public abstract class AttCom {

	static ArrayList<LinearSeparator> totalPairs = new ArrayList<LinearSeparator>();
	static ArrayList<LinearSeparator> totalTriplets = new ArrayList<LinearSeparator>();
	
	public static void tryPairs(){
		long ini = System.currentTimeMillis();
		System.out.println("Testando pares.");
		for (int i = 0;i<DataTable.getAttCount();i++){
			for (int j = i+1;j<DataTable.getAttCount();j++){
				LinearSeparator linearSeparator = new LinearSeparator(2, new int[]{i,j});
				if (linearSeparator.isTotal())
					totalPairs.add(linearSeparator);
			}
		}
		System.out.println("Avaliados "+LinearSeparator.getEvaluated()+" pares.");
		System.out.println("Encontradas "+LinearSeparator.getLinearS()+" separações lineares totais.");
		long end = System.currentTimeMillis();
		System.out.println("Tempo decorrido: "+(end-ini)+"ms");
		for (int i=0;i<10;i++){
			System.out.println(totalPairs.get(i));
		}
		LinearSeparator.metricReset();
	}
	
	public static void tryTriplets(){
		System.out.println("Testando triplas.");
		for (int i = 0;i<DataTable.getAttCount()/10;i++){
			for (int j = i+1;j<DataTable.getAttCount()/10;j++){
				for (int k = j+1;k<DataTable.getAttCount()/10;k++){
					LinearSeparator linearSeparator = new LinearSeparator(3, new int[]{i,j,k});
				if (linearSeparator.isTotal())
					totalTriplets.add(linearSeparator);
				}
			}
		}
		System.out.println("Avaliadas "+LinearSeparator.getEvaluated()+" triplas.");
		System.out.println("Encontradas "+LinearSeparator.getLinearS()+" separações lineares totais.");
		for (int i=0;i<10;i++){
			System.out.println(totalTriplets.get(i));
		}
		LinearSeparator.metricReset();
	}
}
