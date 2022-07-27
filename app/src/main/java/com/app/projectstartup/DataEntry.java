package com.app.projectstartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataEntry extends AppCompatActivity {
    EditText name,mobile,address,area,city,pincode;
    Spinner type;
    String typeArray[]={"Client","Dilivery Boy","Admin"};
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        db = FirebaseFirestore.getInstance();
        submit=findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArray);
     //   Spinner spin=findViewById(R.id.spinner);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spin.setAdapter(adapter);
        name=findViewById(R.id.etEmail2);
        mobile=findViewById(R.id.etEmail3);
        address=findViewById(R.id.etEmail4);
        area=findViewById(R.id.etEmail);
        city=findViewById(R.id.etEmail5);
        pincode=findViewById(R.id.etEmail6);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleClass data=new simpleClass(name.getText().toString(),address.getText().toString(),city.getText().toString(),area.getText().toString(),Long.parseLong(mobile.getText().toString()),Long.valueOf(pincode.getText().toString()),1,user.getEmail());
                Toast.makeText(DataEntry.this, "data"+data, Toast.LENGTH_SHORT).show();

                CollectionReference dbentry=db.collection("user");
                dbentry.document(user.getUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DataEntry.this, "User add successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(DataEntry.this,customerMain.class);
                        startActivity(intent);
                    }
                });
                        
            }
        });

    }
}
