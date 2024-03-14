package de.leuphana.cosa.routesystem.behaviour;

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

import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;
import de.leuphana.cosa.routesystem.structure.LocationName;
import de.leuphana.cosa.routesystem.structure.Routable;
import de.leuphana.cosa.routesystem.structure.Route;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RouteSystemTest {
	
	private static RouteSystemCommandService routeService;
	private static Routable routable;
	
	@BeforeAll
	static void setup() {
		routable = new Routable() {
			
			@Override
			public LocationName getStartLocation() {
				return LocationName.HAMBURG;
			}
			
			@Override
			public LocationName getEndLocation() {
				return LocationName.LUNENBURG;
			}
		};
	}
	
	@AfterAll
	static void tearDown() {
		routable = null;
		routeService = null;
	}
	
	@Test
	@Order(1)
	void canRouteServiceBeAccessed() {
		routeService = getService(RouteSystemCommandService.class);
		Assertions.assertNotNull(routeService);
	}

	@Test
	@Order(2)
	void canRouteBeCreated() {
		Route route = routeService.createRoute(routable);
		System.out.println("Created route with start " + route.getStartLocation().getName() + " and destination " + route.getEndLocation().getName());
		Assertions.assertNotNull(route);
	}
	
	// helper-function to retrieve Service!
	static <T> T getService(Class<T> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(RouteSystem.class);
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
