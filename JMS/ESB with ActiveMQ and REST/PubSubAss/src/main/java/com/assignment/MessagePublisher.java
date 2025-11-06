package com.assignment;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessagePublisher {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "NewsTopic";

    public void publishMessage(String message) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);

        System.out.println("Published message: " + message);

        producer.close();
        session.close();
        connection.close();
    }

    public static void main(String[] args) throws JMSException {
        MessagePublisher publisher = new MessagePublisher();
        publisher.publishMessage("Breaking News: New technology advancements announced!");
    }
}