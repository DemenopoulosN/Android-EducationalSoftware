package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherActivity extends AppCompatActivity {
    String userID, email;

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

        //GetIntent
        userID = getIntent().getStringExtra("userID");
        email = getIntent().getStringExtra("email");

        //database Firebase
        //database = FirebaseDatabase.getInstance().getReference();

        studentsRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(userID);

        recyclerView = findViewById(R.id.studentsRecyclerList);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        /*FirebaseRecyclerOptions<Favorites> options
                = new FirebaseRecyclerOptions.Builder<Favorites>()
                .setQuery(studentsRef, Favorites.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new TeacherAdapter(options);
        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);*/


        //
        //If the user has items in favorites: Visibility of buttons, textViews etc.
        //
        //userIDRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(userID);

        //userIDRef.addValueEventListener(new ValueEventListener() {
        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mySnapshot) {
                if (mySnapshot.exists()){
                    //textViewFavoritesTitle.setVisibility(View.VISIBLE);
                    //textView4.setVisibility(View.INVISIBLE);
                    //imageView2.setVisibility(View.INVISIBLE);
                }
                else {
                    //textViewFavoritesTitle.setVisibility(View.INVISIBLE);
                    //textView4.setVisibility(View.VISIBLE);
                    //imageView2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}