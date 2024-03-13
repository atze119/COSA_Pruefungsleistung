package de.leuphana.cosa.pricingsystem.behaviour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PricingSystemTest {

	private static PricingSystemCommandService pricingService;
	private static double distance = 662.24;
	
	@Test
	@Order(1)
	void canPricingServiceBeAccessed() {
		pricingService = getService(PricingSystemCommandService.class);
		Assertions.assertNotNull(pricingService);
	}
	
	@Test
	@Order(2)
	void canPriceBeCalculated() {
		Price price = pricingService.calculatePrice(distance, PriceGroup.BARGAIN_TARIFF);
		System.out.println("Calculated price " + price.getAmount() + " for pricegroup " + price.getPriceGroup() + " and distance " + distance);
		Assertions.assertNotNull(price);
	}

	
	// helper-function to retrieve Service!
			static <T> T getService(Class<T> clazz) {
		        Bundle bundle = FrameworkUtil.getBundle(PricingSystem.class);
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
