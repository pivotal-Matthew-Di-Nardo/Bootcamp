package com.pivotallabs.bootcamp.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pivotallabs.bootcamp.models.Product;

import android.util.JsonReader;


public class ProductCollectionsParser {

    public static Product[] parse(String in) {

        Product[] newProductArray;
        
        try {
            
            JSONObject jsonObject = new JSONObject(in);
            
            JSONArray jsonProductArray = jsonObject.getJSONArray("products");
            
            newProductArray = new Product[jsonProductArray.length()];
            
            for (int i = 0; i < jsonProductArray.length(); i++) {
                
                JSONObject productJSON = jsonProductArray.getJSONObject(i);
                
                Product product = new Product(productJSON);
                //productList.add(product);
                newProductArray[i] = product;
            }
            
            return newProductArray;
            
        } catch (Exception e){}
        
        return null;
    }
    
}
