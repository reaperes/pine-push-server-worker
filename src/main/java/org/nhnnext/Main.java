package org.nhnnext;

import com.rabbitmq.client.QueueingConsumer;
import org.nhnnext.rabbitmq.RabbitMQ;

public class Main {
    private final static String UNIQUISH_HOSTNAME = "10.73.45.42";
    private final static int UNIQUISH_PORT = 8500;
    private final static String UNIQUISH_PUSH_URL = "http://" + UNIQUISH_HOSTNAME + ":" + UNIQUISH_PORT + "/push";

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        RabbitMQ rabbitMQ = RabbitMQ.getInstance();
        QueueingConsumer consumer = rabbitMQ.getConsumer();

        System.out.println("Start");
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("handler delivery");



//                String message = new String(delivery.getBody());
//                System.out.println(" [x] Received '" + message + "'");
//                JSONObject jsonMessage = new JSONObject(message);
//
//                HttpRequestWithBody request = Unirest.post(UNIQUISH_PUSH_URL);
//                request.header("Content-Type", "application/x-www-form-urlencoded");
//                MultipartBody body = request.field("service", "test")
//                        .field("subscriber", "namhoon");
//
//                Iterator iterator = jsonMessage.keys();
//                while (iterator.hasNext()) {
//                    String key = iterator.next().toString();
//                    Object o = jsonMessage.get(key);
//                    body = body.field(key, o);
//                }
//                HttpResponse<JsonNode> response = body.asJson();
//                System.out.println(response.getCode());
//                System.out.println(response.getBody());
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
        }
    }
}
