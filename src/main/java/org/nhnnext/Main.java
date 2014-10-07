package org.nhnnext;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONObject;
import org.nhnnext.handler.BaseHandler;
import org.nhnnext.handler.MessageHandler;
import org.nhnnext.handler.RegisterHandler;
import org.nhnnext.rabbitmq.RabbitMQ;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        RabbitMQ rabbitMQ = RabbitMQ.getInstance();
        Channel channel = rabbitMQ.getChannel();
        QueueingConsumer consumer = rabbitMQ.getConsumer();

        Map<String, BaseHandler> handlerMap = new HashMap<String, BaseHandler>();
        handlerMap.put("/push/register", new RegisterHandler());
        handlerMap.put("/push/message", new MessageHandler());

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            JSONObject jsonDelivery = new JSONObject(new String(delivery.getBody()));
            try {
                if (jsonDelivery.has("type") && jsonDelivery.has("type") && handlerMap.containsKey(jsonDelivery.getString("type")) && jsonDelivery.has("data")) {
                    BaseHandler handler = handlerMap.get(jsonDelivery.getString("type"));
                    handler.handle(jsonDelivery.getJSONObject("data"));
                }
                else {
                    System.out.println("Main Error: " + jsonDelivery.toString());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
