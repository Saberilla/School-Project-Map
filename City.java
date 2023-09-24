// PROG2 VT2023, Inlamningsuppgift, del 2
// Grupp 077
// Sara Berg sabe4314

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class City extends Circle {
	
	private String name;
	private double x;
	private double y;
	
	public City (double x, double y, String name) {
		super(x, y, 10);
		setFill(Color.BLUE);
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void turnBlue() {
		setFill(Color.BLUE);
	}
	
	public void turnRed() {
		setFill(Color.RED);
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		long tempX = Double.doubleToLongBits(this.x);
		hash = 31 * hash + (int)(tempX^(hash>>32));
		long tempY = Double.doubleToLongBits(this.y);
		hash = 31 * hash + (int)(tempY^(hash>>32));
		hash = 31 * hash + this.name == null ? 0 : this.name.hashCode();
		return hash;
		
	}
	
	@Override
	public boolean equals(Object o) { //NY
		City anotherCity = (City) o;
		if (anotherCity.getX() != this.x) {
			return false;
		}
		if (anotherCity.getY() != this.y) {
			return false;
		}
		if (anotherCity.getName() != this.name) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
