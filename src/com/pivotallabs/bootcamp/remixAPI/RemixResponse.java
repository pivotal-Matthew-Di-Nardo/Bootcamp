package com.pivotallabs.bootcamp.remixAPI;

import java.io.OutputStream;

public class RemixResponse {
    private RemixAPI.Format responseFormat;
    private String result;
    
    public RemixResponse(RemixAPI.Format format, OutputStream result) {
        this.responseFormat = format;
        this.result = result.toString();
    }
    
    public RemixResponse(RemixAPI.Format format, String result) {
        this.responseFormat = format;
        this.result = result;
    }
    
    public RemixAPI.Format getFormat() {
        return this.responseFormat;
    }
    public String getResult() {
        return this.result;
    }
}
