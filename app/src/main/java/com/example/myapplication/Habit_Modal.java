package com.example.myapplication;

import android.accounts.Account;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class Habit_Modal extends Activity {

    EditText title,desc,date;
    DatePickerDialog picker;
    Button cancel, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit__modal);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height* 0.7));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        date = findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Habit_Modal.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add = findViewById(R.id.add);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.date);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Habit_Modal.this);
                if (acct != null) {
                    String account = acct.getId();
                    String titleString = title.getText().toString();
                    String descString = desc.getText().toString();
                    String dateString = date.getText().toString();

                    if(titleString.equals("")){
                        Toast.makeText(Habit_Modal.this, "Must have a title", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (descString.equals("")){ descString = "null"; }
                        String value = titleString + ";" + descString + ";" + dateString;
                        addToDatabase(account, value, titleString);
                        finish();
                        startActivity(new Intent(Habit_Modal.this, homescreen.class));
                    }
                }
                else{
                    Toast.makeText(Habit_Modal.this, "Cannot Add to Database", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void addToDatabase(final String id, String habit, final String title){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("habits");
        myRef.child(id).child(title).setValue(habit);
        Toast.makeText(Habit_Modal.this, "Added to Database", Toast.LENGTH_LONG).show();
    }

}