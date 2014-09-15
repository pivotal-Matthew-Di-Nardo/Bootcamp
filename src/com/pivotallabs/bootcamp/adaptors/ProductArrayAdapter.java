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
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Product product = this.productArray[arg0];
        
        if (null == arg1) {
            arg1 = this.layoutInflator.inflate(R.layout.products_list_view_item, arg2, false);
        }
        TextView name = (TextView) arg1.findViewById(R.id.products_list_view_item_name_textview);
        
        
        
        return null;
    }

}
