package com.pivotallabs.bootcamp.clients;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.params.HttpClientParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;

public class ImageClient extends AsyncClient {

    public interface Callback {
        public void onFetchImageSuccess(Bitmap image);

        public void onFetchImageFailure(Exception error);
    }

    private static ImageClient instance;
    private final Map<String, Bitmap> cache;
    private final Object cacheLock = new Object(); 

    private ImageClient() {
        super.setup();
        this.cache = new ConcurrentHashMap<String, Bitmap>();
    }

    public static ImageClient getInstance() {
        if (null == instance) {
            instance = new ImageClient();
        }

        return instance;
    }

    public boolean fetchBitmap(final String url, final ImageClient.Callback callback, boolean forceNetworkFetch, boolean asyncOnNetworkFetch) {
        
        synchronized(this.cacheLock) {
            if (!forceNetworkFetch && this.cache.containsKey(url)) {
                callback.onFetchImageSuccess(this.cache.get(url));
                return true;
            }
        }
        
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                //fetchBitmap(url, callback);
                Bitmap image = null;
                try {
                    InputStream is = (new java.net.URL(url)).openStream();
                    image = BitmapFactory.decodeStream(is);
                    
                    //failed to create image from input stream
                    if (null == image) {
                        callback.onFetchImageFailure(new Exception(String.format("Unable to decode image stream from url: %s", url)));
                    } else {
                        //success
                        callback.onFetchImageSuccess(image);
                        
                        //store image in cache
                        synchronized(cacheLock) {
                            cache.put(url, image);
                        }
                    }
                    
                } catch (Exception e) {
                    callback.onFetchImageFailure(e);
                }
                
            }
        };

        try {
            if (asyncOnNetworkFetch) {
                this.executeTaskAsync(task);
            } else {
                task.run();
            }
        } catch (InterruptedException e) {

            return false;
        }

        

        return true;
    }

    private void fetchBitmap(String url, ImageClient.Callback callback) {

        try {
            HttpResponse httpResponse = this.httpGet(url);
            StatusLine statusLine = httpResponse.getStatusLine();

            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }

            InputStream dataStream = httpResponse.getEntity().getContent();
            Bitmap image = BitmapFactory.decodeStream(dataStream);

            if (null == image) {
                Exception e = new Exception("Unable to create Bitmap image from stream");
                callback.onFetchImageFailure(e);
            } else {

                // cache image
                synchronized (this.cacheLock) {
                    this.cache.put(url, image);
                }

                // return image
                callback.onFetchImageSuccess(image);
            }

        } catch (Exception e) {
            callback.onFetchImageFailure(e);
        }

    }
    
    public void fetchAndSetBitmap(final String url, final android.widget.ImageView view) {
        this.fetchBitmap(url, new ImageClient.Callback() {
            
            @Override
            public void onFetchImageSuccess(Bitmap image) {
                // TODO Auto-generated method stub
                view.setImageBitmap(image);
                
                //Looper.getMainLooper().getThread().
            }
            
            @Override
            public void onFetchImageFailure(Exception error) {
                // TODO Auto-generated method stub
                
            }
        }, false, true);
    }
    
    public void clearCache() {
        synchronized (this.cacheLock) {
            this.cache.clear();
        }
    }
}
