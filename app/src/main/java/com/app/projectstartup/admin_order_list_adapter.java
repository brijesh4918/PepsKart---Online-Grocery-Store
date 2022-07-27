package com.app.projectstartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class admin_order_list_adapter extends BaseAdapter {
    adminActivity adminActivity;
    List<String> o_name;
    List<String> db_name;
    List<String> db_mobile;
    public admin_order_list_adapter(adminActivity adminActivity, List<String> o_name, List<String> db_name, List<String> db_mobile) {
        this.adminActivity=adminActivity;
        this.o_name=o_name;
        this.db_mobile=db_mobile;
        this.db_name=db_name;
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
        LayoutInflater layoutInflater= (LayoutInflater) adminActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.item_db_order_list,parent,false);
        TextView namet=convertView.findViewById(R.id.textView10);
        TextView db_namet=convertView.findViewById(R.id.textView15);
        TextView db_mobilet=convertView.findViewById(R.id.textView13);
        namet.setText(o_name.get(position));
        db_mobilet.setText(db_mobile.get(position));
        db_namet.setText(db_name.get(position));
        return convertView;
    }
}
