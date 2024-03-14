package de.leuphana.cosa.componentservicebus.behaviour;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.leuphana.cosa.componentservicebus.structure.connector.DocumentToPrintableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.PrintReportToSendableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.TextToDocumentableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.TicketInformationToChargableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.TicketInformationToRoutableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.TicketInformationToTicketTemplateAdapter;
import de.leuphana.cosa.documentsystem.behaviour.DocumentSystem;
import de.leuphana.cosa.documentsystem.behaviour.service.command.DocumentSystemCommandService;
import de.leuphana.cosa.documentsystem.structure.Document;
import de.leuphana.cosa.documentsystem.structure.Documentable;
import de.leuphana.cosa.messagingsystem.behaviour.MessagingSystem;
import de.leuphana.cosa.messagingsystem.behaviour.service.command.MessagingSystemCommandService;
import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.pricingsystem.behaviour.PricingSystem;
import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.pricingsystem.structure.Chargeable;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.printingsystem.behaviour.PrintingSystem;
import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.UserAccount;
import de.leuphana.cosa.routesystem.behaviour.RouteSystem;
import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;
import de.leuphana.cosa.routesystem.structure.Routable;
import de.leuphana.cosa.routesystem.structure.Route;
import de.leuphana.cosa.ticketautomaton.behaviour.TicketAutomaton;
import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;

@Component (
		immediate = true, service = EventHandler.class,
		property = {
				EventConstants.EVENT_TOPIC + "=" + DocumentSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + MessagingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + PrintingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + PricingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + RouteSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + TicketAutomaton.EVENT_TOPIC_TICKET,
		})
public class ComponentServiceBus implements BundleActivator, EventHandler {
	
	private DocumentToPrintableAdapter documentToPrintableAdapter;
	private PrintReportToSendableAdapter printReportToSendableAdapter;
	private TextToDocumentableAdapter textToDocumentableAdapter;
	private TicketInformationToTicketTemplateAdapter ticketInformationToTextAdapter;
	private TicketInformationToRoutableAdapter ticketInformationToRoutableAdapter;
	private TicketInformationToChargableAdapter ticketInformationToChargableAdapter;
	
	private Ticket ticketBackup;
	private Price priceBackup;
	private Route routeBackup;
	
	private Logger logger;
	private Marker debugModeMarker;
	
	@Reference
	private MessagingSystemCommandService messagingCommandService;
	
	@Reference
	private PrintingCommandService printingCommandService;
	
	@Reference
	private PricingSystemCommandService pricingCommandService;
	
	@Reference
	private RouteSystemCommandService routeCommandService;
	
	@Reference
	private DocumentSystemCommandService documentCommandService;
	
	@Reference
	private TicketAutomatonCommandService ticketCommandService;
	
	public ComponentServiceBus() {
		documentToPrintableAdapter = new DocumentToPrintableAdapter();
		printReportToSendableAdapter = new PrintReportToSendableAdapter();
		textToDocumentableAdapter = new TextToDocumentableAdapter();
		ticketInformationToTextAdapter = new TicketInformationToTicketTemplateAdapter();
		ticketInformationToRoutableAdapter = new TicketInformationToRoutableAdapter();
		ticketInformationToChargableAdapter = new TicketInformationToChargableAdapter();
		
		logger = LogManager.getLogger(this.getClass());
		boolean isDebugMode = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
		if (isDebugMode) {
			debugModeMarker = MarkerManager.getMarker("DEBUG_MODE");
		}	
	}
	
	@Override
	public void start(BundleContext bundleContext) {
		System.out.println("ComponentServiceBus activated!");
	}
	
	@Override
	public void stop(BundleContext bundleContext) {
		System.out.println("ComponentServiceBus deactivated!");
	}
	
	// osgi-eventhandler
	@Override
	public void handleEvent(Event event) {
		String topic = event.getTopic();
		switch (topic) {
			case DocumentSystem.EVENT_TOPIC:
				Document document = (Document) event.getProperty(Document.class.getSimpleName());
				logger.debug(debugModeMarker, "Event is document: " + document.getName());
				Printable printable = documentToPrintableAdapter.convert(document);
	
				printingCommandService.printDocument(printable, priceBackup.getAmount(), new UserAccount());
				break;
			case MessagingSystem.EVENT_TOPIC:
				DeliveryReport deliveryReport = (DeliveryReport) event.getProperty(DeliveryReport.class.getSimpleName());
				logger.debug("Event is deliveryReport.type: " + deliveryReport.getMessageType() + " and delivery successful? " + deliveryReport.isDeliverySuccessful());
				break;
			case PricingSystem.EVENT_TOPIC:
				Price price = (Price) event.getProperty(Price.class.getSimpleName());
				
				priceBackup = price;
				
				logger.debug(debugModeMarker, "Event is price and pricegroup is: " + price.getPriceGroup() + " price: " + price.getAmount() + "€");
				String ticketDocumentTemplate = ticketInformationToTextAdapter.convert(price, routeBackup);
				Documentable documentable = textToDocumentableAdapter.convert(price.getPriceGroup().toString(), ticketDocumentTemplate);
				documentCommandService.createDocument(documentable);
				break;
			case PrintingSystem.EVENT_TOPIC:
				PrintReport printReport = (PrintReport) event.getProperty(PrintReport.class.getSimpleName());
				logger.debug(debugModeMarker, "Event is printreport: " + printReport.getName());			
				Sendable sendable = printReportToSendableAdapter.convert(printReport);
		
				DeliveryReport delivery = messagingCommandService.sendMessage(sendable);
				logger.debug(debugModeMarker, "MessagecommandService worked, deliveryReport is: " + delivery + " " + delivery.getMessageType());
				break;
			case RouteSystem.EVENT_TOPIC:
				Route route = (Route) event.getProperty(Route.class.getSimpleName());
				logger.debug(debugModeMarker, "Event is route: " + route);
				
				routeBackup = route;
				Chargeable chargeable = ticketInformationToChargableAdapter.convert(route.getDistance(), ticketBackup.getPriceGroup());
				pricingCommandService.calculatePrice(chargeable);
				break;
			case TicketAutomaton.EVENT_TOPIC_TICKET:
				Ticket ticket = (Ticket) event.getProperty(Ticket.class.getSimpleName());
				logger.debug(debugModeMarker, "Event is ticket and ticket priceGroup is: " + ticket.getPriceGroup());
				ticketBackup = ticket;
				Routable routable = ticketInformationToRoutableAdapter.convert(ticket.getStartLocation(), ticket.getEndLocation());
				routeCommandService.createRoute(routable);
				break;
			default: System.out.println("Couldn't resolve the EVENT_TOPIC: " + topic);
		}
		
	}
	
}