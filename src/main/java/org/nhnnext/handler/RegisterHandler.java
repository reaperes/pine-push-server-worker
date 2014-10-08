package org.nhnnext.handler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.nhnnext.Config;

public class RegisterHandler extends BaseHandler {
    private static final Logger logger = LogManager.getLogger(RegisterHandler.class.getName());

    private static Config config = Config.getInstance();

    private static final String UNIQUSH_BASE_URL = "http://" + config.getString("uniqush.host") + ":" + config.getInt("uniqush.port") + "/subscribe";

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            String subscriber = jsonObject.getString("device_name");
            String deviceType = jsonObject.getString("device_type");
            String deviceId = jsonObject.getString("device_id");

            if (deviceType.equals("android")) {
                HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
                httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
                MultipartBody body = httpRequestWithBody
                        .field("service", config.getString("uniqush.service"))
                        .field("subscriber", jsonObject.getString("device_name"))
                        .field("pushservicetype", jsonObject.getString("device_type"))
                        .field("regid", jsonObject.getString("device_id"));
                logger.info("Send to uniqush server : " + jsonObject.toString());
                HttpResponse<JsonNode> response = body.asJson();
                if (response.getCode() != 200)
                    logger.error("Uniqush server return code is not 200 : " + response.getCode() + " " + response.getBody());
            }
            else if (deviceType.equals("ios")) {
                HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
                httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
                MultipartBody body = httpRequestWithBody
                        .field("service", config.getString("uniqush.service"))
                        .field("subscriber", jsonObject.getString("device_name"))
                        .field("pushservicetype", jsonObject.getString("device_type"))
                        .field("devtoken", jsonObject.getString("device_id"));
                logger.info("Send to uniqush server : " + jsonObject.toString());
                HttpResponse<JsonNode> response = body.asJson();
                if (response.getCode() != 200)
                    logger.error("Uniqush server return code is not 200 : " + response.getCode() + " " + response.getBody());
            }
            else {
                logger.error("Register handler error : " + jsonObject.toString());
            }
        } catch (Exception e) {
            logger.error("Exception occured");
        }
    }
}