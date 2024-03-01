package de.leuphana.cosa.messagingsystem.structure.communicationpartner;

public class EmailReceiver implements Receiver {
	// sp�ter Role-Object-Pattern
	private String name;
	private String address;
	
	public EmailReceiver(String receiverAddress) {
		this.setAddress(receiverAddress);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
