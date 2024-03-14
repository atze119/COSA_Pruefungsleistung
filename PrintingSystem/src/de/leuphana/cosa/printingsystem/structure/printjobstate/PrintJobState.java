package de.leuphana.cosa.printingsystem.structure.printjobstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public abstract class PrintJobState {
	
	protected static final Logger LOGGER = LogManager.getLogger(PrintJobState.class);
	protected static Marker DEBUG_MODE_MARKER;
	
	protected PrintJob printJob;
	
	public PrintJobState(PrintJob printJob) {
		this.printJob = printJob;
		
		boolean isDebugMode = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
		if (isDebugMode) {
			DEBUG_MODE_MARKER = MarkerManager.getMarker("DEBUG_MODE");
		}	
	}
	
	public abstract PrintJobState changePrintJobState(PrintAction printAction);
}