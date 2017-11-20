package com.techmaster.hunter.jms;

public class CodeSnippet {
	
	public void connectionSample(){
		
/*
 
// try JSMPP 

This is a typical code.


InitialContext jndiContext = new InitialContext();
ConnectionFactory factory = jndiContext.lookup(connectionFactoryName);
Connection connection = factory.createConnection();

 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
 Destination d1 = (Queue)jndiContext.lookup("/jms/myQueue"); // for point to point
 Destination d2 = (Topic)jndiContext.lookup("/jms/myTopic"); // for publish-subscribe.
 
 // For producer
 MessageProducer producer = session.createProducer(dest1);
 Message pMessage = session.createMessage();
 pMessage.setText("Ruto will be in Bomet tomorrow at 2pm. Please attend.");
 producer.send(pMessage); 
 
 connection.close();
 
 // For consumer
 MessageConsumer consumer = session.createConsumer(dest1);
 connection.start();
 Message cMessage = consumer.receive();
 
 
 for spring template : 
 
 
 
 

*/
		
		
	}

}
