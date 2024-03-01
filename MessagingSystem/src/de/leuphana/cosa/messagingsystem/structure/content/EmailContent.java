package de.leuphana.cosa.messagingsystem.structure.content;

public class EmailContent implements Content {
	private String text;
	private String attachment;
	
	public EmailContent(String content) {
		this.setText(content);
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
