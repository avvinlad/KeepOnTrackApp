package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homescreen extends AppCompatActivity {

    TextView nameTV;
    ImageView settingsButton, addHabit, refresh;
    ListView habitList;
    ArrayList<String> allHabits = new ArrayList<>();
    ArrayList<Habit> habits = new ArrayList<>();
    ArrayList<String> habitListView = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    String[] habits_split;
    Object habitsQuery;
    Habit selectedHabit;
    GoogleSignInAccount acct;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        nameTV = findViewById(R.id.name);
        settingsButton = findViewById(R.id.settings);
        addHabit = findViewById(R.id.addHabit);
        refresh = findViewById(R.id.refresh);


        acct = GoogleSignIn.getLastSignedInAccount(homescreen.this);
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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectedHabit = null;
                allHabits.clear();
                habits.clear();
                habitListView.clear();
                refreshData();
            }
        });

        retrieveData();
        addHabits();

        itemPressed();
    }

    private void itemPressed(){
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(!habitListView.isEmpty()) {
                    habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String title = habitListView.get(i);
                            for (Habit habit: habits){
                                if (habit.getTitle().equals(title)){
                                    break;
                                }
                            }
                        }
                    });
                }
                else
                    handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void retrieveData(){
        final String account = acct.getId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("habits");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                habitsQuery = dataSnapshot.child(account).getValue();
                if (habitsQuery != null) {
                    habits_split = habitsQuery.toString().split("[\\n\\t\\r,{}]");
                    for (String val : habits_split) {
                        String hasEqual = val;
                        if (hasEqual.indexOf("=") != -1) {
                            val = hasEqual.substring(val.indexOf("=") + 1);
                        }
                        if (!val.equals("")) {
                            allHabits.add(val);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });
    }

    private void addHabits(){
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(!allHabits.isEmpty()) {
                    for (String habit: allHabits){
                        String[] values = habit.split("[; ]");
                        if (!(values[1].equals("null")) && !(values[2].equals("MM/DD/YYYY"))){ habits.add(new Habit(values[0], values[1], values[2])); }
                        else if ((values[1].equals("null")) && !(values[2].equals("MM/DD/YYYY"))){ habits.add(new Habit(values[0], "", values[2])); }
                        else if (!(values[1].equals("null")) && (values[2].equals("MM/DD/YYYY"))){ habits.add(new Habit(values[0], values[1], "")); }
                        else if ((values[1].equals("null")) && (values[2].equals("MM/DD/YYYY"))){ habits.add(new Habit(values[0], "", "")); }
                    }
                    habitList = (ListView) findViewById(R.id.habitList);
                    printToList();
                }
                else
                    handler.postDelayed(this, delay);
            }
        }, delay);
    }


    private void refreshData(){
        retrieveData();
        addHabits();
    }

    private void printToList(){
        for(Habit curHabit: habits){
            habitListView.add(curHabit.getTitle());
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, habitListView);
        habitList.setAdapter(arrayAdapter);
    }

}