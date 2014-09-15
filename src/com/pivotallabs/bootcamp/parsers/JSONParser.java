package com.pivotallabs.bootcamp.parsers;

import java.io.InputStream;

import org.json.JSONObject;

public interface JSONParser {
    public JSONObject parse(InputStream in);
}
