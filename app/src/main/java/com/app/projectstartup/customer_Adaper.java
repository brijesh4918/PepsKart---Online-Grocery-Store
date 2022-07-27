package com.app.projectstartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class customer_Adaper extends BaseAdapter {
    List<String> p_name;
    List<String> p_price;
    List<String> p_shop,p_link;
    customerMain customerMain;

    public customer_Adaper(List<String> p_name, List<String> p_price, List<String> p_shop, List<String> p_link, customerMain customerMain) {
        this.p_name=p_name;
        this.customerMain=customerMain;
        this.p_price=p_price;
        this.p_shop=p_shop;
        this.p_link=p_link;
    }

    @Override
    public int getCount() {
        return p_name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) customerMain.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.item_all_product,parent,false);
        TextView namet=convertView.findViewById(R.id.textView8);
        TextView shopt=convertView.findViewById(R.id.textView12);
        //TextView pricet=convertView.findViewById(R.id.textView11);
        ImageView imageView=convertView.findViewById(R.id.imageView5);
        namet.setText(p_name.get(position));
        shopt.setText(p_shop.get(position));
      //  pricet.setText(p_price.get(position));
        Glide.with(customerMain).load(p_link.get(position)).into(imageView);
        return convertView;
    }
}
