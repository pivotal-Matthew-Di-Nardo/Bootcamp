package com.pivotallabs.bootcamp.clients;

import org.apache.http.client.ClientProtocolException;
import org.json.*;

import com.pivotallabs.bootcamp.parsers.JSONParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.Exception;
import java.util.concurrent.ThreadPoolExecutor;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.JsonReader;

public class JSONClient extends AsyncClient {

    public interface FetchJSONCallback {
        public void onFetchJSONSuccess(String json);
        public void onFetchJSONFail(Exception exception);
    }

    public interface ParseJSONCallback {
        public void onParseJSONSuccess (JSONObject json);
        public void onParseJSONFail (Exception exception);
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

    public boolean fetchJSON(@NonNull final String url,
            @NonNull final JSONClient.FetchJSONCallback callback,
            boolean asynchronous) {

        // attempt to add a JSON fetch request to the thread pool.

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    ByteArrayOutputStream os = (ByteArrayOutputStream) HttpRequest(url);
                    String result = os.toString();
                    os.close();
                    callback.onFetchJSONSuccess(result);
                } catch (Exception e) {
                    callback.onFetchJSONFail(e);
                }
            }
        };

        try {

            if (asynchronous) {
                super.executeTaskAsync(task);
            } else {
                task.run();
            }

        } catch (InterruptedException e) {
            // task failed to be dispatched. return false to indicate this
            // failure.
            return false;
        }

        return true;
    }

    public boolean parseJSON(final InputStream jsonStream, final JSONParser parser, final JSONClient.ParseJSONCallback callback, boolean asynchronous) {

	    final Runnable task = new Runnable() {
            @Override
            public void run() {             
                try {
                    final JSONObject jsonObject = parser.parse(jsonStream);
                    
                    callback.onParseJSONSuccess(jsonObject);
                } catch (Exception e) {
                    callback.onParseJSONFail(e);
                }
            }
        };
    
        try {
        
            if (asynchronous) {
                super.executeTaskAsync(task);
            } else {
                task.run();
            }
        
        } catch (InterruptedException e) {
            //task failed to be dispatched.  return false to indicate this failure.
            return false;
        }
	    
        return true;
	}
}
