package de.leuphana.cosa.printingsystem.structure;

import java.util.LinkedList;
import java.util.Queue;

import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintAction;
import de.leuphana.cosa.printingsystem.structure.printjobstate.PrintJobState;

public class Printer {
	private Queue<PrintJob> printJobQueue;
	private ColorType colorType;
	
	public Printer() {
	//	printJobQueue = new PriorityQueue<PrintJob>(100);
		printJobQueue = new LinkedList<PrintJob>();
	}

	public void setColorType(ColorType colorType) {
		this.colorType = colorType;
	}

	public void addPrintJob(PrintJob printJob) {
		printJobQueue.add(printJob);
		PrintJobState printJobState = printJob.getPrintJobState();
		printJob.setPrintJobState(printJobState.changePrintJobState(PrintAction.QUEUE));
	}

	public boolean print() {
		PrintJob printJob = printJobQueue.remove();
		PrintJobState printJobState = printJob.getPrintJobState();
		printJobState.changePrintJobState(PrintAction.PRINT);

		return true;
	}

}
