package de.leuphana.cosa.printingsystem.behaviour.service.command;

import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.UserAccount;

public interface PrintingCommandService {

	PrintReport printDocument(Printable printable, double price, UserAccount userAccount);

}