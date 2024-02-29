package de.leuphana.cosa.componentservicebus.structure.connector;

import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.printingsystem.structure.PrintReport;

public class PrintReportToSendableAdapter {

	public Sendable convert(PrintReport printReport) {
		
		Sendable sendable = new Sendable() {
			
			@Override
			public String getSender() {
				return "TicketAutomaton1@train.de";
			}

			@Override
			public String getReceiver() {
				return "TrainCentral@train.de";
			}

			@Override
			public MessageType getMessageType() {
				return MessageType.EMAIL;
			}

			@Override
			public String getContent() {
				return printReport.getContent();
			}
		};
		
		return sendable;
	}

}