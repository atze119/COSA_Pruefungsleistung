package de.leuphana.cosa.ticketautomaton.structure;

public class TicketPurchaseInformation {

	String name;
	String content;
	
	public TicketPurchaseInformation(String name, String content) {
		this.name = name;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}
}
