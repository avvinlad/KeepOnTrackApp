package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class homescreen extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    ImageView sign_out;
    TextView nameTV;
    TextView emailTV;
    TextView idTV;
    ImageView photoIV;
    ImageView settingsButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

//        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        settingsButton = findViewById(R.id.settings);
//        emailTV = findViewById(R.id.email);
//        idTV = findViewById(R.id.id);
        //photoIV = findViewById(R.id.photo);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(homescreen.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            ImageView img = (ImageView)findViewById(R.id.photo);

            // test
            nameTV.setText(personName.toUpperCase());
//            emailTV.setText("Email: "+personEmail);
//            idTV.setText("ID: "+personId);
            Glide.with(this).load(personPhoto).into(img);
        }

//        sign_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //settings_page();

                startActivity(new Intent(homescreen.this,Settings.class));
            }
        });


    }

//    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete( Task<Void> task) {
//                        Toast.makeText(homescreen.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(homescreen.this, MainActivity.class));
//                        finish();
//                    }
//                });
//    }

}