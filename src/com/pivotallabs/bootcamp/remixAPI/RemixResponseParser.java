package com.pivotallabs.bootcamp.remixAPI;

import org.json.JSONObject;

import com.pivotallabs.bootcamp.remixAPI.RemixAPI;

public class RemixResponseParser {

    
    public static JSONObject parse(RemixResponse response) {
        return RemixResponseParser.parse(response.getFormat(), response.getResult());
    }

    public static JSONObject parse(RemixAPI.Format responseFormat, String response) {
        
        JSONObject o = new JSONObject();
        return null;
    }
    
    private static class JSONParser {
        
    }
    
    private static class XMLParser {
        
    }
}
 