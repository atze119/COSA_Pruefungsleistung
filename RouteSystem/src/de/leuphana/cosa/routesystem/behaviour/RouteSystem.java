package de.leuphana.cosa.routesystem.behaviour;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;
import de.leuphana.cosa.routesystem.structure.Location;
import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.routesystem.structure.Routable;
import de.leuphana.cosa.routesystem.structure.Route;

@Component(service = RouteSystemCommandService.class)
public class RouteSystem implements BundleActivator, RouteSystemCommandService {

	public static final String EVENT_TOPIC = "routesystem/Route";
	
	@Reference
	private EventAdmin eventAdmin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("RouteSystem activated!");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("RouteSystem deactivated!");
	}

	@Override
	public Route createRoute(Routable routable) {
		Location startLocation1 = pickLocation(routable.getStartLocation());
		Location endLocation1 = pickLocation(routable.getEndLocation());
		Route route = new Route(startLocation1, endLocation1);
		
		// Eventing
		Dictionary<String, Route> eventProps = new Hashtable<String, Route>();
		eventProps.put(Route.class.getSimpleName(), route);
		Event event = new Event(EVENT_TOPIC, eventProps);
		eventAdmin.sendEvent(event);
		
		return route;
	}
	
	private Location pickLocation(LocationName locationName) {
	    
	    Location location = null;
	    while(location == null) {
			switch (locationName) {
				case LUNENBURG:
					location = new Location("Lunenburg", 53.250900, 10.414090);
					break;
				case HAMBURG:
					location = new Location("Hamburg", 53.553406, 9.992196);
					break;
				case MUNICH: 
					location = new Location("Munich", 48.137428, 11.575490);
					break;
				case BREMEN:
					location = new Location("Bremen", 53.075160, 8.807770);
					break;
				case DUSSELDORF:
					location = new Location("Dusseldorf", 51.224941, 6.775652);
					break;
				case KIEL:
					location = new Location("Kiel", 54.321675, 10.137186);
					break;
				default:
					System.out.println("The location couldn't be resolved!!!");
			}
	    }
		return location;
	}
	
}
