package de.leuphana.cosa.componentservicebus.structure.connector;

import de.leuphana.cosa.documentsystem.structure.Documentable;

public class TextToDocumentableAdapter {

	public Documentable convert(String name, String content) {
		
		Documentable manageable = new Documentable() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getContent() {
				return content;
			}
		};
		
		return manageable;
	}

}