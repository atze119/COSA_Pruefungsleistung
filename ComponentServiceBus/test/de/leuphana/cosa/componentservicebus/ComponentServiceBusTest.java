package de.leuphana.cosa.componentservicebus;

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

import de.leuphana.cosa.componentservicebus.behaviour.ComponentServiceBus;
import de.leuphana.cosa.componentservicebus.behaviour.command.ComponentServiceBusCommandService;
import de.leuphana.cosa.documentsystem.behaviour.DocumentSystem;
import de.leuphana.cosa.documentsystem.behaviour.service.command.DocumentSystemCommandService;
import de.leuphana.cosa.messagingsystem.behaviour.MessagingSystem;
import de.leuphana.cosa.messagingsystem.behaviour.service.command.MessagingSystemCommandService;
import de.leuphana.cosa.pricingsystem.behaviour.PricingSystem;
import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.printingsystem.behaviour.PrintingSystem;
import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.routesystem.behaviour.RouteSystem;
import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComponentServiceBusTest {

	private static DocumentSystemCommandService documentService;
	
	@BeforeAll
	public static void setup() {
		
		//documentService = getService(DocumentSystemCommandService.class, DocumentSystem.class);
		//PrintingSystem printingSystem = new PrintingSystem();
		//messagingSystem = new MessagingSystem("MessagingSystem");
		// TODO: changed documentsystem not extending component componentServiceBus.registerComponent(documentSystem);
		//componentServiceBus.registerComponent(printingSystem);
		//componentServiceBus.registerComponent(messagingSystem);
		//documentSystem.addDocumentEventListener(componentServiceBus);
		//printingSystem.addPrintingEventListener(componentServiceBus);
	}
	
	@AfterAll
	public static void tearDown() {
		
	}
	
	@Test
	@Order(1)
	void canComponentServiceBusBeAccessed() {
		ComponentServiceBusCommandService componentServiceBus = getService(ComponentServiceBusCommandService.class, ComponentServiceBus.class);
		Assertions.assertNotNull(componentServiceBus);
	}
	
	@Test
	@Order(2)
	void canDocumentServiceBeAccessed() {
		documentService = getService(DocumentSystemCommandService.class, DocumentSystem.class);
		Assertions.assertNotNull(documentService);
	}
	
	@Test
	@Order(3)
	void canMessagingServiceBeAccessed() {
		MessagingSystemCommandService messagingService = getService(MessagingSystemCommandService.class, MessagingSystem.class);
		Assertions.assertNotNull(messagingService);
	}
	
	@Test
	@Order(4)
	void canPricingServiceBeAccessed() {
		PricingSystemCommandService pricingService = getService(PricingSystemCommandService.class, PricingSystem.class);
		Assertions.assertNotNull(pricingService);
	}
	
	@Test
	@Order(5)
	void canPrintingServiceBeAccessed() {
		PrintingCommandService printingService = getService(PrintingCommandService.class, PrintingSystem.class);
		Assertions.assertNotNull(printingService);
	}
	
	@Test
	@Order(6)
	void canRouteServiceBeAccessed() {
		RouteSystemCommandService routeService = getService(RouteSystemCommandService.class, RouteSystem.class);
		Assertions.assertNotNull(routeService);
	}
	
	// TODO: TicketAutomaton doesnt work -> maybe componentServiceBus doesnt need any tests

//	@Test
//	@Order(4)
//	// TODO: This needs to be deleted i think. There should be no command-service in componentservicebus
//	void isDocumentCreatedTest() {
////		messagingSystem.addMessagingEventListener(new MessagingEventListener() {
////
////			@Override
////			public void onMessageSent(DeliveryReportEvent deliveryReportEvent) {
////				deliveryReport = deliveryReportEvent.getDeliveryReport();
////			}
////
////		});
//		Documentable documentable = new Documentable() {
//			
//			@Override
//			public String getName() {
//				return "New Document";
//			}
//			
//			@Override
//			public String getContent() {
//				return "New content";
//			}
//		};
//
//		// triggers the event-delegation!
//		documentService.createDocument(documentable);
//		
//		//componentServiceBus.createDocument(documentable.getName(), documentable.getContent());
//		// TODO: This needs to be changed -> variable / attribute in ComponentServiceBus bad practice
//		ComponentServiceBus comp = (ComponentServiceBus) componentServiceBus;
//		Assertions.assertNotNull(comp.deliveryReport);
//	}
	
	// helper-function
		static <T, Y> T getService(Class<T> clazz, Class<Y> bundleClass) {
	        Bundle bundle = FrameworkUtil.getBundle(bundleClass);
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
