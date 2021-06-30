package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;

public class UnitsActivity extends AppCompatActivity {
    String userID, email, difficulty;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9, imageButton10;
    Integer selectedUnit = 0;
    ImageView imageView;
    ImageView imageViewStars1, imageViewStars2, imageViewStars3, imageViewStars4, imageViewStars5, imageViewStars6, imageViewStars7, imageViewStars8, imageViewStars9, imageViewStars10;

    Double[] totalUnitsScoresArray = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    //Integer totalScoreUnit1, totalScoreUnit2, totalScoreUnit3, totalScoreUnit4, totalScoreUnit5, totalScoreUnit6, totalScoreUnit7, totalScoreUnit8, totalScoreUnit9, totalScoreUnit10;
    int score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference myRef, studentsRef;

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
        imageViewStars1 =findViewById(R.id.imageViewUnit1Stars);
        imageViewStars2 =findViewById(R.id.imageViewUnit2Stars);
        imageViewStars3 =findViewById(R.id.imageViewUnit3Stars);
        imageViewStars4 =findViewById(R.id.imageViewUnit4Stars);
        imageViewStars5 =findViewById(R.id.imageViewUnit5Stars);
        imageViewStars6 =findViewById(R.id.imageViewUnit6Stars);
        imageViewStars7 =findViewById(R.id.imageViewUnit7Stars);
        imageViewStars8 =findViewById(R.id.imageViewUnit8Stars);
        imageViewStars9 =findViewById(R.id.imageViewUnit9Stars);
        imageViewStars10 =findViewById(R.id.imageViewUnit10Stars);

        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //GetIntent
        userID = getIntent().getStringExtra("userID");
        difficulty = getIntent().getStringExtra("difficulty");
        email = getIntent().getStringExtra("email");


        //Firebase Database
        database = FirebaseDatabase.getInstance();


        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(int i = 0; i <= 9; i++){
                    for(int j = 1; j<= 10; j++){
                        totalUnitsScoresArray[i] = totalUnitsScoresArray[i] + Double.parseDouble(dataSnapshot.child(String.valueOf(i + 1)).child(String.valueOf(j)).child("score").getValue().toString());
                    }
                    totalUnitsScoresArray[i] = totalUnitsScoresArray[i]/10;
                }

                //unit1
                score1 = (int) Math.round(totalUnitsScoresArray[0]);
                if(score1 == 0){
                    imageViewStars1.setImageResource(R.drawable.stars0);
                }
                else if(score1 == 1){
                    imageViewStars1.setImageResource(R.drawable.stars1);
                }
                else if(score1 == 2){
                    imageViewStars1.setImageResource(R.drawable.stars2);
                }
                else if(score1 == 3){
                    imageViewStars1.setImageResource(R.drawable.stars3);
                }
                else if(score1 == 4){
                    imageViewStars1.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars1.setImageResource(R.drawable.stars5);
                }

                //unit2
                score2 = (int) Math.round(totalUnitsScoresArray[1]);
                if(score2 == 0){
                    imageViewStars2.setImageResource(R.drawable.stars0);
                }
                else if(score2 == 1){
                    imageViewStars2.setImageResource(R.drawable.stars1);
                }
                else if(score2 == 2){
                    imageViewStars2.setImageResource(R.drawable.stars2);
                }
                else if(score2 == 3){
                    imageViewStars2.setImageResource(R.drawable.stars3);
                }
                else if(score2 == 4){
                    imageViewStars2.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars2.setImageResource(R.drawable.stars5);
                }

                //unit3
                score3 = (int) Math.round(totalUnitsScoresArray[2]);
                if(score3 == 0){
                    imageViewStars3.setImageResource(R.drawable.stars0);
                }
                else if(score3 == 1){
                    imageViewStars3.setImageResource(R.drawable.stars1);
                }
                else if(score3 == 2){
                    imageViewStars3.setImageResource(R.drawable.stars2);
                }
                else if(score3 == 3){
                    imageViewStars3.setImageResource(R.drawable.stars3);
                }
                else if(score3 == 4){
                    imageViewStars3.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars3.setImageResource(R.drawable.stars5);
                }

                //unit4
                score4 = (int) Math.round(totalUnitsScoresArray[3]);
                if(score4 == 0){
                    imageViewStars4.setImageResource(R.drawable.stars0);
                }
                else if(score4 == 1){
                    imageViewStars4.setImageResource(R.drawable.stars1);
                }
                else if(score4 == 2){
                    imageViewStars4.setImageResource(R.drawable.stars2);
                }
                else if(score4 == 3){
                    imageViewStars4.setImageResource(R.drawable.stars3);
                }
                else if(score4 == 4){
                    imageViewStars4.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars4.setImageResource(R.drawable.stars5);
                }

                //unit5
                score5 = (int) Math.round(totalUnitsScoresArray[4]);
                if(score5 == 0){
                    imageViewStars5.setImageResource(R.drawable.stars0);
                }
                else if(score5 == 1){
                    imageViewStars5.setImageResource(R.drawable.stars1);
                }
                else if(score5 == 2){
                    imageViewStars5.setImageResource(R.drawable.stars2);
                }
                else if(score5 == 3){
                    imageViewStars5.setImageResource(R.drawable.stars3);
                }
                else if(score5 == 4){
                    imageViewStars5.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars5.setImageResource(R.drawable.stars5);
                }

                //unit6
                score6 = (int) Math.round(totalUnitsScoresArray[5]);
                if(score6 == 0){
                    imageViewStars6.setImageResource(R.drawable.stars0);
                }
                else if(score6 == 1){
                    imageViewStars6.setImageResource(R.drawable.stars1);
                }
                else if(score6 == 2){
                    imageViewStars6.setImageResource(R.drawable.stars2);
                }
                else if(score6 == 3){
                    imageViewStars6.setImageResource(R.drawable.stars3);
                }
                else if(score6 == 4){
                    imageViewStars6.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars6.setImageResource(R.drawable.stars5);
                }

                //unit7
                score7 = (int) Math.round(totalUnitsScoresArray[6]);
                if(score7 == 0){
                    imageViewStars7.setImageResource(R.drawable.stars0);
                }
                else if(score7 == 1){
                    imageViewStars7.setImageResource(R.drawable.stars1);
                }
                else if(score7 == 2){
                    imageViewStars7.setImageResource(R.drawable.stars2);
                }
                else if(score7 == 3){
                    imageViewStars7.setImageResource(R.drawable.stars3);
                }
                else if(score7 == 4){
                    imageViewStars7.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars7.setImageResource(R.drawable.stars5);
                }

                //unit8
                score8 = (int) Math.round(totalUnitsScoresArray[7]);
                if(score8 == 0){
                    imageViewStars8.setImageResource(R.drawable.stars0);
                }
                else if(score8 == 1){
                    imageViewStars8.setImageResource(R.drawable.stars1);
                }
                else if(score8 == 2){
                    imageViewStars8.setImageResource(R.drawable.stars2);
                }
                else if(score8 == 3){
                    imageViewStars8.setImageResource(R.drawable.stars3);
                }
                else if(score8 == 4){
                    imageViewStars8.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars8.setImageResource(R.drawable.stars5);
                }

                //unit9
                score9 = (int) Math.round(totalUnitsScoresArray[8]);
                if(score9 == 0){
                    imageViewStars9.setImageResource(R.drawable.stars0);
                }
                else if(score9 == 1){
                    imageViewStars9.setImageResource(R.drawable.stars1);
                }
                else if(score9 == 2){
                    imageViewStars9.setImageResource(R.drawable.stars2);
                }
                else if(score9 == 3){
                    imageViewStars9.setImageResource(R.drawable.stars3);
                }
                else if(score9 == 4){
                    imageViewStars9.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars9.setImageResource(R.drawable.stars5);
                }

                //unit10
                score10 = (int) Math.round(totalUnitsScoresArray[9]);
                if(score10 == 0){
                    imageViewStars10.setImageResource(R.drawable.stars0);
                }
                else if(score10 == 1){
                    imageViewStars10.setImageResource(R.drawable.stars1);
                }
                else if(score10 == 2){
                    imageViewStars10.setImageResource(R.drawable.stars2);
                }
                else if(score10 == 3){
                    imageViewStars10.setImageResource(R.drawable.stars3);
                }
                else if(score10 == 4){
                    imageViewStars10.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars10.setImageResource(R.drawable.stars5);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // we are showing that error message in toast
                Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        //TO-DO
        //Change background color of imageButtons
        //green or red

        //
        //προαπαιτούμενο να έχει ολοκληρωθεί επιτυχώς η προηγούμενη ενότητα για να ανοίξει η επόμενη (θεωρία + καλό σκορ)
        //
        imageButton1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            selectedUnit = 1;
            intent.putExtra("selectedUnit", selectedUnit);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        imageButton2.setOnClickListener(v -> {
            if (score1 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 2;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 1\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton3.setOnClickListener(v -> {
            if (score2 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 3;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 2\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton4.setOnClickListener(v -> {
            if (score3 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 4;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 3\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton5.setOnClickListener(v -> {
            if (score4 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 5;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 4\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton6.setOnClickListener(v -> {
            if (score5 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 6;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 5\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton7.setOnClickListener(v -> {
            if (score6 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 7;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 6\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton8.setOnClickListener(v -> {
            if (score7 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 8;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 7\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton9.setOnClickListener(v -> {
            if (score8 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 9;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 8\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });

        imageButton10.setOnClickListener(v -> {
            if (score9 >= 4) {
                Intent intent = new Intent(getApplicationContext(), TheoryActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                selectedUnit = 10;
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("email", email);
                startActivity(intent);
            }
            else {
                Toast.makeText(UnitsActivity.this, "You have to finish unit 9\nwith a score of at least 4/5!", Toast.LENGTH_LONG).show();
            }
        });
    }
}