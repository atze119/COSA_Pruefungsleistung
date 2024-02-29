package de.leuphana.cosa.messagingsystem.behaviour;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.messagingsystem.behaviour.service.command.MessagingSystemCommandService;
import de.leuphana.cosa.messagingsystem.structure.DeliveryReport;
import de.leuphana.cosa.messagingsystem.structure.MessageType;
import de.leuphana.cosa.messagingsystem.structure.Sendable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessagingServiceTest {

	private static MessagingSystemCommandService messagingService;
	private static Sendable sendable;
	
	@BeforeAll
	static void setUp() throws Exception {
		sendable = new Sendable() {
		
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
				return "Test content";
			}
		};
	}
	
	@AfterAll
	static void tearDown() throws Exception {
		messagingService = null;
		sendable = null;
	}
	
	@Test
	@Order(1)
	void canMessagingServiceBeAccessed() {
		messagingService = getService(MessagingSystemCommandService.class);
		Assertions.assertNotNull(messagingService);
	}
	
	@Test
	@Order(2)
	void canMessageBeSend() {
		DeliveryReport report = messagingService.sendMessage(sendable);
		System.out.println("DeliveryReport of message with content: " + report.getContent());
		Assertions.assertNotNull(report);
	}
	
	
	// helper-function
	static <T> T getService(Class<T> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(MessagingSystem.class);
        if (bundle != null) {
            ServiceTracker<T, T> st =
                new ServiceTracker<T, T>(
                    bundle.getBundleContext(), clazz, null);
            st.open();
            if (st != null) {
                try {
                    // give the runtime some time to startup
                    return st.waitForService(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
