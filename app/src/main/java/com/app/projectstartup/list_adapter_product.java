package com.app.projectstartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class list_adapter_product extends BaseAdapter {
    all_product all_product;
    List<String> p_name;
    List<String> p_shop;
    customerMain customerMain;
    List<String> p_price;
    public list_adapter_product(all_product all_product, List<String> p_name, List<String> p_shop, List<String> p_price) {
        this.all_product=all_product;
        this.p_name=p_name;
        this.p_shop=p_shop;
        this.p_price=p_price;
    }

    public list_adapter_product(List<String> p_name, List<String> p_price, List<String> p_shop, customerMain customerMain) {
        this.customerMain=customerMain;
        this.p_name=p_name;
        this.p_shop=p_shop;
        this.p_price=p_price;
    }
//    public list_adapter_product(OnSuccessListener<QuerySnapshot> querySnapshotOnSuccessListener, List<String> p_name, List<String> p_shop, List<String> p_price) {
//
//    }

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
        LayoutInflater layoutInflater= (LayoutInflater) all_product.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.item_all_product,parent,false);
        TextView namet=convertView.findViewById(R.id.textView8);
        TextView shopt=convertView.findViewById(R.id.textView12);
       // TextView pricet=convertView.findViewById(R.id.textView11);
        namet.setText(p_name.get(position));
        shopt.setText(p_shop.get(position));
       // pricet.setText(p_price.get(position));

        return convertView;
    }
}
