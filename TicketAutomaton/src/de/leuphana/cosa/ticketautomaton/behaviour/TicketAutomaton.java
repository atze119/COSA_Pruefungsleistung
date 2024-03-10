package de.leuphana.cosa.ticketautomaton.behaviour;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;
import de.leuphana.cosa.ticketautomaton.structure.TicketPurchaseInformation;

// Deleted immediate = true, maybe add it again
@Component(service = TicketAutomatonCommandService.class, property = {"osgi.command.scope=printCommandInterface", "osgi.command.function=printCommandInterface"})
public class TicketAutomaton implements BundleActivator, TicketAutomatonCommandService { // , TicketAutomatonConfigurationService
	
	public static final String EVENT_TOPIC_TICKET_PURCHASE = "ticketsystem/TicketPurchaseInformation";
	public static final String EVENT_TOPIC_TICKET = "ticketsystem/Ticket";
	
	@Reference // bind = "bindEventAdmin", unbind = "unbindEventAdmin"
	private EventAdmin eventAdmin;

	// OSGI event delegation
//	public void bindEventAdmin(EventAdmin eventAdmin) {
//		this.eventAdmin = eventAdmin;
//	}
//	
//	public void unbindEventAdmin(EventAdmin eventAdmin) {
//		this.eventAdmin = null;
//	}
		
	@Override
	public void start(BundleContext context) {
		System.out.println("TicketAutomaton activated!");
		// TODO: Change this to make it start with maybe "createTicket"
		System.out.println("Start the process with enter the command: printCommandInterface");
//		printCommandInterface();
	}
	
	@Override
	public void stop(BundleContext context) {
		System.out.println("TicketAutomaton deactivated!");		
	}
	
	@Override
	public void createTicket(String priceGroup, String startLocation, String endLocation, double price, double distance) {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String date = localDate.format(dateFormatter);
		String timeStamp = localDate.format(timeFormatter);

		String ticketText = String.format(
				"""
				 -----------------------------------------------------------------------
				|	Date: %s		Time: %s
				|	Startlocation: %s		EndLocation: %s
				|	Routedistance: %.2fKm
				|	
				|	Pricegroup: %s 	Price: %.2f€
				 -----------------------------------------------------------------------
				"""
				, date, timeStamp, startLocation, endLocation, distance, priceGroup, price);
		
		TicketPurchaseInformation ticketPurchaseInformation = new TicketPurchaseInformation(priceGroup, ticketText);
		
		Dictionary<String, TicketPurchaseInformation> eventProps = new Hashtable<String, TicketPurchaseInformation>();
		eventProps.put(TicketPurchaseInformation.class.getSimpleName(), ticketPurchaseInformation);
		Event event = new Event(EVENT_TOPIC_TICKET_PURCHASE, eventProps);
		
		// TODO: maybe change this, did post and send -> made no difference
		eventAdmin.sendEvent(event);
	}
	
	@Override
	public void printCommandInterface() {
		Scanner scanner = new Scanner(System.in);
		
		LocationName startLocationName = chooseLocation(scanner, "start");
		LocationName endLocationName = chooseLocation(scanner, "end");
		PriceGroup tariff = chooseTariff(scanner);
		
		Ticket ticket = new Ticket(tariff.toString(), startLocationName.toString(), endLocationName.toString());
		
		Dictionary<String, Ticket> eventProps = new Hashtable<String, Ticket>();
		eventProps.put(Ticket.class.getSimpleName(), ticket);
		Event event = new Event(EVENT_TOPIC_TICKET, eventProps);
		eventAdmin.postEvent(event);
	}

	private PriceGroup chooseTariff(Scanner scanner) {
		System.out.println("Please choose one of the following pricegroups:");
		System.out.println("1. Normal-Tariff");
		System.out.println("2. Cheaper-Tariff");
		System.out.println("3. Bargain-Tariff");
		
		PriceGroup tariff = null;
		while (tariff == null) {			
			int userInput = scanner.nextInt();
			switch (userInput) {
			case 1:
				tariff = PriceGroup.NORMAL_TARIFF; // "NORMAL_TARIFF"
				break;
			case 2:
				tariff = PriceGroup.CHEAPER_TARIFF; // "CHEAPER_TARIFF"
				break;
			case 3:
				tariff = PriceGroup.BARGAIN_TARIFF; // "BARGAIN_TARIFF"
				break;
			default:
				System.out.println("Couldn't resolve the pricegroup!");
			}
			if (tariff == null) {
				System.out.println("Please enter a valid number!");
			}
		}
		return tariff;
	}
	
	private LocationName chooseLocation(Scanner scanner, String choosePoint) {
		if (choosePoint == "start") {
			System.out.println("Please choose your start: ");			
		} else if (choosePoint == "end") {
			System.out.println("Please choose your destination: ");
		}
	    System.out.println("1. Lunenburg");
	    System.out.println("2. Hamburg");
	    System.out.println("3. Munich");
	    System.out.println("4. Bremen");
	    System.out.println("5. Dusseldorf");
	    System.out.println("6. Kiel");
	   
	    LocationName location = null;
	    while (location == null) {	    	
	    	int userInput = scanner.nextInt();
	    	switch (userInput) {
	    	case 1:
	    		location = LocationName.LUNENBURG; // "LUNENBURG"
	    		break;
	    	case 2:
	    		location = LocationName.HAMBURG; // "HAMBURG"
	    		break;
	    	case 3:
	    		location = LocationName.MUNICH; // "MUNICH"
	    		break;
	    	case 4:
	    		location = LocationName.BREMEN; // "BREMEN"
	    		break;
	    	case 5: 
	    		location = LocationName.DUSSELDORF; // "DUSSELDORF"
	    		break;
	    	case 6:
	    		location = LocationName.KIEL; // "KIEL"
	    		break;
	    	default:
	    		System.out.println("Couldn't resolve the location");
	    	}
	    	
	    	if (location == null) {
	    		System.out.println("Please enter a valid number!");
	    	}
	    }
	    
	    return location;
	}

}