package com.pivotallabs.bootcamp.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import org.json.*;

import com.pivotallabs.bootcamp.remixAPI.*;

public class ParserTest extends AndroidTestCase {
	
	RemixResponse response;
	RemixResponseParser parser = new RemixResponseParser();
	
	
    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testParseSuccessful() {
        
    	String json = "";
    	RemixResponseParser.parse(RemixAPI.Format.JSON, json);
    	
        fail("Not yet implemented");
    }
    
    @Test
    public void testParseFailFromMalformedJSON() {

    	String badJSON = "";
    	
    	RemixResponseParser.parse(RemixAPI.Format.JSON, badJSON);
    	
    	fail("Not yet implemented");
    }
    
    
    // Helper Methods
    
    private static RemixResponse createRemixResponse(RemixAPI.Format format, String response) {
    	return new RemixResponse(format, response);
    }
}
