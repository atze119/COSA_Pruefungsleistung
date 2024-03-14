package de.leuphana.cosa.componentservicebus.structure.connector;

import de.leuphana.cosa.pricingsystem.structure.Chargeable;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;

public class TicketInformationToChargableAdapter {
	
	public Chargeable convert(double routeDistance, String priceGroup) {
		return new Chargeable() {
			
			@Override
			public double getRouteDistance() {
				return routeDistance;
			}
			
			@Override
			public PriceGroup getPriceGroup() {
				return PriceGroup.valueOf(priceGroup);
			}
		};
	}

}
