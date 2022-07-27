package com.app.projectstartup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class product_Detail extends AppCompatActivity {
    TextView namet,storet,weightt,pricet;
    ImageView imageView;
    FirebaseAuth auth;
    FirebaseFirestore db;
    AlertDialog.Builder builder;
    Button cart;
    String address,city,area,pincode,o_name,o_price,o_store,o_userid,nameb,mobile,db_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        namet=findViewById(R.id.textView18);
        builder = new AlertDialog.Builder(this);
        pricet=findViewById(R.id.textView9);
        weightt=findViewById(R.id.textView16);
        storet=findViewById(R.id.textView17);
        imageView=findViewById(R.id.imageView);
        cart = findViewById(R.id.button4);
        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("name");
        String store=bundle.getString("store");
        String price=bundle.getString("price");
        String weight=bundle.getString("weight");
        String link=bundle.getString("link");
        final String pid=bundle.getString("p_id");
        namet.setText(name);
        pricet.setText("Price :-"+price+"/-");
        weightt.setText("Weight :-"+weight+"gm");
        storet.setText("Store :-"+store);
        final FirebaseUser user=auth.getCurrentUser();
        Glide.with(getApplicationContext()).load(link).into(imageView);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Do you confirm Your Order ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

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
                                                    nameb=ds.getString("name");
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
                                                                odata.put("user_name",nameb);
                                                                odata.put("user_mobile",mobile);
                                                                odata.put("o_time",System.currentTimeMillis());
                                                                odata.put("o_user_id",auth.getUid());
                                                                //                db.collection("order")
//                        .add()
                                                                db.collection("order").add(odata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Toast.makeText(product_Detail.this, "Order Successfully", Toast.LENGTH_SHORT).show();
                                                                        Map<String , Object> update_re=new HashMap<>();
                                                                        update_re.put("db_orders_id",documentReference.getId());
                                                                        String doccc=documentReference.getId();
//                                               db.collection("delivery_boy").document(db_id).set(FieldValue.arrayUnion(update_re)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                   @Override
//                                                   public void onSuccess(Void aVoid) {
//                                                       Toast.makeText(cart.this, "update Successfully", Toast.LENGTH_SHORT).show();
//                                                   }
//                                               });
//                                                        db.collection("delivery_boy").document(db_id).update("db_order", FieldValue.arrayUnion(doccc)).addOnSuccessListener(new OnSuccessListener<Void>() {
////                                                            @Override
////                                                            public void onSuccess(Void aVoid) {
////                                                                Toast.makeText(cart.this, "updated", Toast.LENGTH_SHORT).show();
////                                                            }
////                                                        });

                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
//

//                Intent intent=new Intent(product_Detail.this,cart.class);
////                intent.putExtra("product_id",pid);
////                startActivity(intent);


            }
        });




    }
}
