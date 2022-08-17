package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    EditText mail,pass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String mailStr,passStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void login(View view) {

        progressDialog.show();
        progressDialog.setMessage("Checking User!!");

        mailStr=mail.getText().toString();
        passStr=pass.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(mailStr,passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Toast.makeText(AuthActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AuthActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AuthActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();



            }
        });

    }

    public void forget(View view) {

        startActivity(new Intent(AuthActivity.this,Auth3Activity.class));
    }

    public void newsignup(View view) {
        startActivity(new Intent(AuthActivity.this,Auth2Activity.class));
    }
}