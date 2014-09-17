package com.pivotallabs.bootcamp.models;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

public class Product {
	
	public enum Attribute {
		NAME("name"),
		SKU("sku"),
		REGULAR_PRICE("regularPrice"),
		SALE_PRICE("salePrice"),
		ON_SALE("onSale"),
		THUMBNAIL_IMAGE_URL("thumbnailImage"),
		LARGE_IMAGE_URL("largeImage");
		
		private final String apiKeyName;
		Attribute(String name) {
			this.apiKeyName = name;
		}
	}
	
	Map <Attribute, Object> attributesMap;
	
	public Product() {
		this.attributesMap = new EnumMap<Product.Attribute, Object>(Product.Attribute.class);
	}
	
	public Product(JSONObject productJSON) {
	    this();
	    
	    for(Product.Attribute attribute : Product.Attribute.values()) {
	        try {
                Object value = productJSON.get(attribute.apiKeyName);
                
                this.attributesMap.put(attribute, value);
                
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	    
	}
	
	public Object getAttribute(Product.Attribute attribute) {
	    return this.attributesMap.get(attribute);
	}
	
	public Map<Product.Attribute, Object> getAttributeMapCopy() {
	    return Collections.unmodifiableMap(new EnumMap<Product.Attribute, Object>(this.attributesMap));
	}
	
	public Object setAttribute(Product.Attribute attribute, @NonNull Object value) {
	    Object previousValue = this.attributesMap.put(attribute, value);
	    return previousValue;
	}
	
	public void setAttributes(@NonNull EnumMap<Product.Attribute, Object> sourceMap) {
	    this.attributesMap.putAll(sourceMap);
	}
}
