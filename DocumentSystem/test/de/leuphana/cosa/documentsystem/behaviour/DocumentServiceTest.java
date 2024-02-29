package de.leuphana.cosa.documentsystem.behaviour;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.documentsystem.behaviour.service.command.DocumentSystemCommandService;
import de.leuphana.cosa.documentsystem.structure.Document;
import de.leuphana.cosa.documentsystem.structure.Documentable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DocumentServiceTest {

	private static DocumentSystemCommandService documentService;
	private static Documentable documentable;
	
	@BeforeAll
	static void setUp() throws Exception {
		documentable = new Documentable() {

			@Override
			public String getName() {
				return "New Test Document";
			}

			@Override
			public String getContent() {
				return "New content";
			}
		};
	}

	@AfterAll
	static void tearDown() throws Exception {
		documentService = null;
	}
	
	@Test
	@Order(1)
	void canDocumentServiceBeAccessed() {
		documentService = getService(DocumentSystemCommandService.class);
		Assertions.assertNotNull(documentService);
	}

	@Test
	@Order(2)
	void canDocumentBeCreated()  {
		Document document = documentService.createDocument(documentable);
		System.out.println("Document created with name: " + document.getName());
		Assertions.assertNotNull(document);
	}
	
	// helper-function
	static <T> T getService(Class<T> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(DocumentSystem.class);
        if (bundle != null) {
            ServiceTracker<T, T> st =
                new ServiceTracker<T, T>(
                    bundle.getBundleContext(), clazz, null);
            st.open();
            if (st != null) {
                try {
                    // give the runtime some time to startup
                    return st.waitForService(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}