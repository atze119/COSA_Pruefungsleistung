package de.leuphana.cosa.ticketautomaton.structure;

public class Ticket {
	// Name == PriceGroup
	private String name;
	private String startLocation;
	private String endLocation;

	public Ticket(String name, String startLocation, String endLocation) {
		this.name = name;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	public String getName() {
		return name;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

}
