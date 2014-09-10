package com.pivotallabs.bootcamp.remixAPI;
import java.net.URL;

import android.os.*;
import android.text.TextUtils;

public class RemixRequest {
    
    public enum ShowParam {
        All("all"),
        ID("id");
        
        
        private String name;
        private ShowParam(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public class Filter {
        
    }
    
    private static final String apiKey = "fd5a9pp3fs96z6nvw3bmmpt6";
    private static final String baseURL = "http://api.remix.bestbuy.com/v1/";
    
    private RemixAPI.Endpoint api = RemixAPI.Endpoint.PRODUCTS;
    private RemixAPI.Format format = RemixAPI.Format.JSON; //json default. 
    private String request;
    
    public RemixRequest() {
        
    }
    
    public RemixRequest(RemixAPI.Endpoint api, RemixAPI.Format format, String request) {
        this.api = api;
        this.format = format;
        this.request = request;
    }
    
    //get the currently targeted api
    public RemixAPI.Endpoint getAPI() {
        return this.api;
    }
    
    //set the target api.  If the api parameter is null, 
    //the target api will be set to the products api as a default.
    public void setAPI(RemixAPI.Endpoint api) {
        if(null == api) {
            this.api = RemixAPI.Endpoint.PRODUCTS;
        } else {
            this.api = api;
        }
    }
    
    //get the desired response format
    public RemixAPI.Format getFormat() {
        return this.format;
    }
    
    //set the desired response format.
    //By default it is set to JSON
    public void setFormat(RemixAPI.Format format) {
        if(null == format) {
            this.format = RemixAPI.Format.JSON;
        } else {
            this.format = format;
        }
    }
    
    public String toString() {
        
        if (this.request != null) {
            return this.request;
        }
        
        this.request += this.baseURL;
        //TODO: Add custom request info
        
        this.request += "?format=" + this.format.toString();
        this.request += "&APIKey=" + this.apiKey;
        return request;
    }
}
