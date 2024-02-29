package de.leuphana.cosa.pricingsystem.behaviour.service.command;

import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;

public interface PricingSystemCommandService {
	Price calculatePrice(double routeDistance, PriceGroup priceGroup);
}
