package org.nhnnext.handler;

import org.json.JSONObject;

public class RegisterHandler extends BaseHandler {
    @Override
    public void handle(JSONObject jsonObject) {
        System.out.println(jsonObject.getString("type"));
    }
}