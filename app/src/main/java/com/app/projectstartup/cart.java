package com.app.projectstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class cart extends AppCompatActivity {
        FirebaseAuth auth;
        FirebaseFirestore db;
        String address,city,area,pincode,o_name,o_price,o_store,o_userid,name,mobile,db_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        Bundle bundle=getIntent().getExtras();
        final String pid=bundle.getString("product_id");
        Toast.makeText(this, "pid"+pid, Toast.LENGTH_SHORT).show();
        final FirebaseUser user=auth.getCurrentUser();
        Button b1=findViewById(R.id.button5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("user")
                        .whereEqualTo("email",user.getEmail())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for(DocumentSnapshot ds : queryDocumentSnapshots)
                                    {
                                        address=ds.getString("address");
                                        area=ds.getString("area");
                                        city=ds.getString("city");
                                        name=ds.getString("name");
                                        mobile=String.valueOf(ds.getLong("mobile"));
                                        pincode=String.valueOf(ds.getLong("pincode"));
                                    }

                                db.collection("delivery_boy").whereEqualTo("db_pincode",Long.parseLong(pincode)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            for (DocumentSnapshot ds :  queryDocumentSnapshots)
                                            {
                                                db_id=ds.getId();
                                            }

                                db.collection("product").document(pid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        o_name=documentSnapshot.getString("p_name");
                                        o_price=String.valueOf(documentSnapshot.getLong("p_price"));
                                        o_store=documentSnapshot.getString("p_store");
                                        //=documentSnapshot.getString("p_name");
                                        Map<String , Object> odata=new HashMap<>();
                                        odata.put("o_address",address);
                                        odata.put("o_area",area);
                                        odata.put("o_city",city);
                                        odata.put("o_delivery_boy_id",db_id);
                                        odata.put("o_dispatch",null);
                                        odata.put("o_name",o_name);
                                        odata.put("o_pincode",pincode);
                                        odata.put("o_price",o_price);
                                        odata.put("o_status",1);
                                        odata.put("o_store",o_store);
                                        odata.put("user_name",name);
                                        odata.put("user_mobile",mobile);
                                        odata.put("o_time",System.currentTimeMillis());
                                        odata.put("o_user_id",auth.getUid());
                                        //                db.collection("order")
//                        .add()
                                        db.collection("order").add(odata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(cart.this, "Order Successfully", Toast.LENGTH_SHORT).show();
                                                Map<String , Object> update_re=new HashMap<>();
                                                update_re.put("db_orders_id",documentReference.getId());
                                                String doccc=documentReference.getId();
//                                               db.collection("delivery_boy").document(db_id).set(FieldValue.arrayUnion(update_re)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                   @Override
//                                                   public void onSuccess(Void aVoid) {
//                                                       Toast.makeText(cart.this, "update Successfully", Toast.LENGTH_SHORT).show();
//                                                   }
//                                               });
                                                db.collection("delivery_boy").document(db_id).update("db_order",FieldValue.arrayUnion(doccc)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(cart.this, "updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });
                            }
                        });
                            }
                        });

            }
        });
        Button b2=findViewById(R.id.button6);
        final Map<String,Long> up=new HashMap<>();
        up.put("p_price",30L);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("product").document("QhI7Hhr5PrJS5x5GoTUq").set(up, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(cart.this, "update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
