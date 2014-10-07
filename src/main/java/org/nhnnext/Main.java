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
            if (jsonDelivery.has("type") && handlerMap.containsKey(jsonDelivery.getString("type"))) {
                BaseHandler handler = handlerMap.get(jsonDelivery.getString("type"));
                handler.handle(jsonDelivery);
            } else {
                System.out.println("Error: " + jsonDelivery.toString());
            }
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);





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
