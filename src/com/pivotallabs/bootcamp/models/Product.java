package com.pivotallabs.bootcamp.models;

import java.util.EnumMap;
import java.util.Map;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

public class Product {
	
	public enum Attribute {
		NAME("name"),
		SKU("sku"),
		REGULAR_PRICE("regularPrice"),
		SALE_PRICE("salePrice"),
		ON_SALE("onSale");
		
		private final String attributeName;
		Attribute(String name) {
			this.attributeName = name;
		}
	}
	
	Map <Attribute, Object> attributesMap;
	
	public Product() {
		this.attributesMap = new EnumMap<Product.Attribute, Object>(Product.Attribute.class);
	}
	
	public Object getAttribute(Product.Attribute attribute) {
	    return this.attributesMap.get(attribute);
	}
	
	public Object setAttribute(Product.Attribute attribute, @NonNull Object value) {
	    Object previousValue = this.attributesMap.put(attribute, value);
	    return previousValue;
	}
	
	public void setAttributes(@NonNull EnumMap<Product.Attribute, Object> sourceMap) {
	    this.attributesMap.putAll(sourceMap);
	}
}
