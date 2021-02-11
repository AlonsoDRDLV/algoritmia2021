package tsp;
import java.util.LinkedList;

public class routeTSP {

	private LinkedList<Integer> cities;
	private int cost;
	
	//CONSTRUCTORES-------------------------------------
	public routeTSP(){
		cities = new LinkedList<Integer>();
		cost = 0;
	}
	
	@SuppressWarnings("unchecked")
	public routeTSP(routeTSP r){
		cities = (LinkedList<Integer>) r.cities.clone();
		cost = r.cost;
	}

	//SETERS Y GETERS-----------------------------------
	public LinkedList<Integer> getCities() {
		return cities;
	}

	@SuppressWarnings("unchecked")
	public void setCities(LinkedList<Integer> cities) {
		this.cities = cities = (LinkedList<Integer>) cities.clone();
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
