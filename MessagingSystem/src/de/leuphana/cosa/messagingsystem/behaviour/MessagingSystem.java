package de.leuphana.cosa.messagingsystem.behaviour;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.leuphana.cosa.messagingsystem.behaviour.service.command.MessagingSystemCommandService;
import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.Sendable;
import de.leuphana.cosa.messagingsystem.structure.message.Message;
import de.leuphana.cosa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.cosa.messagingsystem.structure.messagingprotocol.MessagingProtocol;

@Component(immediate = true, service = MessagingSystemCommandService.class)
public class MessagingSystem implements BundleActivator, MessagingSystemCommandService {
	private Logger logger;
	
	public static final String EVENT_TOPIC = "messagingsystem/DeliveryReport";
	
	@Reference(bind = "bindEventAdmin", unbind = "unbindEventAdmin")
	private EventAdmin eventAdmin;
	
	// OSGI event-delegation
	public void bindEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
	
	public void unbindEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}

	
	@Override
	public void start(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MessagingSystem activated");
	}
	
	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MessagingSystem deactivated");
		
	}
	
	@Override
	public DeliveryReport sendMessage(Sendable sendable) {
		logger = LogManager.getLogger(this.getClass());
		
		AbstractMessagingFactory abstractMessagingFactory = AbstractMessagingFactory.getFactory(sendable.getMessageType());

		Message message = abstractMessagingFactory.createMessage(sendable.getSender(), sendable.getReceiver(), sendable.getContent());

		MessagingProtocol messageProtocol = abstractMessagingFactory.createMessagingProtocol();
		messageProtocol.open();
		messageProtocol.transfer(message);
		messageProtocol.close();

		logger.info("Message: \n" + sendable.getContent() + " transported via " + sendable.getMessageType() + "\n");
		
		DeliveryReport deliveryReport =  new DeliveryReport();
		deliveryReport.setSender(sendable.getSender());
		deliveryReport.setReceiver(sendable.getReceiver());
		deliveryReport.setContent(sendable.getContent());
		deliveryReport.setMessageType(sendable.getMessageType().toString());
		deliveryReport.setDeliverySuccessful(true);

		Dictionary<String, DeliveryReport> eventProps = new Hashtable<String, DeliveryReport>();
		eventProps.put(DeliveryReport.class.getSimpleName(), deliveryReport);
		Event event = new Event(EVENT_TOPIC, eventProps);
		eventAdmin.sendEvent(event);
		
		return deliveryReport;
	}
}

