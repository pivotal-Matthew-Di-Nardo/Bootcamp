package com.pivotallabs.bootcamp.clients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import android.os.Process;
public abstract class AsyncClient {
	
	private ThreadPoolExecutor threadPoolExecutor;
	private BlockingQueue<Runnable> threadPoolTaskQueue;
	
	protected void setup() {
		this.threadPoolTaskQueue = new SynchronousQueue<Runnable>();
		threadPoolExecutor = new ThreadPoolExecutor(1, 5, 5000, TimeUnit.MILLISECONDS, threadPoolTaskQueue);
	}
	
	protected void executeTask(@NonNull Runnable r) throws InterruptedException {
		this.threadPoolExecutor.execute(r);
	}
	
	protected String HttpRequest(String request) throws IOException, ClientProtocolException{
		HttpClient httpclient = new DefaultHttpClient();
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
