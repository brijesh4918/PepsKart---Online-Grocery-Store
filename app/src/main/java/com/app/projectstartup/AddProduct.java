package com.app.projectstartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    String name,store;
    Long qnt,price,weight;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    EditText namet,qntt,pricet,weightt;
    Spinner storet;
    Button add,photo,all;
    String imagepath=null;
    private StorageReference Folder;
    String stores[]={
      "D-Mart","Reliance","Maruti"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        all=findViewById(R.id.button3);
        namet=findViewById(R.id.editText2p);
        storet=findViewById(R.id.spinnerp);
        qntt=findViewById(R.id.editText3p);
        pricet=findViewById(R.id.editText4p);
        weightt=findViewById(R.id.editText5p);
        photo=findViewById(R.id.button2);
        Folder= FirebaseStorage.getInstance().getReference().child("product_Image");
        add=findViewById(R.id.buttonp);
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storet.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> city = new HashMap<>();
                city.put("p_name",namet.getText().toString());
                city.put("p_price",Long.parseLong(pricet.getText().toString()));
                city.put("p_qnt", Long.parseLong(qntt.getText().toString()));
                city.put("p_store", storet.getSelectedItem().toString());
                city.put("p_weight",Long.parseLong(weightt.getText().toString()));
                city.put("p_image",imagepath);

              // simpleClass p_data=new simpleClass(namet.getText().toString(),storet.getSelectedItem().toString(),Long.parseLong(qntt.getText().toString()),Long.parseLong(pricet.getText().toString()),Long.parseLong(weightt.getText().toString()));
                CollectionReference reference = db.collection("product");
                reference.add(city)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddProduct.this, "add successfully", Toast.LENGTH_SHORT).show();             
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProduct.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddProduct.this,all_product.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                Uri uri=data.getData();
                final StorageReference file=Folder.child("image"+uri.getLastPathSegment());
                Toast.makeText(this, "data arrived", Toast.LENGTH_SHORT).show();
                //file.putFile(uri);
                file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddProduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imagepath=uri.toString();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProduct.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
