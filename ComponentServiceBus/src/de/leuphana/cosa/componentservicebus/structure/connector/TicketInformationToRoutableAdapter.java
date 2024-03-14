package de.leuphana.cosa.componentservicebus.structure.connector;

import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.routesystem.structure.Routable;

public class TicketInformationToRoutableAdapter {
	
	public Routable convert(String startLocation, String endLocation) {
		
		return new Routable() {
			
			@Override
			public LocationName getStartLocation() {
				return LocationName.valueOf(startLocation);
			}
			
			@Override
			public LocationName getEndLocation() {
				return LocationName.valueOf(endLocation);
			}
		};
		
	}

}
