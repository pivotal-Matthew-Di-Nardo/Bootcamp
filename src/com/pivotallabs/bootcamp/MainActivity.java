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
import android.os.Handler;
import android.os.Parcel;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pivotallabs.bootcamp.R;
import com.pivotallabs.bootcamp.clients.JSONClient;
import com.pivotallabs.bootcamp.remixAPI.*;

import com.pivotallabs.bootcamp.clients.*;

public class MainActivity extends Activity implements RemixHttpTask.Callback{

	
	private final String testRequest = "http://api.remix.bestbuy.com/v1/products/1305180947.json?show=sku%2Cname&apiKey=fd5a9pp3fs96z6nvw3bmmpt6";
	private static JSONClient jsonClient;
	private static Handler uiThreadHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (null == this.jsonClient) this.jsonClient = JSONClient.getInstance();
		if (null == this.uiThreadHandler) this.uiThreadHandler = new Handler(getMainLooper());
		
		//set full screen
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

			    jsonClient.fetchJSON(testRequest, new JSONClient.FetchJSONCallback() {
					
					@Override
					public void onFetchJSONSuccess(final String json) {
						// TODO Auto-generated method stub
						uiThreadHandler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								TextView tv = (TextView) findViewById(R.id.textbox);
								tv.setText(json);
							}
						});
					}
					
					@Override
					public void onFetchJSONFail(final Exception exception) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		
		
		
		((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView)findViewById(R.id.textbox)).setText("");
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
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
