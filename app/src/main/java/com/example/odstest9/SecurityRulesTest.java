package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SecurityRulesTest extends AppCompatActivity {

    EditText dob;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_rules_test);
        dob=findViewById(R.id.Dob);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

    }

    public void Add(View view) {
        progressDialog.show();
        progressDialog.setMessage("Adding Data!!!");
        Map<String,Object> map=new HashMap<>();
        map.put("born1",dob.getText().toString());
        map.put("user","UserName "+dob.getText().toString());

        firebaseFirestore.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.dismiss();
                Log.d("TAG", "onSuccess: "+documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("TAG", "onFailure: "+e.getLocalizedMessage());
            }
        });

       /* firebaseFirestore.collection("AnyName1").document("user2").set(map, SetOptions.merge())
       .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SecurityRulesTest.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+e.getLocalizedMessage());



            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();

                Log.d("TAG", "onSuccess: "+"Created");
            }
        });*/
    }

    public void get(View view) {
        progressDialog.show();
        progressDialog.setMessage("Getting Data!!!");

        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Log.d("TAG", "onComplete: "+task.getException());
                }
                else
                {
                    for(QueryDocumentSnapshot qb:task.getResult())
                    {
                        String born= qb.getString("born1");
                        String user=qb.getString("user");
                        Log.d("TAG", "onComplete: "+born+"\n"+user);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();


            }
        });

        //get one doc
       /* firebaseFirestore.collection("users").document("1cqFGlnZCAu1RNlQe8ma").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Log.d("TAG!", "onComplete: "+task.getException());
                }
                else
                {
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.exists())
                    {
                        String born=snapshot.getString("born1");
                        String user=snapshot.getString("user");
                        Log.d("TAG=", "onComplete: "+born+"\n"+user);
                    }
                    else
                    {
                        //null coming
                        Log.d("TAG=", "onComplete: "+"Fail"+task.getException());

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("TAGE", "onFailure: "+e.getLocalizedMessage());
            }
        });
*/
        /*firebaseFirestore.collection("AnyName1").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                Log.d("GETTAG1", "onSuccess: "+queryDocumentSnapshots.getDocuments());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("GETTAG2", "onFailure: "+e.getLocalizedMessage());

            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();

                if(!task.isSuccessful())
                {
                    Log.d("GETTAG4", "onComplete: "+task.getException());

                }
                else
                {

                    for(QueryDocumentSnapshot ab:task.getResult())
                    {
                        String born=ab.getString("born1");
                        String user=ab.getString("user");
                        String id=ab.getId();
                        Log.d("GETTAG3", "onComplete: "+born+"\n"+user+"\n"+id);
                    }
                }

            }
        });*/
        /*DocumentReference documentReference=firebaseFirestore.collection("AnyName1").document("user2");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Toast.makeText(SecurityRulesTest.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DocumentSnapshot documentSnapshot= task.getResult();
                    if(documentSnapshot.exists())
                    {
                        Toast.makeText(SecurityRulesTest.this, "Doc  Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SecurityRulesTest.this, "Doc Not  Found", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SecurityRulesTest.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/
    }
}