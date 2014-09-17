package com.pivotallabs.bootcamp;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ListView;

import com.pivotallabs.bootcamp.adaptors.ProductArrayAdapter;
import com.pivotallabs.bootcamp.clients.ImageClient;
import com.pivotallabs.bootcamp.clients.JSONClient;
import com.pivotallabs.bootcamp.models.Product;
import com.pivotallabs.bootcamp.parsers.ProductCollectionsParser;

public class MainActivity extends Activity {

	
	//private final String testRequest = "http://api.remix.bestbuy.com/v1/products/1305180947.json?show=sku%2Cname&apiKey=fd5a9pp3fs96z6nvw3bmmpt6";
    private final String requestMostExpensive = "http://api.remix.bestbuy.com/v1/products?format=json&show=sku,name,regularPrice,salePrice,onSale,image,largeImage,thumbnailImage,spin360URL&apiKey=fd5a9pp3fs96z6nvw3bmmpt6&sort=regularPrice.desc";
    private final String requestLeastExpensive = "http://api.remix.bestbuy.com/v1/products?format=json&show=sku,name,regularPrice,salePrice,onSale,image,largeImage,thumbnailImage,spin360URL&apiKey=fd5a9pp3fs96z6nvw3bmmpt6&sort=regularPrice.asc";
    
	private static JSONClient jsonClient;
	private static ImageClient imageClient;
	private static Handler uiThreadHandler;
	
	private ProductArrayAdapter productArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (null == this.jsonClient) this.jsonClient = JSONClient.getInstance();
		if (null == this.imageClient) this.imageClient = ImageClient.getInstance();
		if (null == this.uiThreadHandler) this.uiThreadHandler = new Handler(getMainLooper());
		
		//set full screen
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		//create a new product array adapter and bind the product list view to it
		this.productArrayAdapter = new ProductArrayAdapter(getApplicationContext());
		ListView productsListView = (ListView)findViewById(R.id.products_list_view);
		productsListView.setAdapter(this.productArrayAdapter);
		
		((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

			    fetchAndDisplayProducts();
				
			}
		});
		
		
		
		((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    //findViewById(R.id.textbox_scrollview).
				((TextView)findViewById(R.id.textbox)).setText("");
			}
		});

	}
	
	
	
	private String getSearchRequest() {
	    
	    String request;
	    
	    Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	    int position = spinner.getSelectedItemPosition();
	    
	    switch(position) {
	    case 0:
	        request = this.requestMostExpensive;
	        break;
	    case 1:
	        request = this.requestLeastExpensive;
	        break;
	    default:
	        Log.d("MainActivity::getSearchRequest", "unexpected index value");
	        request = this.requestMostExpensive;
	    }
	    
	    return request;
	}
	private void fetchAndDisplayProducts() {
	    
	    String request = this.getSearchRequest();
	    
	    jsonClient.fetchJSON(request, new JSONClient.FetchJSONCallback() {
            
            @Override
            public void onFetchJSONSuccess(final String json) {
                // TODO Auto-generated method stub
                uiThreadHandler.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) findViewById(R.id.textbox);
                        tv.setText(json);
                        
                      //parse json data and populate array adapter
                        Product[] newProductArray = ProductCollectionsParser.parse(json);
                        productArrayAdapter.setDataSource(newProductArray);
                    }
                });
            }
            
            @Override
            public void onFetchJSONFail(final Exception exception) {
                // TODO Auto-generated method stub
                
            }
        }, true);
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
}
