package de.leuphana.cosa.printingsystem.structure.printjobstate;

import de.leuphana.cosa.printingsystem.structure.PrintJob;

public class PrintedPrintJobState extends PrintJobState {
	
	public PrintedPrintJobState(PrintJob printJob) {
		super(printJob);
		System.out.println("Print job with document name " + printJob.getPrintablet().getTitle() + " printed!");
	}

	@Override
	public PrintJobState changePrintJobState(PrintAction printAction) {
		// TODO ...
		return null;
	}
}