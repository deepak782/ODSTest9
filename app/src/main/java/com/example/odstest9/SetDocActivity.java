package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SetDocActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText name,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_doc);
        name=findViewById(R.id.editTextTextPersonName);
        mobile=findViewById(R.id.editTextTextPersonName2);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void setDoc(View view) {
        progressDialog.show();

        Map<String,Object> map=new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Course","Android");
        map.put("Age",26);
        map.put("Mobile",mobile.getText().toString());
        map.put("Bike",true);


        firebaseFirestore.collection("DocTest").document(""+mobile.getText().toString()).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();

                Toast.makeText(SetDocActivity.this, "Successfully doc added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(SetDocActivity.this, "Server "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}