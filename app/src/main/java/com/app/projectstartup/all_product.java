package com.app.projectstartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class all_product extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    List<String> p_name,p_price,p_shop,p_link;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        p_name=new ArrayList<>();
        p_shop=new ArrayList<>();
        p_price=new ArrayList<>();
        p_link =new ArrayList<>();

        listView=findViewById(R.id.list);

                firebaseAuth=FirebaseAuth.getInstance();
                db=FirebaseFirestore.getInstance();
                db.collection("product")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for(DocumentSnapshot ds:queryDocumentSnapshots)
                                {
                                    Toast.makeText(all_product.this, ds.getString("p_name"), Toast.LENGTH_SHORT).show();
                                        p_name.add(ds.getString("p_name"));
                                        p_shop.add(ds.getString("p_store"));
                                        p_price.add(String.valueOf(ds.getLong("p_price")));
                                        p_link.add(ds.getString("p_image"));

                                }
                            list_adapter_product list_adapter_product=new list_adapter_product(all_product.this,p_name,p_shop,p_price);
                            listView.setAdapter(list_adapter_product);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

    }
}
