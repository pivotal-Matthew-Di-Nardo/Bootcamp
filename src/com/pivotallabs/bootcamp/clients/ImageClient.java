package com.pivotallabs.bootcamp.clients;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

public class ImageClient extends AsyncClient {
    
    public interface Callback {
        public void onFetchImageSuccess(Bitmap image);
        public void onFetchImageFailure(Exception error);
    }
    
    private Map <String, Bitmap> cache;
    
    private ImageClient() {
        //this.cache = new HashMap<String, Bitmap>();
        this.cache = new ConcurrentHashMap<String, Bitmap>();
    }
    
    
    public Bitmap fetchBitmap(String url) {
        
        if (this.cache.containsKey(url)) return this.cache.get(url);
         else {
            
            
            
        }
        return null;
    }
}
