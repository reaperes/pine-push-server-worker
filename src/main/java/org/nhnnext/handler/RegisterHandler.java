package org.nhnnext.handler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.json.JSONObject;
import org.nhnnext.Config;

public class RegisterHandler extends BaseHandler {
    private static Config config = Config.getInstance();

    private static final String UNIQUSH_BASE_URL = "http://" + config.getString("uniqush.host") + ":" + config.getInt("uniqush.port") + "/subscribe";

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            HttpRequestWithBody httpRequestWithBody = Unirest.post(UNIQUSH_BASE_URL);
            httpRequestWithBody.header("Content-Type", "application/x-www-form-urlencoded");
            MultipartBody body = httpRequestWithBody.field("service", config.getString("uniqush.service"))
                    .field("subscriber", jsonObject.getString("device_name"))
                    .field("pushservicetype", jsonObject.getString("device_type"))
                    .field("regid", jsonObject.getString("device_id"));
            HttpResponse<JsonNode> response = body.asJson();
            if (response.getCode() != 200)
                System.out.println("Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}