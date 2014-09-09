package com.pivotallabs.bootcamp.remixAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

public class RemixHttpTask extends AsyncTask<RemixRequest, Integer, String> {

    public interface Callback {
        public void onRemixHttpTaskComplete (String result);
    }
    
    private RemixHttpTask.Callback callback = null;
    private HttpClient httpclient = null;
    
    public RemixHttpTask(RemixHttpTask.Callback callback) {
        
        this.callback = callback;
        this.httpclient = new DefaultHttpClient();
    }
    
    public RemixHttpTask.Callback getCallback() {
        return this.callback;
    }
    public void setCallback(RemixHttpTask.Callback callback) {
        this.callback = callback;
    }
    
    @Override
    protected String doInBackground(RemixRequest... requests) {
        
        try {
            return HttpRequest(requests[0].toString());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //some error occured
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (null != this.callback) {
            this.callback.onRemixHttpTaskComplete(result);
        }
    };
    
    private String HttpRequest(String request) throws IOException, ClientProtocolException {
        HttpResponse response;
        String responseString = null;
        
            response = httpclient.execute(new HttpGet(request));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        
        
        return responseString;
    }
}
