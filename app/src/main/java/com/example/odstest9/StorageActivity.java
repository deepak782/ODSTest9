package com.example.odstest9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class StorageActivity extends AppCompatActivity {

    ImageView setImage;
    EditText editText;
    Uri uri;
    TextView path;
    String filenameStr;

    //Storage
    StorageReference storageReference;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;

    ProgressDialog progressDialog;

    //CloudFireStore
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        setImage=findViewById(R.id.setImage);
        path=findViewById(R.id.path);
        editText=findViewById(R.id.edt_filename);

        firebaseStorage=FirebaseStorage.getInstance();
        //Storage location Created in Firebase Storage
        storageReference=firebaseStorage.getReference("ODS");

        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        findViewById(R.id.chooseFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 pickImageFromGallery();
            }
        });

        findViewById(R.id.uploadFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadtoStorage();
            }
        });
    }

    private void uploadtoStorage() {
        progressDialog.show();
        progressDialog.setMessage("Image Uploading...");

        filenameStr=editText.getText().toString();
        StorageReference riversRef = storageReference.child(uri.getLastPathSegment());
        uploadTask = riversRef.putFile(uri);

     // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                progressDialog.dismiss();
                Toast.makeText(StorageActivity.this, ""+exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                progressDialog.dismiss();

                downloadURL(uploadTask,riversRef);
            }
        });
    }

    private void downloadURL(UploadTask uploadTask, StorageReference riversRef) {
        progressDialog.show();
        progressDialog.setMessage("downloading url..");

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                progressDialog.dismiss();
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String myurl=downloadUri.toString();
                    cloudFireStore(myurl);

                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(StorageActivity.this, "No url found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cloudFireStore(String myurl) {
        progressDialog.show();
        progressDialog.setMessage("Saving Image almost Done");

        Map<String,Object> map=new HashMap<>();
        map.put("photoUrl",myurl);
        map.put("fileName",filenameStr);
        map.put("refMail","deepu.kamal78@gmail.com");

        firebaseFirestore.collection("ODSIMAGES").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.dismiss();

                Toast.makeText(StorageActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(StorageActivity.this, "Failed to upload"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void pickImageFromGallery() {

        //image/,video/,audio/,application/pdf/,doc/
       /* Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,202);*/

        //or

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,202);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 202:
                if(resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
                {
                    uri=data.getData();
                    setImage.setImageURI(uri);
                    //Log.d("Name0",""+uri);

                    path.setText("Select Path is "+uri);
                   // getfileName(uri);
                }
                else
                {
                    Toast.makeText(this, "File not Choose", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void photolist(View view) {
        startActivity(new Intent(StorageActivity.this,PhotoListActivity.class));
    }


   /* @SuppressLint("Range")
    private void getfileName(Uri uri) {

        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.d("Name1",""+result);
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);

                Log.d("Name2",""+result);
            }
        }

    }*/

}