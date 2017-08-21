package com.javarticles.spring.jms;

import java.net.URISyntaxException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsMessage {
    public static void main(String[] args) throws URISyntaxException, Exception {
        JmsBroker broker = new JmsBroker();
        broker.start();
        try {
            //Producer
            Session session = broker.createSession();
            Destination queue = broker.createQueue();
            sendTextMessage(session, queue, "Hi");
            
            //Consumer
            session.createConsumer(queue);
            MessageConsumer consumer = session.createConsumer(queue);
            TextMessage msg = (TextMessage) consumer.receive();
            
            System.out.println(msg);
            System.out.println("Received: " + msg.getText());
            
            session.close();
        } finally {
            broker.cleanup();
        }
    }

    public static void sendTextMessage(Session session, Destination queue,
            String payload) throws JMSException {
        Message msg = session.createTextMessage(payload);
        MessageProducer producer = session.createProducer(queue);
        System.out.println("Sending text '" + payload + "'");
        producer.send(msg);
        session.close();
    }

}
