package com.pivotallabs.bootcamp.tests;

import java.util.EnumMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import org.json.*;

import com.pivotallabs.bootcamp.clients.JSONClient;
import com.pivotallabs.bootcamp.models.Product;
import com.pivotallabs.bootcamp.models.Product.Attribute;
import com.pivotallabs.bootcamp.parsers.ProductCollectionsParser;


public class ParserTest extends AndroidTestCase {
	
	
	
    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testParse() {
        //Expected: 10 results (1st page), each containing name, regularPrice, largeImage, etc...
    	String request = "http://api.remix.bestbuy.com/v1/products?format=json&show=sku,name,regularPrice,salePrice,onSale,image,largeImage,thumbnailImage,spin360URL&apiKey=fd5a9pp3fs96z6nvw3bmmpt6&sort=regularPrice.desc";
    	
    	JSONClient.getInstance().fetchJSON(request, new JSONClient.FetchJSONCallback() {
            
            @Override
            public void onFetchJSONSuccess(final String json) {
                // TODO Auto-generated method stub
                Product[] products = ProductCollectionsParser.parse(json);
                
                assertNotNull(products);
                
                assertEquals(10, products.length);
                
                assertNotNull(products[0].getAttribute(Product.Attribute.NAME));
                assertNotNull(products[0].getAttribute(Product.Attribute.REGULAR_PRICE));
                assertNotNull(products[0].getAttribute(Product.Attribute.LARGE_IMAGE_URL));
            }
            
            @Override
            public void onFetchJSONFail(Exception exception) {
                // TODO Auto-generated method stub
                fail("Couldn't complete test: unable to retrieve JSON.");
            }
        }, false);
    	
    }
    
    
}
