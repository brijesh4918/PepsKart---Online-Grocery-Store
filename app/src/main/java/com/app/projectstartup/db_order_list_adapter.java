package com.app.projectstartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class db_order_list_adapter extends BaseAdapter {
    delivery_boy_Activity delivery_boy_activity;
    List<String> order_id,o_name,o_address,o_date;
    List<String> name,address,date;

    public db_order_list_adapter(delivery_boy_Activity delivery_boy_activity, List<String> order_id, List<String> o_name, List<String> o_date, List<String> o_address) {
        this.delivery_boy_activity=delivery_boy_activity;
        this.o_name=o_name;
        this.o_date=o_date;
        this.o_address=o_address;
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
        LayoutInflater layoutInflater= (LayoutInflater) delivery_boy_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.item_db_order_list,parent,false);
        TextView name=convertView.findViewById(R.id.textView10);
        TextView date=convertView.findViewById(R.id.textView13);
        TextView address=convertView.findViewById(R.id.textView15);
        name.setText(o_name.get(position));
        date.setText(o_date.get(position));
        address.setText(o_address.get(position));

        return convertView;
    }
}
