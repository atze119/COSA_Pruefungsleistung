package de.leuphana.cosa.messagingsystem.structure.message;

import de.leuphana.cosa.messagingsystem.structure.communicationpartner.Receiver;
import de.leuphana.cosa.messagingsystem.structure.communicationpartner.Sender;

public class MessageHeader {
	private Sender sender;
	private Receiver receiver;
	
	public MessageHeader(Sender sender, Receiver receiver) {
		this.setReceiver(receiver);
		this.setSender(sender);
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}
}
