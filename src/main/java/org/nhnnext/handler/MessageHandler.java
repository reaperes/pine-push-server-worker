package org.nhnnext.handler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.nhnnext.Config;

/**
 * Push message to Uniqush-push
 */
public class MessageHandler extends BaseHandler {
    private static final Logger logger = LogManager.getLogger(MessageHandler.class.getName());

    private static final String UNIQUSH_BASE_URL = "http://" + Config.UNIQUSH_HOST + ":" + Config.UNIQUSH_PORT + "/push";

    @Override
    public void handle(JSONObject jsonObject) throws HandlerException {
        try {
            HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
            httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
            MultipartBody body = httpRequestWithBody.field("service", Config.UNIQUSH_SERVICE)
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
            logger.info("Send to uniqush : " + jsonObject.toString());
            if (response.getCode() != 200)
                throw new HandlerException("Uniqush server return code is not 200 : " + response.getCode() + " " + response.getBody());
        } catch (UnirestException e) {
            throw new HandlerException("UnirestException occurred : " + e.getMessage());
        }
    }
}