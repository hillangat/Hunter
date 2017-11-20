package com.techmaster.hunter.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class HunterProducer {
	
	public static void main(String[] args) {
		
		try {
			
			
			ActiveMQConnectionFactory msqsConFactory = new ActiveMQConnectionFactory("tcp://localhost:61616"); // this is the path you get when you start your activeMQS.
			Connection  connection  = msqsConFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("HELLOW WORLD.TEST_QUEUE");
			
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); 
			
			String currentThreadName = Thread.currentThread().getName();
			String text = "Hellow world! From : " + currentThreadName;
			TextMessage textMessage = session.createTextMessage(text);
			
			System.out.println("Sent message >> " + textMessage.hashCode() + " : " + currentThreadName);
			
			producer.send(textMessage);
			
			session.close();
			connection.close(); 
			 
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
