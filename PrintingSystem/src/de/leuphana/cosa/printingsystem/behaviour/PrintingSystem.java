package de.leuphana.cosa.printingsystem.behaviour;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.printingsystem.structure.ColorType;
import de.leuphana.cosa.printingsystem.structure.PrintJob;
import de.leuphana.cosa.printingsystem.structure.PrintReport;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.Printer;
import de.leuphana.cosa.printingsystem.structure.UserAccount;

@Component(immediate = true, service = PrintingCommandService.class)
public class PrintingSystem implements BundleActivator, PrintingCommandService {
	
	public static final String EVENT_TOPIC = "printingsystem/PrintReport";
	// Interface  Was?
	//Collection - 
	// Wie ?
	// Map  (Key - Value)
	// List (Element event. mehrfach)
	// Queue Reihenfolge 
	// Set (keine Elemente doppelt)
	
	// Wie
	// Konkrete Klassen
	
	// ArrayList
	// LinkedList
	
	
	// HashMap
	// TreeMap
	
	// Programmieren Sie immer !!!! gegen ein Inteface!!!! Was? und Wie?
	// Interface / was? = Realisierung // Wie?
	private Set<Printer> printers;
	
	@Reference
	private EventAdmin eventAdmin;

	//private printers;
	
	public PrintingSystem() {
		printers = new HashSet<Printer>();
		Printer printer = new Printer();
		printer.setColorType(ColorType.BLACK_WHITE);
		printers.add(printer);
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("PrintingService activated!");
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("PrintingSystem deactivated!");
	}

	@Override
	public PrintReport printDocument(Printable printable, double price, UserAccount userAccount) {
//		Check user account balance
		checkUserAccountBalance(userAccount);
		
		// TODO check if user account balance is positive
		
		PrintJob printJob = new PrintJob(printable);
		// Suche des richtigen Druckers (simuliert)
		Printer selectedPrinter = null;
		for (Printer printer : printers) {
			// if( ) {
			selectedPrinter = printer;
			// }
		}
		selectedPrinter.addPrintJob(printJob);
		
		PrintReport printReport = null;
		
		if(selectedPrinter.print()) {
			printReport = new PrintReport();
			printReport.setName(printable.getTitle());
			printReport.setContent(printable.getContent());
		}
		
		// TODO: Printing the ticket happens now here, not sure if its right!
		System.out.println(printable.getContent());
		
		Dictionary<String, PrintReport> eventProps = new Hashtable<String, PrintReport>();
		eventProps.put(PrintReport.class.getSimpleName(), printReport);
		Event event = new Event(EVENT_TOPIC, eventProps);
		eventAdmin.sendEvent(event);
		
		return printReport;
	}

	private Double checkUserAccountBalance(UserAccount userAccount) {
		return userAccount.getAccountBalance();
	}
}