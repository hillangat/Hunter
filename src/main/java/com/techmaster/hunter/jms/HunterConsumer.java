package com.techmaster.hunter.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class HunterConsumer {
	
	public static void main(String[] args) {
		
try {
			
			
			ActiveMQConnectionFactory msqsConFactory = new ActiveMQConnectionFactory("tcp://localhost:61616"); // this is the path you get when you start your activeMQS.
			Connection  connection  = msqsConFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("HELLOW WORLD.TEST_QUEUE");
			
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			
			if(message instanceof TextMessage){
				String text = ((TextMessage) message).getText();
				System.out.println("Received >> " + text); 
			}else{
				System.out.println("Received >> " + message);
			}
			
			consumer.close();
			session.close();
			connection.close(); 
			 
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
