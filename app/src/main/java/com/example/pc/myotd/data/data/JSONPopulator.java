package com.example.pc.myotd.data.data;

import org.json.JSONObject;

/**
 * Created by PIER on 05/01/2016.
 */
public interface JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}
