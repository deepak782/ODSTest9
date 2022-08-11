package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadDataActivity extends AppCompatActivity {

    ListView listView;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList=new ArrayList<>();
    Spinner spinner;
    String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        listView=findViewById(R.id.MyList);
        spinner=findViewById(R.id.state);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


    }

    private void loadData(String state) {
        arrayList.clear();
        progressDialog.show();
        progressDialog.setMessage("Loading Data......");

        firebaseFirestore.collection("ADD").whereEqualTo("State",""+state).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();

                if(!task.isSuccessful())
                {
                    Toast.makeText(ReadDataActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
                else {

                    for(QueryDocumentSnapshot qb:task.getResult())
                    {
                        String name=qb.getString("Name");
                        String mobile=qb.getString("Mobile");
                        String mail=qb.getString("Mail");
                        String gender=qb.getString("Gender");
                        String state=qb.getString("State");

                        arrayList.add(""+name+"\n"+mobile+"\n"+mail+"\n"+gender+"\n"+state);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

            }
        });
    }

    public void search(View view) {

        state=spinner.getSelectedItem().toString();
        loadData(state);
    }
}