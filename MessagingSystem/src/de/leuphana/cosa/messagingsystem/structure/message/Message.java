package de.leuphana.cosa.messagingsystem.structure.message;

public abstract class Message {
	private MessageHeader messageHeader;
	private MessageBody messageBody; 
	
	public Message(MessageHeader messageHeader, MessageBody messageBody) {
		this.messageBody = messageBody;
		this.messageHeader = messageHeader;
	}

}
