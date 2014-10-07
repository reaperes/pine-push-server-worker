package org.nhnnext.handler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.json.JSONObject;
import org.nhnnext.Config;

public class MessageHandler extends BaseHandler {
    private static Config config = Config.getInstance();

    private static final String UNIQUSH_BASE_URL = "http://" + config.getString("uniqush.host") + ":" + config.getInt("uniqush.port") + "/push";

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
            httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
            MultipartBody body = httpRequestWithBody.field("service", config.getString("uniqush.service"))
                    .field("subscriber", jsonObject.get("device_name"))
                    .field("push_type", jsonObject.get("push_type"))
                    .field("badge", jsonObject.get("push_badge"))
                    .field("msg", jsonObject.get("push_message"))
                    .field("event_date", jsonObject.get("event_date"))
                    .field("image_url", jsonObject.get("image_url"))
                    .field("summary", jsonObject.get("summary"))
                    .field("thread_id", jsonObject.get("thread_id"))
                    .field("comment_id", jsonObject.get("comment_id"));
            HttpResponse<JsonNode> response = body.asJson();
            if (response.getCode() != 200)
                System.out.println("Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}