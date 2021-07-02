package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class TeacherActivity extends AppCompatActivity {
    String userID, email, username;

    //TextView
    TextView textViewTeacherTitle, textViewTeacherEmptyTitle;

    //Firebase Database
    //FirebaseDatabase database;
    DatabaseReference studentsRef;

    //RecyclerView
    private RecyclerView recyclerView;
    TeacherAdapter adapter; // Create Object of the Adapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        textViewTeacherTitle = findViewById(R.id.textViewTeacherTitle);
        textViewTeacherEmptyTitle = findViewById(R.id.textViewTeacherEmptyTitle);

        //GetIntent
        userID = getIntent().getStringExtra("userID");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");

        //database Firebase
        //database = FirebaseDatabase.getInstance().getReference();

        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userID);

        recyclerView = findViewById(R.id.studentsRecyclerList);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Students> options
                = new FirebaseRecyclerOptions.Builder<Students>()
                .setQuery(studentsRef, Students.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new TeacherAdapter(options);
        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);


        //
        //If the user has items in favorites: Visibility of buttons, textViews etc.
        //
        //userIDRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(userID);

        //userIDRef.addValueEventListener(new ValueEventListener() {

        //
        //If there are students in database: Visibility of buttons, textViews etc.
        //
        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mySnapshot) {
                if (mySnapshot.exists()){
                    textViewTeacherTitle.setVisibility(View.VISIBLE);
                    textViewTeacherEmptyTitle.setVisibility(View.INVISIBLE);
                    //imageView2.setVisibility(View.INVISIBLE);
                }
                else {
                    textViewTeacherTitle.setVisibility(View.INVISIBLE);
                    textViewTeacherEmptyTitle.setVisibility(View.VISIBLE);
                    //imageView2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



}