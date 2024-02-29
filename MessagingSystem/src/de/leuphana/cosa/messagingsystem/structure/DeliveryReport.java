package de.leuphana.cosa.messagingsystem.structure;

public class DeliveryReport {
	String sender;
	String receiver;
	String content;
	String messageType;
	private boolean isDeliverySuccessful;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public boolean isDeliverySuccessful() {
		return isDeliverySuccessful;
	}

	public void setDeliverySuccessful(boolean isDeliverySuccessful) {
		this.isDeliverySuccessful = isDeliverySuccessful;
	}

}