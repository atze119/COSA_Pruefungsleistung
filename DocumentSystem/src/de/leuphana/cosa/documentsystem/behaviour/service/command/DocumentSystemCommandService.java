package de.leuphana.cosa.documentsystem.behaviour.service.command;

import de.leuphana.cosa.documentsystem.structure.Document;
import de.leuphana.cosa.documentsystem.structure.Documentable;

public interface DocumentSystemCommandService {

	Document createDocument(Documentable documentable);

}