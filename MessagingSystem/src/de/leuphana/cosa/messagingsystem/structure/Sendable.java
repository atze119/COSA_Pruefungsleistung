package de.leuphana.cosa.messagingsystem.structure;

public class Sendable {
	private String sender;
	private String receiver;
	private String content;
	private MessageType messageType;

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getContent() {
		return content;
	}

	public MessageType getMessageType() {
		return messageType;
	}

}