package de.leuphana.cosa.documentsystem.behaviour;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.documentsystem.behaviour.service.command.DocumentSystemCommandService;
import de.leuphana.cosa.documentsystem.behaviour.service.query.DocumentSystemQueryService;
import de.leuphana.cosa.documentsystem.structure.Document;
import de.leuphana.cosa.documentsystem.structure.Documentable;

@Component(immediate = true, service = DocumentSystemCommandService.class)
public class DocumentSystem implements BundleActivator, DocumentSystemCommandService, DocumentSystemQueryService {
	private Map<String, Document> documentMap;
	
	public static final String EVENT_TOPIC = "documentsystem/Document";
	
	@Reference
	private EventAdmin eventAdmin;
	
	public DocumentSystem() {
		documentMap = new HashMap<String, Document>();
	}
	
	@Override
	public void start(BundleContext context) {
		System.out.println("DocumentSystem activated!");
	}
	
	@Override
	public void stop(BundleContext context) {
		System.out.println("DocumentSystem deactivated!");
	}
	
	// --------------------  Command --------------------------------------------------------

	@Override
	public Document createDocument(Documentable documentable) {
		Document document = new Document(documentable.getName());
		document.setContent(documentable.getContent());
		
		System.out.println("Document created: " + documentable.getName());
		
		documentMap.put(documentable.getName(), document);

		// OSGI-Eventing
		Dictionary<String, Document> eventProps = new Hashtable<String, Document>();
		eventProps.put(Document.class.getSimpleName(), document);
		Event event = new Event(EVENT_TOPIC, eventProps);
		eventAdmin.sendEvent(event);
		
		return document;
	}
	
	// --------------------- Query -------------------------------------------
	
	@Override
	public Document getDocument(String documentName) {
		return documentMap.get(documentName);
	}
	

}