package org.nhnnext;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONObject;

import java.util.Iterator;

public class Main {
    private final static String UNIQUISH_HOSTNAME = "10.73.45.42";
    private final static int UNIQUISH_PORT = 8500;
    private final static String UNIQUISH_PUSH_URL = "http://" + UNIQUISH_HOSTNAME + ":" + UNIQUISH_PORT + "/push";

    private final static String QUEUE_NAME = "push_test";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
//                System.out.println(" [x] Received '" + message + "'");
                JSONObject jsonMessage = new JSONObject(message);

                HttpRequestWithBody request = Unirest.post(UNIQUISH_PUSH_URL);
                request.header("Content-Type", "application/x-www-form-urlencoded");
                MultipartBody body = request.field("service", "test")
                        .field("subscriber", "namhoon");

                Iterator iterator = jsonMessage.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next().toString();
                    Object o = jsonMessage.get(key);
                    body = body.field(key, o);
                }
                HttpResponse<JsonNode> response = body.asJson();
                System.out.println(response.getCode());
                System.out.println(response.getBody());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
