package com.javarticles.spring.jms;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;

public class JmsBroker {
    private final String EMBEDDED_BROKER = "tcp://localhost:61616";
    private final String QUEUE = "someQueue";
    
    private Connection connection;
    ConnectionFactory connectionFactory;
    
    public void start() throws URISyntaxException, Exception {
        BrokerService broker = BrokerFactory.createBroker(new URI(EMBEDDED_BROKER));
        broker.start();
    }
    
    public Session createSession() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(EMBEDDED_BROKER);
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        return session;
    }
    
    public Destination createQueue() throws JMSException {
        return new ActiveMQQueue(QUEUE);
    }

    public void cleanup() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
}
