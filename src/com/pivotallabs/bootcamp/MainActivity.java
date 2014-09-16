package com.pivotallabs.bootcamp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ListView;

import com.pivotallabs.bootcamp.adaptors.ProductArrayAdapter;
import com.pivotallabs.bootcamp.clients.ImageClient;
import com.pivotallabs.bootcamp.clients.JSONClient;
import com.pivotallabs.bootcamp.models.Product;
import com.pivotallabs.bootcamp.remixAPI.RemixHttpTask;

public class MainActivity extends Activity implements RemixHttpTask.Callback{

	
	//private final String testRequest = "http://api.remix.bestbuy.com/v1/products/1305180947.json?show=sku%2Cname&apiKey=fd5a9pp3fs96z6nvw3bmmpt6";
    private final String testRequest = "http://api.remix.bestbuy.com/v1/products?format=json&show=sku,name,regularPrice,salePrice,onSale,image,largeImage,thumbnailImage,spin360URL&apiKey=fd5a9pp3fs96z6nvw3bmmpt6&sort=regularPrice.desc";
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
		Product product = new Product();
		product.setAttribute(Product.Attribute.NAME, "Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah Blah!");
		product.setAttribute(Product.Attribute.SALE_PRICE, new Double(39.98));
		this.productArrayAdapter.setDataSource(new Product[] {product, product, product});
		
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
				}, true);
				
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
		
		
		//test image download and display
		imageClient.fetchBitmap("http://placekitten.com/g/200/200", new ImageClient.Callback() {

            @Override
            public void onFetchImageSuccess(final Bitmap image) {
                // TODO Auto-generated method stub
                uiThreadHandler.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        ImageView iv = (ImageView)findViewById(R.id.test_image_view);
                        iv.setImageBitmap(image);
                    }
                });
            }

            @Override
            public void onFetchImageFailure(Exception error) {
                // TODO Auto-generated method stub
                
            }
		}, false, false);
		
		
		
		
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
