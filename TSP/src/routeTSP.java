import java.util.LinkedList;
import java.util.List;

public class routeTSP {

	private LinkedList<Integer> cities;
	private int cost;
	
	//CONSTRUCTORES-------------------------------------
	public routeTSP(){
		cities = null;
		cost = 0;
	}
	
	public routeTSP(routeTSP r){
		cities = r.cities;
		cost = r.cost;
	}

	//SETERS Y GETERS-----------------------------------
	public LinkedList<Integer> getCities() {
		return cities;
	}

	public void setCities(LinkedList<Integer> cities) {
		this.cities = cities;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
