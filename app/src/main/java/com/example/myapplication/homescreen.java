package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ImageButton addHabit;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        nameTV = findViewById(R.id.name);
        settingsButton = findViewById(R.id.settings);
        addHabit = findViewById(R.id.addHabit);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(homescreen.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();
            ImageView img = (ImageView)findViewById(R.id.photo);

            nameTV.setText(personName.toUpperCase());
            Glide.with(this).load(personPhoto).into(img);
        }

        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popUp = new Intent(getApplicationContext(), Habit_Modal.class);
                startActivity(popUp);
            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //settings_page();
                startActivity(new Intent(homescreen.this,Settings.class));
            }
        });

    }

}