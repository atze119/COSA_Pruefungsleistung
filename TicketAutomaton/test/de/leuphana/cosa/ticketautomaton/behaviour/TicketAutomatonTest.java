package de.leuphana.cosa.ticketautomaton.behaviour;

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

import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketAutomatonTest {
	private static TicketAutomatonCommandService ticketService;
	private static PriceGroup priceGroup;
	private static LocationName startLocation;
	private static LocationName endLocation;
	private static double price;
	private static double distance;
	
	@BeforeAll
	public static void setup() {
		priceGroup = PriceGroup.BARGAIN_TARIFF;
		startLocation = LocationName.BREMEN;
		endLocation = LocationName.DUSSELDORF;
		price = 20.50;
		distance = 300.0;
	}
	
	@AfterAll
	public static void tearDown() {
		priceGroup = null;
		startLocation = null;
		endLocation = null;
		price = 0.0;
		distance = 0.0;
	}
	
	@Test
	@Order(1)
	void canTicketServiceBeAccessed() {
		ticketService = getService(TicketAutomatonCommandService.class);
		Assertions.assertNotNull(ticketService);
	} 

	@Test
	void canTicketBeCreated() {
		// TODO: implement this
//		Ticket ticket = ticketService.createTicket(priceGroup.toString(), startLocation.toString(), endLocation.toString(), price, distance);
		//Assert.assertNotNull(ticketAutomaton.createTicket("Ticket name", "Lüneburg", "Hamburg"));
	}
	
	// helper-function to retrieve service!
	static <T> T getService(Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(TicketAutomaton.class);
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