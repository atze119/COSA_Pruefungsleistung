package de.leuphana.cosa.pricingsystem.behaviour.service.command;

import de.leuphana.cosa.pricingsystem.structure.Chargeable;
import de.leuphana.cosa.pricingsystem.structure.Price;

public interface PricingSystemCommandService {
	Price calculatePrice(Chargeable chargeable);
}
