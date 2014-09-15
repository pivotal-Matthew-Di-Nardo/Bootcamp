package com.pivotallabs.bootcamp.models;

import java.util.Map;

import android.graphics.BitmapFactory;

public class Product {
	
	public enum Attribute {
		NAME("name");
		
		
		private final String attributeName;
		Attribute(String name) {
			this.attributeName = name;
		}
	}
	
	Map <Attribute, Object> attributesMap;
	
	public Product() {
		
	}
}
