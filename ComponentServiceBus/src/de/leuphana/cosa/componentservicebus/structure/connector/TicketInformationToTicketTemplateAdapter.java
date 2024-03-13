package de.leuphana.cosa.componentservicebus.structure.connector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.routesystem.structure.Route;

public class TicketInformationToTicketTemplateAdapter {

	public String convert(Price price, Route route) {
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
				, date, timeStamp, route.getStartLocation().getName(), route.getEndLocation().getName(), route.getDistance(), price.getPriceGroup(), price.getAmount());
		
		return ticketText;
	}
	
}
