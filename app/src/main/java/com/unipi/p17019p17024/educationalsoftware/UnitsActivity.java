package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UnitsActivity extends AppCompatActivity {
    String userID;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9, imageButton10;
    Integer selectedUnit = 0;
    ImageView imageView;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);
        imageButton1 = findViewById(R.id.imageButtonUnit1);
        imageButton2 = findViewById(R.id.imageButtonUnit2);
        imageButton3 = findViewById(R.id.imageButtonUnit3);
        imageButton4 = findViewById(R.id.imageButtonUnit4);
        imageButton5 = findViewById(R.id.imageButtonUnit5);
        imageButton6 = findViewById(R.id.imageButtonUnit6);
        imageButton7 = findViewById(R.id.imageButtonUnit7);
        imageButton8 = findViewById(R.id.imageButtonUnit8);
        imageButton9 = findViewById(R.id.imageButtonUnit9);
        imageButton10 = findViewById(R.id.imageButtonUnit10);
        imageView = findViewById(R.id.imageViewInfoUnits);

        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userID = getIntent().getStringExtra("userID");


        //Firebase Database
        database = FirebaseDatabase.getInstance();


        //
        //προαπαιτούμενο να έχει ολοκληρωθεί επιτυχώς η προηγούμενη ενότητα για να ανοίξει η επόμενη (θεωρία + καλό σκορ)
        //
        imageButton1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 1;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 2;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 3;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 4;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton5.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 5;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton6.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 6;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton7.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 7;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton8.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 8;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton9.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 9;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });

        imageButton10.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 10;
            intent.putExtra("selectedUnit", selectedUnit);
            startActivity(intent);
        });
    }
}