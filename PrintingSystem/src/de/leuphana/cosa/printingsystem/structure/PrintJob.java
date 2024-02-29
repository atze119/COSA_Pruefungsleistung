package de.leuphana.cosa.printingsystem.structure;

import de.leuphana.cosa.printingsystem.structure.printjobstate.CreatedPrintJobState;
import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintJobState;

public class PrintJob {
	private Printable printable;
	private PrintJobState printJobState;
	
	public PrintJob(Printable printable) {
		this.printable = printable;
		this.printJobState = new CreatedPrintJobState(this);
		System.out.println("Print job with document name " + printable.getTitle() + " created!");
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