package com.javarticles.spring.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JmsMessageConsumer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        Destination destination = new ActiveMQQueue("someQueue");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        try {
            MessageConsumer consumer = session.createConsumer(destination);
            connection.start();
            TextMessage msg = (TextMessage) consumer.receive();
            System.out.println(msg);
            System.out.println("Received: " + msg.getText());
            session.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

}
