package de.leuphana.cosa.pricingsystem.behaviour;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.pricingsystem.structure.Chargeable;
import de.leuphana.cosa.pricingsystem.structure.Price;

@Component(immediate = true, service = PricingSystemCommandService.class)
public class PricingSystem implements BundleActivator, PricingSystemCommandService {
	
	public static final String EVENT_TOPIC = "pricesystem/Price";

	@Reference
	private EventAdmin eventAdmin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("PricingSystem is activated");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("PricingSystem is deactivated");
	}

	@Override
	public Price calculatePrice(Chargeable chargeable) {
		Price price = new Price();
		price.setPriceGroup(chargeable.getPriceGroup());
		switch (chargeable.getPriceGroup()) {
		case NORMAL_TARIFF:
			price.setAmount(chargeable.getRouteDistance() * 0.03);
			break;
		case CHEAPER_TARIFF:
			price.setAmount((chargeable.getRouteDistance() * 0.03f) / 100 * 0.75f);
			break;
		case BARGAIN_TARIFF:
			price.setAmount((chargeable.getRouteDistance() * 0.03f) / 100 * 0.5f);
			break;
		default: System.out.println("Unable to resolve pricegroup!");
		}
		
		// Eventing
		Dictionary<String, Price> eventProps = new Hashtable<String, Price>();
		eventProps.put(Price.class.getSimpleName(), price);
		Event event = new Event(EVENT_TOPIC, eventProps);
		eventAdmin.sendEvent(event);
		
		return price;
	}
	
	
}
