import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TSP {
	
	private static int numCities;
	private static int [][] distances;
	
	public static void main(String[] args) {
		routeTSP route = new routeTSP();
		ArrayList<Integer> candidates = null;
		
		readFile("benchmarks/a4.tsp");
		//un read de los fichero solucion
		printMatrix();
		
		routeTSP solution = null;
		switch(args[1]) {
		case "-fb":
			solution = bruteForceTSP(route, candidates);
			return;
		}
		
		System.out.println("Distancia conseguida: " + route.getCost());
		//System.out.println("Distancia esperada: " + );
		 
		long time = System.currentTimeMillis();
		time = System.currentTimeMillis()-time;
		System.out.println("Tiempo: "+time);
		
	}
	
	public static routeTSP bruteForceTSP(routeTSP route, ArrayList<Integer> candidates) {
		routeTSP retVal = null; //valor a retornar	
		for(int i : candidates){
			routeTSP partialSolution = bruteForceTSPR(route, candidates, i);
			
			//Nos quedamos con el camino de menor coste
			if(retVal == null) {
				retVal = partialSolution;
			}
			if(retVal.getCost() < partialSolution.getCost()) {
				retVal.setCost(partialSolution.getCost());
				retVal.setCities(partialSolution.getCities());
			}
		}
		return retVal;
	}
	
	public static routeTSP bruteForceTSPR(routeTSP route, ArrayList<Integer> candidates,
			Integer i) {
		routeTSP retVal = null; //valor a retornar

		//Copias de los argumentos para no modificarlos en niveles de 
		//anidamiento inferiores
		routeTSP r = new routeTSP(route);
		ArrayList<Integer> c = new ArrayList<Integer>(candidates);
		
		if(!c.isEmpty()) {
			for(int j : c){
				//Añadir ciudad al camino y aumentar el coste
				r.getCities().addLast(j);
				r.setCost(r.getCost() + distances[i][j]);
				
				//eliminar la ciudad añadida de la lista de posibles ciudades siguientes
				c.remove(j);
				
				routeTSP partialSolution = bruteForceTSPR(r, c, j);
				
				//Nos quedamos con el camino de menor coste
				if(retVal == null) {
					retVal = partialSolution;
				}
				if(retVal.getCost() < partialSolution.getCost()) {
					retVal.setCost(partialSolution.getCost());
					retVal.setCities(partialSolution.getCities());
				}
			}
		}
		else {
			//si no quedan ciudades por visitar sumamos el coste de ir 
			//de la ultima a la primera
			retVal.setCost(distances[route.getCities().getLast()][route.getCities().getFirst()]);
		}
		return retVal;
	}
	
	public static void readFile(String nomFich){
		getNumCities(nomFich);
		try {
			Scanner ent = new Scanner(new FileReader(nomFich));
			distances=new int[numCities][numCities];
			int i = 0;
			while (ent.hasNext()) {
				String line = ent.nextLine();
					int num=0;
					String [] listNumbers = line.split(" ");
					for (int j=0; j<listNumbers.length; j++) {
						System.out.println(listNumbers[j]);
						//salta excepcion aqui porque en los ficheros que nos da el hay columnas 
						//separadas por mas de un espacio.
						num = Integer.parseInt(listNumbers[j]); 
		            	distances[i][j]=num;
					}
				i++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void getNumCities(String nomFich) {
		try {
			Scanner ent = new Scanner(new FileReader(nomFich));
			String line = null;
			if(ent.hasNext()){
				line = ent.nextLine();
				numCities=line.length()-line.replace(" ", "").length()+1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printMatrix(){
		for (int i=0; i<numCities; i++){
			for (int j=0;j<numCities; j++){
				System.out.print(distances[i][j]);
			}
			System.out.println();
		}
	}
	
	/*public static int getCost(int[] road) {
		int cost = 0;
		for (int i=0; i<numCities-1; i++ ) {
			cost += distances[road[i]][road[i+1]];
		}
		cost += distances[road[numCities-1]][road[0]];
		return cost;
	}*/

}
