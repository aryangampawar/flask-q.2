package com.assignment;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSubscriber {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "NewsTopic";

    public void subscribe(String subscriberName) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(subscriberName);
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                try {
                    String text = ((TextMessage) message).getText();
                    System.out.println(subscriberName + " received: " + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // Keep the subscriber running
        System.out.println(subscriberName + " is subscribed and listening...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                consumer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }));
    }

    public static void main(String[] args) throws JMSException {
        MessageSubscriber subscriber = new MessageSubscriber();
        subscriber.subscribe("Subscriber-" + args[0]);
    }
}