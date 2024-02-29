package de.leuphana.cosa.messagingsystem.structure.messagingprotocol;

import de.leuphana.cosa.messagingsystem.structure.message.Message;

public interface MessagingProtocol {
	boolean open();
	boolean transfer(Message message);
	boolean close();
}
