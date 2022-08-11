package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AddingActivity extends AppCompatActivity {

    EditText name,mail,mobile;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner state;

    String nameStr,mailStr,mobileStr,genderStr,stateStr;

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        name=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        mobile=findViewById(R.id.mobile);
        radioGroup=findViewById(R.id.rg);
        state=findViewById(R.id.state);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void submit(View view) {
        progressDialog.show();
        progressDialog.setMessage("Checking mobile number");

        nameStr=name.getText().toString();
        mobileStr=mobile.getText().toString();
        mailStr=mail.getText().toString();
        radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        genderStr=radioButton.getText().toString();
        stateStr=state.getSelectedItem().toString();

        DocumentReference documentReference=firebaseFirestore.collection("ADD").document(""+mobileStr);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Toast.makeText(AddingActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DocumentSnapshot documentSnapshot= task.getResult();
                    if(documentSnapshot.exists())
                    {
                        Toast.makeText(AddingActivity.this, "Mobile Number Already Exist", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AddData(nameStr,mobileStr,mailStr,genderStr,stateStr);
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddingActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void AddData(String nameStr, String mobileStr, String mailStr, String genderStr, String stateStr) {

        progressDialog.show();
        progressDialog.setMessage("Please wait Almost Done!!.....");

        Map<String,Object> map=new HashMap<>();
        map.put("Name",nameStr);
        map.put("Mobile",mobileStr);
        map.put("Mail",mailStr);
        map.put("Gender",genderStr);
        map.put("State",stateStr);

        firebaseFirestore.collection("ADD").document(""+mobileStr).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(AddingActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(AddingActivity.this, "Fail to Add", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void read(View view) {
        startActivity(new Intent(AddingActivity.this,ReadDataActivity.class));
    }
}