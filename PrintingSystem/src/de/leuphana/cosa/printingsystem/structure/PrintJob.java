package de.leuphana.cosa.printingsystem.structure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import de.leuphana.cosa.printingsystem.structure.printjobstate.CreatedPrintJobState;
import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintJobState;

public class PrintJob {
	private Printable printable;
	private PrintJobState printJobState;
	
	private static Logger LOGGER;
	private static Marker DEBUG_MODE_MARKER;
	
	public PrintJob(Printable printable) {
		this.printable = printable;
		this.printJobState = new CreatedPrintJobState(this);
		LOGGER = LogManager.getLogger(this.getClass());
		boolean isDebugMode = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
		if (isDebugMode) {
			DEBUG_MODE_MARKER = MarkerManager.getMarker("DEBUG_MODE");
		}	
		LOGGER.debug(DEBUG_MODE_MARKER, "Print job with document name " + printable.getTitle() + " created!");
	}

	public Printable getPrintablet() {
		return printable;
	}

	public PrintJobState getPrintJobState() {
		return printJobState;
	}

	public void setPrintJobState(PrintJobState printJobState) {
		this.printJobState = printJobState;
	}
}