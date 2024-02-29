package de.leuphana.cosa.messagingsystem.structure.content;

public class EmailContent implements Content {
	private String text;
	private String attachment;
	
	public EmailContent(String content) {
		this.text = content;
	}
}
