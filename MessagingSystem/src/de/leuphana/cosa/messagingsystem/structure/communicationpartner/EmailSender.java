package de.leuphana.cosa.messagingsystem.structure.communicationpartner;

public class EmailSender implements Sender {
	// sp�ter Role-Object-Pattern
	private String name;
	private String address;
	
	public EmailSender(String senderAddress) {
		this.setAddress(senderAddress);
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
