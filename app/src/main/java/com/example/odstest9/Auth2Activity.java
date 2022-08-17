package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Auth2Activity extends AppCompatActivity {

    EditText mail,pass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String mailStr,passStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth2);
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void signup(View view) {

        progressDialog.show();
        progressDialog.setMessage("Creating User!!");

        mailStr=mail.getText().toString();
        passStr=pass.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(mailStr,passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Toast.makeText(Auth2Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Auth2Activity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Auth2Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



            }
        });
    }


}