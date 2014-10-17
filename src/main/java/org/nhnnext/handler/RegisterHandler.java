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
 * Register subscriber to Uniqush-push
 */
public class RegisterHandler extends BaseHandler {
    private static final Logger logger = LogManager.getLogger(RegisterHandler.class.getName());

    private static final String UNIQUSH_BASE_URL = "http://" + Config.UNIQUSH_HOST + ":" + Config.UNIQUSH_PORT + "/subscribe";

    @Override
    public void handle(JSONObject jsonObject) throws HandlerException {
        String subscriber = jsonObject.getString("device_name");
        String deviceType = jsonObject.getString("device_type");
        String deviceId = jsonObject.getString("device_id");

        MultipartBody body;
        switch (deviceType) {
            case "android":
                body = generateAndroidBody(subscriber, deviceType, deviceId);
                break;

            case "ios":
                body = generateIosBody(subscriber, deviceType, deviceId);
                break;

            default:
                logger.error("Register handler error : " + jsonObject.toString());
                return ;
        }
        logger.info("Send to uniqush server : " + jsonObject.toString());

        try {
            HttpResponse<JsonNode> response = body.asJson();
            if (response.getCode() != 200)
                throw new HandlerException("Uniqush server return code is not 200 : " + response.getCode() + " " + response.getBody());
        } catch (UnirestException e) {
            throw new HandlerException("Unirest Exception occurred : " + e.getMessage());
        }
    }

    private MultipartBody generateAndroidBody(String subscriber, String deviceType, String deviceId) {
        HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
        httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
        return httpRequestWithBody
                .field("service", Config.UNIQUSH_SERVICE)
                .field("subscriber", subscriber)
                .field("pushservicetype", deviceType)
                .field("regid", deviceId);
    }

    private MultipartBody generateIosBody(String subscriber, String deviceType, String deviceId) {
        HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
        httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
        return httpRequestWithBody
                .field("service", Config.UNIQUSH_SERVICE)
                .field("subscriber", subscriber)
                .field("pushservicetype", deviceType)
                .field("devtoken", deviceId);
    }
}