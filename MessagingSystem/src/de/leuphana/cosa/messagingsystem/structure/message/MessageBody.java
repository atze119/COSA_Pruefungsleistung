package de.leuphana.cosa.messagingsystem.structure.message;

import de.leuphana.cosa.messagingsystem.structure.content.Content;

public class MessageBody {
	private Content content;
	
	public MessageBody(Content content) {
		this.setContent(content);
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

}
