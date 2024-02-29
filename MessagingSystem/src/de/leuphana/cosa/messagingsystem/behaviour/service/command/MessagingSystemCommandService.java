package de.leuphana.cosa.messagingsystem.behaviour.service.command;

import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;

public interface MessagingSystemCommandService {

	 DeliveryReport sendMessage(Sendable sendable);

}