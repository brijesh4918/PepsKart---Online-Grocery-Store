package com.app.projectstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class adminActivity extends AppCompatActivity {
    ListView listView;
    int orderlength;
    List<String> o_name,db_name,db_mobile,o_id;
    String db_id;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listView=findViewById(R.id.list5);
        o_name=new ArrayList<>();
        db_name=new ArrayList<>();
        db_mobile=new ArrayList<>();
        o_id=new ArrayList<>();

//        db_id=new ArrayList<>();
        db.collection("order").orderBy("o_status", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        orderlength=queryDocumentSnapshots.size();

                         for(DocumentSnapshot ds : queryDocumentSnapshots) {
                             o_name.add(ds.getString("o_name"));
                             db_id=(ds.getString("o_delivery_boy_id"));
                                o_id.add(ds.getId());
                             db.collection("delivery_boy").document(db_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                 @Override
                                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                                     db_name.add(documentSnapshot.getString("db_name"));
                                     db_mobile.add(String.valueOf(documentSnapshot.getLong("db_mobile")));
                                     i++;
                                    // Toast.makeText(adminActivity.this, "i"+i, Toast.LENGTH_SHORT).show();
                                     if(orderlength==(i)){
                                         Toast.makeText(adminActivity.this, ""+orderlength+i, Toast.LENGTH_SHORT).show();
                                         admin_order_list_adapter admin_order_list_adapter=new admin_order_list_adapter(adminActivity.this,o_name,db_name,db_mobile);
                                         listView.setAdapter(admin_order_list_adapter);
                                     }
                                 }
                             });

                         }
                         }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(adminActivity.this,db_order_detail.class);
                intent.putExtra("o_id",o_id.get(position));
                startActivity(intent);
            }
        });
    }
}
