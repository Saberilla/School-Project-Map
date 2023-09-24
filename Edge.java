// PROG2 VT2023, Inlamningsuppgift, del 1
// Grupp 077
// Sara Berg sabe4314
import java.util.*;

public class Edge<N> {
	
	private final N destination;
	private String name;
	private int weight;
	
	public Edge(N destination, String name, int weight) { 
		this.destination = destination;
		this.name = name;
		this.weight = weight;
	}
	
	public N getDestination() {
		return this.destination;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void setWeight(int weight) {
		if (weight < 0) {
			throw new IllegalArgumentException("error: weight can't be negative");
		} else {
			this.weight = weight;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	
	@Override
	public String toString() { 
		return "to " + this.destination + " by " + this.name + " takes " + this.weight;
	}

}
