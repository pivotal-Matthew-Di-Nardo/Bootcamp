package com.pivotallabs.bootcamp.clients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.support.annotation.NonNull;

public abstract class AsyncClient {

    private ThreadPoolExecutor threadPoolExecutor;
    private BlockingQueue<Runnable> threadPoolTaskQueue;

    protected void setup() {
        this.threadPoolTaskQueue = new SynchronousQueue<Runnable>();
        this.threadPoolExecutor = new ThreadPoolExecutor(1, 5, 5000, TimeUnit.MILLISECONDS, threadPoolTaskQueue);
    }

    protected void executeTaskAsync(@NonNull Runnable r)
            throws InterruptedException {
        this.threadPoolExecutor.execute(r);
    }
    
    protected HttpResponse httpGet(String request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response;
        
        response = httpClient.execute(new HttpGet(request));
        
        return response;
    }
    
    protected OutputStream HttpRequest(String request) throws IOException, ClientProtocolException {
        HttpResponse response;
        String responseString = null;
        
        response = this.httpGet(request);
        
        StatusLine statusLine = response.getStatusLine();
        
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            OutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            return out;
            
        } else {
            // Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }

    }

}
