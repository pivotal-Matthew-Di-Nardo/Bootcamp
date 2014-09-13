package com.pivotallabs.bootcamp.clients;

import org.apache.http.client.ClientProtocolException;
import org.json.*;

import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;
import java.util.concurrent.ThreadPoolExecutor;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.JsonReader;

public class JSONClient extends AsyncClient{
	
	public interface FetchJSONCallback {
		public void onFetchJSONSuccess(final String json);
		
		public void onFetchJSONFail(final Exception exception);
	}
	
	private static JSONClient instance;
	
	private JSONClient() {
		super.setup();
		
	}
	
	public static JSONClient getInstance() {
		
		if (null == instance) {
			instance = new JSONClient();
		}
		
		return instance;
	}
	
	public boolean fetchJSON(@NonNull final String url, @NonNull final JSONClient.FetchJSONCallback callback, boolean asynchronous) {
		
		//attempt to add a JSON fetch request to the thread pool.
		
		final Runnable task = new Runnable() {
				@Override
				public void run() {				
					try {
						String result = HttpRequest(url);
						callback.onFetchJSONSuccess(result);
					} catch (Exception e) {
						callback.onFetchJSONFail(e);
					}
				}
			};
		
		try {
			
			if (asynchronous) {
				super.executeTask(task);
			} else {
				task.run();
			}
			
		} catch (InterruptedException e) {
			//task failed to be dispatched.  return false to indicate this failure.
			return false;
		}
		
		return true;
	}
	
	public void parseJSON(String url, JSONClient.FetchJSONCallback callback) {
		JsonReader reader = new JsonReader(new StringReader(url));
	}
}
