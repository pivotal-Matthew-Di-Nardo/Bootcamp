package com.pivotallabs.bootcamp.models;

import java.util.EnumMap;
import java.util.Map;

import android.graphics.BitmapFactory;

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
		
	}
}
