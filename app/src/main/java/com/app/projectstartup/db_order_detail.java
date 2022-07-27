package com.app.projectstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class db_order_detail extends AppCompatActivity {
    String order_id;
    TextView c_name,c_mobile,c_address,c_city,c_area,c_pincode,o_name,o_store,o_time,o_price,db_name,db_mobile,o_status;
    Button b1,b2,b3;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_order_detail);
        order_id=getIntent().getExtras().getString("o_id");
        c_name=findViewById(R.id.textView22);
        c_mobile=findViewById(R.id.textView24);
        c_address=findViewById(R.id.textView26);
        c_area=findViewById(R.id.textView28);
        c_city=findViewById(R.id.textView30);
        c_pincode=findViewById(R.id.textView32);
        o_name=findViewById(R.id.textView34);
        o_store=findViewById(R.id.textView36);
        o_time=findViewById(R.id.textView39);
        o_price=findViewById(R.id.textView41);
        db_name=findViewById(R.id.textView43);
        db_mobile=findViewById(R.id.textView45);
        o_status=findViewById(R.id.textView47);
        b1=findViewById(R.id.button9);
        b2=findViewById(R.id.button7);
        b3=findViewById(R.id.button8);
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");


        db.collection("order").document(order_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot ds) {
                c_name.setText(ds.getString("user_name"));
                c_mobile.setText(ds.getString("user_mobile"));
                c_address.setText(ds.getString("o_address"));
                c_area.setText(ds.getString("o_area"));
                c_city.setText(ds.getString("o_city"));
                c_pincode.setText(ds.getString("o_pincode"));
                o_store.setText(ds.getString("o_store"));
                Date resultdate = new Date(ds.getLong("o_time"));
                o_time.setText(sdf.format(resultdate));
                o_price.setText(ds.getString("o_price"));
                if(ds.getLong("o_status")==1)
                {
                    o_status.setText("");
                }else if(ds.getLong("o_status")==2)
                {
                    o_status.setText("order out of delivery");
                }else if(ds.getLong("o_status")==3)
                {
                    o_status.setText("order delivered");
                }
//                o_status.setText(ds.getLong("o_status").toString());
                o_name.setText(ds.getString("o_name"));

                db.collection("delivery_boy").document(ds.getString("o_delivery_boy_id")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                            db_name.setText(documentSnapshot.getString("db_name"));
                            db_mobile.setText(documentSnapshot.getLong("db_mobile").toString());
                    }
                });
            }
        });
        
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("order").document(order_id).update("o_status",1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(db_order_detail.this, "update successfully", Toast.LENGTH_SHORT).show();     
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("order").document(order_id).update("o_status",2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(db_order_detail.this, "update successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("order").document(order_id).update("o_status",3).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(db_order_detail.this, "update successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
