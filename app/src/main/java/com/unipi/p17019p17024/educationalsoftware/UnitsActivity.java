package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
                    for(int j = 1; j <= 10; j++){
                        totalUnitsScoresArray[i] = totalUnitsScoresArray[i] + Double.parseDouble(dataSnapshot.child(String.valueOf(i + 1)).child(String.valueOf(j)).child("score").getValue().toString());
                    }
                    totalUnitsScoresArray[i] = totalUnitsScoresArray[i]/10;
                    //Update Firebase
                    studentsRef.child(userID).child(String.valueOf(i + 1)).child("unitScore").setValue((int) Math.round(totalUnitsScoresArray[i]));
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
                    imageButton2.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars2.setImageResource(R.drawable.stars5);
                    imageButton2.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton3.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars3.setImageResource(R.drawable.stars5);
                    imageButton3.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton4.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars4.setImageResource(R.drawable.stars5);
                    imageButton4.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton5.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars5.setImageResource(R.drawable.stars5);
                    imageButton5.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton6.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars6.setImageResource(R.drawable.stars5);
                    imageButton6.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton7.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars7.setImageResource(R.drawable.stars5);
                    imageButton7.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton8.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars8.setImageResource(R.drawable.stars5);
                    imageButton8.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton9.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars9.setImageResource(R.drawable.stars5);
                    imageButton9.setBackgroundColor(Color.parseColor("#068A0B"));
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
                    imageButton10.setBackgroundColor(Color.parseColor("#068A0B"));
                }
                else{
                    imageViewStars10.setImageResource(R.drawable.stars5);
                    imageButton10.setBackgroundColor(Color.parseColor("#068A0B"));
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
            studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
            studentsRef.child(userID).child("1").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot1) {
                    Integer unit1Views = Integer.parseInt(dataSnapshot1.getValue().toString()) +1;
                    studentsRef.child(userID).child("1").child("views").setValue(unit1Views);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    // we are showing that error message in toast
                    Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("2").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot2) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot2.getValue().toString()) +1;
                        studentsRef.child(userID).child("2").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("3").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot3) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot3.getValue().toString()) +1;
                        studentsRef.child(userID).child("3").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("4").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot4) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot4.getValue().toString()) +1;
                        studentsRef.child(userID).child("4").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("5").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot5) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot5.getValue().toString()) +1;
                        studentsRef.child(userID).child("5").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("6").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot6) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot6.getValue().toString()) +1;
                        studentsRef.child(userID).child("6").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });



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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("7").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot7) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot7.getValue().toString()) +1;
                        studentsRef.child(userID).child("7").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("8").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot8) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot8.getValue().toString()) +1;
                        studentsRef.child(userID).child("8").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("9").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot9) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot9.getValue().toString()) +1;
                        studentsRef.child(userID).child("9").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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
                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child("10").child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot10) {
                        Integer unit1Views = Integer.parseInt(dataSnapshot10.getValue().toString()) +1;
                        studentsRef.child(userID).child("10").child("views").setValue(unit1Views);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(UnitsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


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

    public void infoUnits(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Γειά σου!","Βρίσκεσαι στην οθόνη των ενοτήτων. Πατώντας το κάθε κουμπί μπορείς να μεταβείς στην θεωρία της προπαίδειας του κάθε αριθμού. Προσοχή όμως! Για να πας στην επόμενη ενότητα κάθε φορά, πρέπει να έχεις ένα καλό σκορ στην προηγούμενη ενότητα. Τα αστέρια κάτω από κάθε ενότητα θα σε βοηθήσουν να βλέπεις πάντα την απόδοσή σου για κάθε μία από τις ενότητες.");
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