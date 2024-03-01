package de.leuphana.cosa.messagingsystem.structure.message;

public abstract class Message {
	private MessageHeader messageHeader;
	private MessageBody messageBody; 
	
	public Message(MessageHeader messageHeader, MessageBody messageBody) {
		this.setMessageBody(messageBody);
		this.setMessageHeader(messageHeader);
	}

	public MessageHeader getMessageHeader() {
		return messageHeader;
	}

	public void setMessageHeader(MessageHeader messageHeader) {
		this.messageHeader = messageHeader;
	}

	public MessageBody getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(MessageBody messageBody) {
		this.messageBody = messageBody;
	}
	
	

}
