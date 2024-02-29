package de.leuphana.cosa.routesystem.structure;

public class Location {
	private String name;
	private double xCoordinate;
	private double yCoordiante;
	
	public Location(String name, double xCoordinate, double yCoordinate) {
		this.setName(name);
		this.xCoordinate = xCoordinate;
		this.yCoordiante = yCoordinate;
	}
	
	public double getxCoordinate() {
		return xCoordinate;
	}
	
	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	public double getyCoordiante() {
		return yCoordiante;
	}
	
	public void setyCoordiante(double yCoordiante) {
		this.yCoordiante = yCoordiante;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
