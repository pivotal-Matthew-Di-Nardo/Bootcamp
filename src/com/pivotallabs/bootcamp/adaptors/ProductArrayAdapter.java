package com.pivotallabs.bootcamp.adaptors;

import com.pivotallabs.bootcamp.R;
import com.pivotallabs.bootcamp.models.Product;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductArrayAdapter extends BaseAdapter {

    Product[] productArray;
    Context context;
    LayoutInflater layoutInflator;
    
    public ProductArrayAdapter(Context context) {
        this.productArray = null;
        this.context = context;
        this.layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public ProductArrayAdapter(Context context, Product[] array) {
        this.productArray = array;
        this.context = context;
        this.layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setDataSource(Product[] array) {
        this.productArray = array;
        this.notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (null == this.productArray) return 0;
        else return this.productArray.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        if (null == this.productArray) return null;
        else return this.productArray[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int index, View convertedView, ViewGroup parent) {
        Product product = this.productArray[index];
        
        if (null == convertedView) {
            convertedView = this.layoutInflator.inflate(R.layout.products_list_view_item, parent, false);
        }
        TextView name = (TextView) convertedView.findViewById(R.id.products_list_view_item_name_textview);
        TextView price = (TextView) convertedView.findViewById(R.id.products_list_view_item_price_textview);
        
        name.setText((String) product.getAttribute(Product.Attribute.NAME));
        price.setText( ((Double)product.getAttribute(Product.Attribute.SALE_PRICE)).toString() );
        
        return convertedView;
    }

}
