package com.app.projectstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class orders extends AppCompatActivity {
List<String> o_id,o_name,o_store,o_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        o_id=new ArrayList<>();
        o_name=new ArrayList<>();
        o_store=new ArrayList<>();
        o_price=new ArrayList<>();
        final ListView listView=findViewById(R.id.list9);

        db.collection("order").whereEqualTo("o_user_id",user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i=0;
                    for(DocumentSnapshot ds : queryDocumentSnapshots)
                    {
                               o_id.add(ds.getId());
                               o_name.add(ds.getString("o_name"));
                               o_store.add(ds.getString("o_store"));
                               o_price.add(ds.getString("o_price"));
                       // Toast.makeText(orders.this, ""+o_name, Toast.LENGTH_SHORT).show();
                                i++;
                    }
                    if(i==o_id.size())
                    {
//                        Toast.makeText(orders.this, ""+i+""+o_id.size(), Toast.LENGTH_SHORT).show();
                        adapter_orders adapter_orders=new adapter_orders(orders.this,o_name,o_store,o_price);
                        listView.setAdapter(adapter_orders);
            }}
        });


    }
}
