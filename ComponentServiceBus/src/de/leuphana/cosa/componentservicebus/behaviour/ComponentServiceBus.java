package de.leuphana.cosa.componentservicebus.behaviour;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.leuphana.cosa.componentservicebus.behaviour.command.ComponentServiceBusCommandService;
import de.leuphana.cosa.componentservicebus.structure.connector.DocumentToPrintableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.PrintReportToSendableAdapter;
import de.leuphana.cosa.componentservicebus.structure.connector.TextToDocumentableAdapter;
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
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import de.leuphana.cosa.printingsystem.behaviour.PrintingSystem;
import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.UserAccount;
import de.leuphana.cosa.routesystem.behaviour.RouteSystem;
import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;
import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.routesystem.structure.Route;
import de.leuphana.cosa.ticketautomaton.behaviour.TicketAutomaton;
import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;
import de.leuphana.cosa.ticketautomaton.structure.TicketPurchaseInformation;

@Component (
		immediate = true, service = {EventHandler.class, ComponentServiceBusCommandService.class},
		property = {
				EventConstants.EVENT_TOPIC + "=" + DocumentSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + MessagingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + PrintingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + PricingSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + RouteSystem.EVENT_TOPIC,
				EventConstants.EVENT_TOPIC + "=" + TicketAutomaton.EVENT_TOPIC_TICKET,
				EventConstants.EVENT_TOPIC + "=" + TicketAutomaton.EVENT_TOPIC_TICKET_PURCHASE
		})
public class ComponentServiceBus implements EventHandler, ComponentServiceBusCommandService {
	
	private DocumentToPrintableAdapter documentToPrintableAdapter;
	private PrintReportToSendableAdapter printReportToSendableAdapter;
	private TextToDocumentableAdapter textToDocumentableAdapter;
	public DeliveryReport deliveryReport;
	
	// TODO: This is real shit
	private Ticket ticketBackup;
	private Route routeBackup;
	
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
	private Price priceBackup;
	
	public ComponentServiceBus() {
		documentToPrintableAdapter = new DocumentToPrintableAdapter();
		printReportToSendableAdapter = new PrintReportToSendableAdapter();
		textToDocumentableAdapter = new TextToDocumentableAdapter();
	}
	
//	@Override
//	public void start(BundleContext context) throws Exception {
//		System.out.println("ComponentServiceBus activated!");
//		ComponentServiceBus.bundleContext = context;
//		Dictionary<String, String> d = new Hashtable<String, String>();
//		d.put(EventConstants.EVENT_TOPIC,"documentsystem/Document");
//		d.put(EventConstants.EVENT_TOPIC,"messagingsystem/DeliveryReport");
//		d.put(EventConstants.EVENT_TOPIC, "printingsystem/PrintReport");
//		d.put(EventConstants.EVENT_TOPIC, "pricesystem/Price");
//		d.put(EventConstants.EVENT_TOPIC, "routesystem/Route");
//		d.put(EventConstants.EVENT_TOPIC, "ticketsystem/Ticket");
//		
//		context.registerService(EventHandler.class.getName(), this, d);
//		
//		// System.out.println(messagingCommandService); prints -> null
//	}
//
//	@Override
//	public void stop(BundleContext context) throws Exception {
//		System.out.println("ComponentServiceBus deactivated!");
//		//bundleContext.ungetService(bundleContext.getServiceReference(EventHandler.class));
//		ComponentServiceBus.bundleContext = null;
//	}
//	
	@Activate
	protected void start() {
		System.out.println("ComponentServiceBus activated!");
	}
	
	@Deactivate
	protected void stop() {
		System.out.println("ComponentServiceBus deactivated!");
	}
	
	// osgi-eventhandler
	@Override
	public void handleEvent(Event event) {
		// TODO: Delete System.outs (was for debugging) and maybe switch-case
		String topic = event.getTopic();
		switch (topic) {
		case DocumentSystem.EVENT_TOPIC:
			Document document = (Document) event.getProperty(Document.class.getSimpleName());
			System.out.println("Event is document: " + document.getName());
			Printable printable = documentToPrintableAdapter.convert(document);

			printingCommandService.printDocument(printable, priceBackup.getAmount(), new UserAccount());
			break;
			
		case MessagingSystem.EVENT_TOPIC:
			DeliveryReport deliveryReport = (DeliveryReport) event.getProperty(DeliveryReport.class.getSimpleName());
			System.out.println("Event is deliveryReport.type: " + deliveryReport.getMessageType() + " and delivery successful? " + deliveryReport.isDeliverySuccessful());
			// TODO: could set public attribute here to test if its not null after creating document
			this.deliveryReport = deliveryReport;
			break;
			
		case PricingSystem.EVENT_TOPIC:
			Price price = (Price) event.getProperty(Price.class.getSimpleName());
			
			priceBackup = price;
			
			System.out.println("Event is price and pricegroup is: " + price.getPriceGroup() + " price: " + price.getAmount() + "€");
			ticketCommandService.createTicket(price.getPriceGroup().toString(), ticketBackup.getStartLocation(), ticketBackup.getEndLocation(), price.getAmount(), routeBackup.getDistance());
			break;
			
		case PrintingSystem.EVENT_TOPIC:
			PrintReport printReport = (PrintReport) event.getProperty(PrintReport.class.getSimpleName());
			System.out.println("Event is printreport: " + printReport.getName());			
			Sendable sendable = printReportToSendableAdapter.convert(printReport);
	
			DeliveryReport delivery = messagingCommandService.sendMessage(sendable);
			System.out.println("MessagecommandService worked, deliveryReport is: " + delivery + " " + delivery.getMessageType());
			break;
			
		case RouteSystem.EVENT_TOPIC:
			Route route = (Route) event.getProperty(Route.class.getSimpleName());
			System.out.println("Event is route: " + route);
			
			// TODO: Maybe remove the backups and place them into fields, should work now because they only get set if event is happening!
			routeBackup = route;
			pricingCommandService.calculatePrice(route.getDistance(), PriceGroup.valueOf(ticketBackup.getName()));
			break;
			
		case TicketAutomaton.EVENT_TOPIC_TICKET:
			Ticket ticket = (Ticket) event.getProperty(Ticket.class.getSimpleName());
			System.out.println("Event is ticket and ticket priceGroup is: " + ticket.getName());
			ticketBackup = ticket;
			
			routeCommandService.createRoute(LocationName.valueOf(ticket.getStartLocation()), LocationName.valueOf(ticket.getEndLocation()));
			break;
			
		case TicketAutomaton.EVENT_TOPIC_TICKET_PURCHASE:
			TicketPurchaseInformation ticketPurchaseInformation = (TicketPurchaseInformation) event.getProperty(TicketPurchaseInformation.class.getSimpleName());
			Documentable documentable = textToDocumentableAdapter.convert(ticketPurchaseInformation.getName(), ticketPurchaseInformation.getContent());
			documentCommandService.createDocument(documentable);
			break;
		default: System.out.println("Couldn't resolve the EVENT_TOPIC!!!");
		}
		
	}
	
	// ------------------------------ Command ------------------------------------------------
	
	@Override
	public void createDocument(String documentName, String documentContent) {
		Documentable documentable = textToDocumentableAdapter.convert(documentName, documentContent);
		documentCommandService.createDocument(documentable);
	}
}