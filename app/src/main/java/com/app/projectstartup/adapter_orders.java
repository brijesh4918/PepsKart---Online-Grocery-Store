package com.app.projectstartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class adapter_orders extends BaseAdapter {
    orders orders;
    List<String> o_name,o_store,o_price;
    public adapter_orders(orders orders, List<String> o_name, List<String> o_store, List<String> o_price) {
    this.orders=orders;
    this.o_name=o_name;
    this.o_price=o_price;
    this.o_store=o_store;
    }

    @Override
    public int getCount() {
        return o_name.size();
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
        LayoutInflater layoutInflater= (LayoutInflater) orders.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.item_db_order_list,parent,false);
        TextView t1=convertView.findViewById(R.id.textView10);
        TextView t2=convertView.findViewById(R.id.textView15);
        TextView t3=convertView.findViewById(R.id.textView13);
        t1.setText(o_name.get(position));
        t2.setText(o_store.get(position));
        t3.setText(o_price.get(position));
      // Toast.makeText(orders, ""+o_name.get(position), Toast.LENGTH_SHORT).show();
        return convertView;
    }
}
