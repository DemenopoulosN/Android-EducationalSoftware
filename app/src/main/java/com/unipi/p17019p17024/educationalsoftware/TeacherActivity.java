package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    //String userID, email, username;

    //TextView
    TextView textViewTeacherTitle, textViewTeacherEmptyTitle;
    ImageView imageViewInfoTeacher1;

    //Firebase Database
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
        imageViewInfoTeacher1 = findViewById(R.id.imageViewInfoTeacher1);

        //GetIntent
        //userID = getIntent().getStringExtra("userID");
        //email = getIntent().getStringExtra("email");
        //username = getIntent().getStringExtra("username");

        //Firebase Database
        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");

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
        //adapter = new TeacherAdapter(options);
        // adapter = new TeacherAdapter(TeacherActivity.this, options);
        adapter = new TeacherAdapter(this, options);
        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);


        //
        //If there are students in database: Visibility of textViews etc.
        //
        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mySnapshot) {
                if (mySnapshot.hasChildren()) {
                    textViewTeacherTitle.setVisibility(View.VISIBLE);
                    textViewTeacherEmptyTitle.setVisibility(View.INVISIBLE);
                } else {
                    textViewTeacherTitle.setVisibility(View.INVISIBLE);
                    textViewTeacherEmptyTitle.setVisibility(View.VISIBLE);
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


    public void infoTeacher1(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Καλησπέρα!","Σε αυτή την οθόνη μπορείς να δεις μια λίστα με τους εγγεγραμμένους στην εφαρμογή μαθητές και να επιλέξεις όποιον επιθυμείς από αυτούς για να μεταβείς στην επόμενη οθόνη όπου θα εμφανίζονται τα στατιστικά και τα σκορ του μαθητή που επέλεξες.");
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.application_photo_round)
                .setPositiveButton("Ok", (dialog, which) -> {
                    //do nothing
                })
                .show();
    }



}