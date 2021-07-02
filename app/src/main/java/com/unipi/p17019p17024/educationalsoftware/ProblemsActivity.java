package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Random;

public class ProblemsActivity extends AppCompatActivity {
    Button buttonSubmit;
    EditText editTextProblem1, editTextProblem2;
    String userID;
    Integer currentProblemsScore;
    ImageView imageViewInfoProblems;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference studentsRef, exercisesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        imageViewInfoProblems = findViewById(R.id.imageViewInfoProblems);
        editTextProblem1 = findViewById(R.id.editTextNumberProblem1);
        editTextProblem2 = findViewById(R.id.editTextNumberProblem2);
        currentProblemsScore = 0;


        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userID = getIntent().getStringExtra("userID");

        //Firebase Database
        database = FirebaseDatabase.getInstance();

    }

    public void submitAnswers(View view){
        if(TextUtils.isEmpty(editTextProblem1.getText().toString()) || TextUtils.isEmpty(editTextProblem2.getText().toString())){
            Toast.makeText(this,"You should answer both problems before submitting!", Toast.LENGTH_LONG).show();
        }
        else{
            exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
            exercisesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("problem1").child("answer").getValue().toString().equals(editTextProblem1.getText().toString()) && dataSnapshot.child("problem2").child("answer").getValue().toString().equals(editTextProblem2.getText().toString())){
                        currentProblemsScore = 2;
                    }
                    else if(dataSnapshot.child("problem1").child("answer").getValue().toString().equals(editTextProblem1.getText().toString()) || dataSnapshot.child("problem2").child("answer").getValue().toString().equals(editTextProblem2.getText().toString())){
                        currentProblemsScore = 1;
                    }
                    else{
                        currentProblemsScore = 0;
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    // we are showing that error message in toast
                    Toast.makeText(ProblemsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
            studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot2) {
                    if(currentProblemsScore > Integer.parseInt(dataSnapshot2.child("problemsScore").getValue().toString())) {
                        studentsRef.child(userID).child("problemsScore").setValue(currentProblemsScore);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    // we are showing that error message in toast
                    Toast.makeText(ProblemsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            //Go to MainActivity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            startActivity(intent);
        }
    }

    public void infoProblems(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Γειά σου!","Σε αυτή τη οθόνη βρίσκονται δύο επαναληπτικά προβλήματα για να δείς τι έμαθες με πιο διασκεδαστικό τρόπο! Προσπάθησε να απαντήσεις σωστά συμπληρώνοντας την απάντησή σου στο κενό! Καλό παιχνίδι!");
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