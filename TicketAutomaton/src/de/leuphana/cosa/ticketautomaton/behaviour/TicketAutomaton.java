package de.leuphana.cosa.ticketautomaton.behaviour;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;
import de.leuphana.cosa.ticketautomaton.structure.TicketPurchaseInformation;

// Deleted immediate = true, maybe add it again
@Component(service = TicketAutomatonCommandService.class)
public class TicketAutomaton implements TicketAutomatonCommandService { // , TicketAutomatonConfigurationService
	
	public static final String EVENT_TOPIC_TICKET_PURCHASE = "ticketsystem/TicketPurchaseInformation";
	public static final String EVENT_TOPIC_TICKET = "ticketsystem/Ticket";
	
	@Reference(bind = "bindEventAdmin", unbind = "unbindEventAdmin")
	private EventAdmin eventAdmin;

	// OSGI event delegation
	public void bindEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
	
	public void unbindEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}
		
	@Activate
	protected void start(BundleContext context) {
		System.out.println("TicketAutomaton activated!");

		printCommandInterface();
	}
	
	@Deactivate
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
	
	private void printCommandInterface() {
		Scanner scanner = new Scanner(System.in);
		
		String startLocationName = chooseLocation(scanner, "start");
		String endLocationName = chooseLocation(scanner, "end");
		String tariff = chooseTariff(scanner);
		
		Ticket ticket = new Ticket(tariff, startLocationName, endLocationName);
		
		Dictionary<String, Ticket> eventProps = new Hashtable<String, Ticket>();
		eventProps.put(Ticket.class.getSimpleName(), ticket);
		Event event = new Event(EVENT_TOPIC_TICKET, eventProps);
		eventAdmin.postEvent(event);
	}

	private String chooseTariff(Scanner scanner) {
		System.out.println("Please choose one of the following pricegroups:");
		System.out.println("1. Normal-Tariff");
		System.out.println("2. Cheaper-Tariff");
		System.out.println("3. Bargain-Tariff");
		
		String tariff = null;
		while (tariff == null) {			
			int userInput = scanner.nextInt();
			switch (userInput) {
			case 1:
				// TODO: I think these should be enums, but would be another dependency
				tariff = "NORMAL_TARIFF";
				break;
			case 2:
				tariff = "CHEAPER_TARIFF";
				break;
			case 3:
				tariff = "BARGAIN_TARIFF";
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
	
	private String chooseLocation(Scanner scanner, String choosePoint) {
		if (choosePoint == "start") {
			System.out.println("Please choose your startpoint: ");			
		} else if (choosePoint == "end") {
			System.out.println("Please choose your endpoint: ");
		}
	    System.out.println("1. Lunenburg");
	    System.out.println("2. Hamburg");
	    System.out.println("3. Munich");
	    System.out.println("4. Bremen");
	    System.out.println("5. Dusseldorf");
	    System.out.println("6. Kiel");
	   
	    String location = null;
	    while (location == null) {	    	
	    	int userInput = scanner.nextInt();
	    	switch (userInput) {
	    	case 1:
	    		location = "LUNENBURG";
	    		break;
	    	case 2:
	    		location = "HAMBURG";
	    		break;
	    	case 3:
	    		location = "MUNICH";
	    		break;
	    	case 4:
	    		location = "BREMEN";
	    		break;
	    	case 5: 
	    		location = "DUSSELDORF";
	    		break;
	    	case 6:
	    		location = "KIEL";
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