package de.leuphana.cosa.componentservicebus.structure.connector;

import de.leuphana.cosa.documentsystem.structure.Document;
import de.leuphana.cosa.printingsystem.structure.Printable;

public class DocumentToPrintableAdapter {
	
	public Printable convert(Document document) {

		// anonyme innere Klassen
		Printable printable = new Printable() {

			public String getTitle() {
				return document.getName();
			}

			public String getContent() {
				return document.getContent();
			}
		};

		return printable;
	}
	
}