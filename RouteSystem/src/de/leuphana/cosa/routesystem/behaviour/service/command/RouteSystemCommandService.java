package de.leuphana.cosa.routesystem.behaviour.service.command;

import de.leuphana.cosa.routesystem.structure.Routable;
import de.leuphana.cosa.routesystem.structure.Route;

public interface RouteSystemCommandService {

	Route createRoute(Routable routable);
	
}
