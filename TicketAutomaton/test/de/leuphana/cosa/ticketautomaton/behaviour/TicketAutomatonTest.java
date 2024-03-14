package de.leuphana.cosa.ticketautomaton.behaviour;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.structure.Ticket;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketAutomatonTest {
	private static TicketAutomatonCommandService ticketService;
	
	private static InputStream sysInBackup;
	
	@BeforeAll
	public static void setup() {
		sysInBackup = System.in;
	}
	
	@AfterAll
	public static void tearDown() {
		ticketService = null;
		System.setIn(sysInBackup);
	}
	
	@Test
	@Order(1)
	void canTicketServiceBeAccessed() {
		ticketService = getService(TicketAutomatonCommandService.class);
		Assertions.assertNotNull(ticketService);
	} 

	@Test
	void canTicketBeCreated() {
		String userInput = "1 3 1";
		ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(in);
		
		Ticket ticket = ticketService.createTicket();
		Assertions.assertNotNull(ticket);
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