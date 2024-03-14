package de.leuphana.cosa.ticketautomaton.structure;

public class Ticket {
	
	private String priceGroup;
	private String startLocation;
	private String endLocation;

	public Ticket(String name, String startLocation, String endLocation) {
		this.priceGroup = name;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	public String getPriceGroup() {
		return priceGroup;
	}
	
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
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
