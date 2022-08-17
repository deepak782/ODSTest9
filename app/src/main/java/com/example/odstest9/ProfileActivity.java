package com.example.odstest9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView name,mail;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount googleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage=findViewById(R.id.Image);
        name=findViewById(R.id.displayname);
        mail=findViewById(R.id.displaymail);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);

        if(googleSignInAccount!=null)
        {
           name.setText(""+googleSignInAccount.getDisplayName());
           mail.setText(""+googleSignInAccount.getEmail());
            Glide.with(getApplicationContext())
                    .load(googleSignInAccount.getPhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(profileImage);
        }

    }

    public void signout(View view) {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this,GoogleLoginActivity.class));
    }
}