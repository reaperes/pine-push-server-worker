package org.nhnnext.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;

public class RabbitMQ {
    private static final String CONFIG_FILENAME = "application.conf";

    private static RabbitMQ instance = new RabbitMQ();

    private QueueingConsumer consumer;

    private RabbitMQ() {
        Config config = ConfigFactory.load(CONFIG_FILENAME);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.getString("rabbitmq.host"));
        factory.setPort(config.getInt("rabbitmq.port"));

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(config.getString("rabbitmq.queue_name"), true, false, false, null);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(config.getString("rabbitmq.queue_name"), false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RabbitMQ getInstance() {
        return instance;
    }

    public QueueingConsumer getConsumer() {
        return consumer;
    }
}
