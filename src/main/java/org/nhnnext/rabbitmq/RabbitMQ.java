package org.nhnnext.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nhnnext.Config;

import java.io.IOException;

public class RabbitMQ {
    private static final Logger logger = LogManager.getLogger(RabbitMQ.class.getName());

    private static RabbitMQ instance = new RabbitMQ();

    private Channel channel;
    private QueueingConsumer consumer;

    private RabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Config.RABBITMQ_HOST);
        factory.setPort(Config.RABBITMQ_PORT);

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(Config.RABBITMQ_QUEUE, true, false, false, null);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(Config.RABBITMQ_QUEUE, false, consumer);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("System is going to shutdown.");
            System.exit(1);
        }
    }

    public static RabbitMQ getInstance() {
        return instance;
    }

    public QueueingConsumer getConsumer() {
        return consumer;
    }

    public Channel getChannel() {
        return channel;
    }
}
