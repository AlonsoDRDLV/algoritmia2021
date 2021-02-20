import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class TSP {
	
	private static int n;
	private static int [][] distances;
	
	public static void main(String[] args) {
		
		readFile("benchmarks/dantzig42.tsp");
		Integer waitSol[] = new Integer [n];
		//int waitCost = readSolution("benchmarks/gr17.sol",waitSol);
		printMatrix();

		routeTSP solution = null;
		switch("-av") {
		case "-fb":			
			solution = bruteForce();
			break;
		case "-av":
			solution = greedy();
			break;
		}
		
		System.out.println("Camino conseguida: " + solution.getCities());

		System.out.println("Distancia conseguida: " + solution.getCost());
		//System.out.println("Distancia esperada: " + waitCost);
		
		long time = System.currentTimeMillis();
		time = System.currentTimeMillis()-time;
		System.out.println("Tiempo: "+time);
		
	}
	
	public static Integer readSolution(String nomFich, Integer[] outCities) {
		try {
			Scanner ent = new Scanner(new FileReader(nomFich));
			String line = ent.nextLine();
			String [] list = line.split(" ");
			String costString = list[3];
			Integer solutionCost = Integer.parseInt(costString);
			
			line = ent.nextLine();
			list = line.split(" ");
			String [] citiesString = list[1].split("-");
			
			for(int i = 0; i < n; i++) {
				outCities[i] = Integer.parseInt(citiesString[i]);
			}
			
			return solutionCost;
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static routeTSP bruteForce() {
		routeTSP bestRoad = new routeTSP();
		
		//inicializar lista a permutar y calcular su coste----------
		routeTSP route = new routeTSP();
		LinkedList<Integer> cities = new LinkedList<Integer>();
		for (int i = 0; i < n; i++){
			cities.add(i);
		}
		double minCost = getCost(cities);
		
		//Bucle, se permuta y si el coste es menor pasa a ser minCost
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
		    indexes[i] = 0;
		}
		int i = 0;
		while (i < n) {
		    if (indexes[i] < i) {
		    	Collections.swap(cities, i % 2 == 0 ?  0: indexes[i], i);
		        double cost = getCost(cities);
		        if ( minCost>cost) {
		        	minCost=cost;
		        	bestRoad.setCities(cities); //esto no hace falta no? vae con hacerlo al final
		        }
		        indexes[i]++;
		        i = 0;
		    } else {
		        indexes[i] = 0;
		        i++;
		    }
		}
		bestRoad.setCost(getCost(bestRoad.getCities()));
		return bestRoad;
	}
	
	public static routeTSP greedy() {
		routeTSP bestRoute = greedyStartingFromI(0);
		for(int i = 1; i < n; i++) {
			routeTSP route = greedyStartingFromI(i);
			if(route.getCost() < bestRoute.getCost()) {
				bestRoute = route;
			}
		}
		return bestRoute;
	
		
	}
	
	public static routeTSP greedyStartingFromI(int i) {
		//ciudades que forman el camino hasta el momento
		LinkedList<Integer> cities = new LinkedList<Integer>();
		cities.add(i);
		
		//coste del camino hasta el momento
		int minCost = 0;
		
		//Lista de ciudades por visitar
		LinkedList<Integer> candidates = new LinkedList<Integer>();
		for (int j = 0; j < n; j++){
			if(j != i) {
				candidates.add(j);
			}
		}
		
		//añadimos el mejor candidato hasta que no quede ninguno
		while(!candidates.isEmpty()) { 
			int nextStepCost = distances[cities.getLast()][candidates.getFirst()];
			int nextStep = candidates.getFirst();
			for(int j : candidates) {
				int costStep = distances[cities.getLast()][j];
				if(costStep < nextStepCost) {
					nextStepCost = costStep;
					nextStep = j;
				}
			}
			cities.add(nextStep);
			minCost += nextStepCost;
			candidates.remove(new Integer(nextStep));
		}
		
		routeTSP retVal = new routeTSP();
		retVal.setCities(cities);
		retVal.setCost(minCost);
		return retVal;
	}
	/*
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
			System.out.println(c.isEmpty());
			System.out.println(c.size());
			for(int j : c){
				//A�adir ciudad al camino y aumentar el coste
				r.getCities().addLast(new Integer(j));
				r.setCost(r.getCost() + distances[i][j]);
				
				//eliminar la ciudad a�adida de la lista de posibles ciudades siguientes
				c.remove(new Integer(j));
				
				routeTSP partialSolution = bruteForceTSPR(r, c, j);
				
				//Nos quedamos con el camino de menor coste
				if(retVal == null) {
					retVal = partialSolution;
				}
				if(retVal.getCost() < partialSolution.getCost()) {
					retVal.setCost(partialSolution.getCost());
					retVal.setCities(partialSolution.getCities());
				}
				if(c.isEmpty()) {
					System.out.println("hola");
					break;
				}
			}
		}
		else {
			//si no quedan ciudades por visitar sumamos el coste de ir 
			//de la ultima a la primera
			retVal = r;
			retVal.setCost(distances[route.getCities().getLast()][route.getCities().getFirst()]);
		}
		return retVal;
	}
	*/
	public static void readFile(String nomFich){
		try {
			Scanner ent = new Scanner(new FileReader(nomFich));
			String line = "";
			String [] listNumbers = {};
			if(ent.hasNext()) {
				line = ent.nextLine();
				listNumbers = line.trim().split("\\s+");
			}
			n=listNumbers.length;
			
			distances=new int[n][n];
			int i = 0;
			while (i<n) {
				int num=0;
				for (int j=0; j<listNumbers.length; j++) {
					num = Integer.parseInt(listNumbers[j]);
	            	distances[i][j]=num;
				}
				i++;
				if(ent.hasNext()) {
					line = ent.nextLine();
					listNumbers = line.trim().split("\\s+");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void printMatrix(){
		for (int i=0; i<n; i++){
			for (int j=0;j<n; j++){
				System.out.print(distances[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static int getCost(LinkedList<Integer> road) {
		int cost = 0;
		for (int i=0; i<n-1; i++ ) {
			cost += distances[road.get(i)][road.get(i+1)];
		}
		cost += distances[road.get(n-1)][road.get(0)];
		return cost;
	}
}
