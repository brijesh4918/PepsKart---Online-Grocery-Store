package com.app.projectstartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class customerMain extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    List<String> p_name,p_price,p_shop,p_weight,p_link,p_id;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Toolbar toolbar = null;

            toolbar = (Toolbar) findViewById(R.id.toolbar2);

       setSupportActionBar(toolbar);
       ImageView imageView=toolbar.findViewById(R.id.imageView5);
        p_name=new ArrayList<>();
        p_shop=new ArrayList<>();
        p_price=new ArrayList<>();
        p_weight=new ArrayList<>();
        p_link=new ArrayList<>();
        p_id=new ArrayList<>();
        listView=findViewById(R.id.list2);

        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        db.collection("product")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot ds:queryDocumentSnapshots)
                        {
                          //  Toast.makeText(customerMain.this, ds.getString("p_name"), Toast.LENGTH_SHORT).show();
                            p_name.add(ds.getString("p_name"));
                            p_shop.add(ds.getString("p_store"));
                            p_price.add(String.valueOf(ds.getLong("p_price")));
                            p_weight.add(String.valueOf(ds.getLong("p_weight")));
                            p_link.add(ds.getString("p_image"));
                            p_id.add(ds.getId());


                        }
                        final customer_Adaper customer_adaper=new customer_Adaper(p_name,p_price,p_shop,p_link,customerMain.this);
//                        list_adapter_product list_adapter_product=new list_adapter_product(customerMain.this,p_name,p_shop,p_price);
                        listView.setAdapter(customer_adaper);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(customerMain.this,product_Detail.class);
                                intent.putExtra("p_id",p_id.get(position));
                                intent.putExtra("name",p_name.get(position));
                                intent.putExtra("weight",p_weight.get(position));
                                intent.putExtra("price",p_price.get(position));
                                intent.putExtra("store",p_shop.get(position));
                                intent.putExtra("link",p_link.get(position));
                                startActivity(intent);
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.orders)
        {
            Intent intent=new Intent(customerMain.this,orders.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
