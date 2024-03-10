package de.leuphana.cosa.ticketautomaton.behaviour.service.command;

public interface TicketAutomatonCommandService {
	void createTicket(String priceGroup, String startLocation, String endLocation, double price, double distance);
	void printCommandInterface();
}