package de.leuphana.cosa.messagingsystem.structure.communicationpartner;

public class EmailSender implements Sender {
	// sp�ter Role-Object-Pattern
	private String name;
	private String address;
	
	public EmailSender(String senderAddress) {
		this.address = senderAddress;
	}

}
