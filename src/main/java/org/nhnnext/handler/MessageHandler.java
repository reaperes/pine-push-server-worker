package org.nhnnext.handler;

import org.json.JSONObject;

public class MessageHandler extends BaseHandler {
    @Override
    public void handle(JSONObject jsonObject) {
        System.out.println(jsonObject.getString("type"));
    }
}
