package org.nhnnext;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.nhnnext.handler.BaseHandler;
import org.nhnnext.handler.HandlerException;
import org.nhnnext.handler.MessageHandler;
import org.nhnnext.handler.RegisterHandler;
import org.nhnnext.rabbitmq.RabbitMQ;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    /**
     * main function
     */
    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        RabbitMQ rabbitMQ = RabbitMQ.getInstance();
        Channel channel = rabbitMQ.getChannel();
        QueueingConsumer consumer = rabbitMQ.getConsumer();

        logger.info("Register handlers");
        Map<String, BaseHandler> handlerMap = new HashMap<>();
        handlerMap.put("/push/register", new RegisterHandler());
        handlerMap.put("/push/message", new MessageHandler());

        logger.info("Service start");
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                JSONObject jsonDelivery = new JSONObject(new String(delivery.getBody()));
                logger.info("Deliver message : " + jsonDelivery.toString());

                if (jsonDelivery.has("type") && jsonDelivery.has("type") && handlerMap.containsKey(jsonDelivery.getString("type")) && jsonDelivery.has("data")) {
                    String messageType = jsonDelivery.getString("type");
                    JSONObject messageJson = jsonDelivery.getJSONObject("data");

                    BaseHandler handler = handlerMap.get(messageType);
                    handler.handle(messageJson);
                } else {
                    logger.error(jsonDelivery.toString());
                }
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
            catch (HandlerException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
