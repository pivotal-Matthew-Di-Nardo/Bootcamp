package com.pivotallabs.bootcamp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pivotallabs.bootcamp.R;
import com.pivotallabs.bootcamp.remixAPI.*;

public class MainActivity extends Activity implements RemixHttpTask.Callback{

	
	private final String testRequest = "http://api.remix.bestbuy.com/v1/products/1305180947.json?show=sku%2Cname&apiKey=fd5a9pp3fs96z6nvw3bmmpt6";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set full screen
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final AsyncTask<String, String, String> asyncHttpRequest = new AsyncTask<String, String, String>() {

					@Override
					protected String doInBackground(String... params) {
						// TODO Auto-generated method stub
						
						String response = null;
						
						try {
							response = HttpRequest(params[0]);
						}
						catch (Exception e) {}
						
						return response;
					}
					
					
					protected void onPostExecute(String result) {
						TextView tv = (TextView) findViewById(R.id.textbox);
						tv.setText(result);
					}
				};
				
				asyncHttpRequest.execute(testRequest);
				
			    
				//TextView tv = (TextView) findViewById(R.id.textbox);
				//String productJSON = "blah";
				//
				//try {
				//	productJSON = HttpRequest("http://api.remix.bestbuy.com/v1/products/1305180947.json?show=sku,name&apiKey=fd5a9pp3fs96z6nvw3bmmpt6");
				//	tv.setText(productJSON);
				//} catch (Exception e) {
				//	tv.setText("Retrieval Failed!\n"+e.toString());
				//}
				
				
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    
	    
	}
	
	private String HttpRequest(String request) throws IOException, ClientProtocolException{
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        
            response = httpclient.execute(new HttpGet(request));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        
        
        return responseString;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


    @Override
    public void onRemixHttpTaskComplete(String result) {
        // TODO Auto-generated method stub
        
    }
}
