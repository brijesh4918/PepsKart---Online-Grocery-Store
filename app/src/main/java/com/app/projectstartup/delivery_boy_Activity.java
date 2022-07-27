package com.app.projectstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class delivery_boy_Activity extends AppCompatActivity {
    List<String> o_name,o_address,o_date,order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_);
        final ListView listView=findViewById(R.id.list3);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        o_name=new ArrayList<>();
        order_id=new ArrayList<>();
        o_address=new ArrayList<>();
        o_date=new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        db.collection("delivery_boy").document("sp33Qx7tXP1Wk00TwYlW").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              order_id= (List<String>) documentSnapshot.get("db_order");

                for(int i=0;i<order_id.size();i++) {
                    db.collection("order").document(order_id.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            o_name.add(documentSnapshot.getString("o_name"));
                            o_address.add(documentSnapshot.getString("o_address"));
                            Long timestamp=documentSnapshot.getLong("o_time");
                            Date resultdate = new Date(timestamp);
                           o_date.add(sdf.format(resultdate));
                            if(order_id.size()==o_name.size()) {
                                for (int i = 0; i < order_id.size(); i++) {
                                    db_order_list_adapter db_order_list_adapter=new db_order_list_adapter(delivery_boy_Activity.this,order_id,o_name,o_date,o_address);
                                    listView.setAdapter(db_order_list_adapter);
                                }
                            }
                        }
                    });
                    Toast.makeText(delivery_boy_Activity.this, "orderid"+order_id.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(delivery_boy_Activity.this,db_order_detail.class);
                intent.putExtra("o_id",order_id.get(position));
                startActivity(intent);
            }
        });


    }
}
