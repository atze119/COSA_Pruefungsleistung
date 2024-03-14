package de.leuphana.cosa.printingsystem.structure.printjobstate;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public class QueuedPrintJobState extends PrintJobState {
	
	public QueuedPrintJobState(PrintJob printJob) {
		super(printJob);
		LOGGER.debug(DEBUG_MODE_MARKER, "Print job with document name " + printJob.getPrintablet().getTitle() + " queued!");
	}

	@Override
	public PrintJobState changePrintJobState(PrintAction printAction) {
		switch (printAction) {
		case PRINT: {
			return new PrintedPrintJobState(printJob);
		}
		case PAUSE: {
			return new PausedPrintJobState(printJob);
		}
		case CANCEL: {
			return new CanceledPrintJobState(printJob);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + printAction);
		}
	}

}