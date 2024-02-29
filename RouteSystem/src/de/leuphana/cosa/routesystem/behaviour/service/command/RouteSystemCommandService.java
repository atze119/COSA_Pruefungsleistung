package de.leuphana.cosa.routesystem.behaviour.service.command;

import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.routesystem.structure.Route;

public interface RouteSystemCommandService {

	Route createRoute(LocationName startLocation, LocationName endLocation);
	
}
