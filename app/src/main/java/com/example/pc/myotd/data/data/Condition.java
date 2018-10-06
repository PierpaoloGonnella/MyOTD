package com.example.pc.myotd.data.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PIER on 05/01/2016.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("code", code);
            data.put("temp", temperature);
            data.put("text", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}

