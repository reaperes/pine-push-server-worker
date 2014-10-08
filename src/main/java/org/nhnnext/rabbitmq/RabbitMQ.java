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
    private static final Logger logger = LogManager.getLogger(Config.class.getName());

    private static RabbitMQ instance = new RabbitMQ();

    private Channel channel;
    private QueueingConsumer consumer;

    private RabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        Config config = Config.getInstance();
        factory.setHost(config.getString("rabbitmq.host"));
        factory.setPort(config.getInt("rabbitmq.port"));

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(config.getString("rabbitmq.queue_name"), true, false, false, null);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(config.getString("rabbitmq.queue_name"), false, consumer);
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
