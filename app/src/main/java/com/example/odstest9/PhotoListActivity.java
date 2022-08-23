package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {

    GridView gridView;
    PhotoModel photoModel;
    PhotoAdapter photoAdapter;
    List<PhotoModel> photoModelList=new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        gridView=findViewById(R.id.photoList);
        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        photoAdapter=new PhotoAdapter(this,photoModelList);
        gridView.setAdapter(photoAdapter);

        loadPhotos();
    }

    private void loadPhotos() {
        progressDialog.show();
        progressDialog.setMessage("Loading...");

        firebaseFirestore.collection("ODSIMAGES").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PhotoListActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Toast.makeText(PhotoListActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    for(QueryDocumentSnapshot qb:task.getResult())
                    {
                        String name=qb.getString("fileName");
                        String url=qb.getString("photoUrl");
                        photoModel=new PhotoModel(name,url);
                        photoModelList.add(photoModel);
                        photoAdapter.notifyDataSetChanged();
                    }
                }


            }
        });
    }
}