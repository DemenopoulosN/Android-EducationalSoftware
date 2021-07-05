package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class StudentsDataActivity extends AppCompatActivity {

    String studentID, studentName, studentEmail;
    TextView textViewUnit1Views, textViewUnit2Views, textViewUnit3Views, textViewUnit4Views, textViewUnit5Views, textViewUnit6Views, textViewUnit7Views, textViewUnit8Views, textViewUnit9Views, textViewUnit10Views;
    TextView textViewProblemsScore, textViewRevisionTestScore, textViewTotalAdditionFaults;
    ImageView imageViewUnit1Score, imageViewUnit2Score, imageViewUnit3Score, imageViewUnit4Score, imageViewUnit5Score, imageViewUnit6Score, imageViewUnit7Score, imageViewUnit8Score, imageViewUnit9Score, imageViewUnit10Score;
    Integer[] totalUnitsScoresArray = new Integer[]{0,0,0,0,0,0,0,0,0,0};
    Integer[] viewsArray = new Integer[]{0,0,0,0,0,0,0,0,0,0};
    Integer score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    Integer problemsScore, revisionTestScore, totalAdditionFaults;
    ImageView imageViewInfoTeacher2;

    //Firebase Database
    DatabaseReference studentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_data);

        //TextView
        textViewUnit1Views = findViewById(R.id.textViewUnit1Views);
        textViewUnit2Views = findViewById(R.id.textViewUnit2Views);
        textViewUnit3Views = findViewById(R.id.textViewUnit3Views);
        textViewUnit4Views = findViewById(R.id.textViewUnit4Views);
        textViewUnit5Views = findViewById(R.id.textViewUnit5Views);
        textViewUnit6Views = findViewById(R.id.textViewUnit6Views);
        textViewUnit7Views = findViewById(R.id.textViewUnit7Views);
        textViewUnit8Views = findViewById(R.id.textViewUnit8Views);
        textViewUnit9Views = findViewById(R.id.textViewUnit9Views);
        textViewUnit10Views = findViewById(R.id.textViewUnit10Views);
        //ImageView
        imageViewUnit1Score = findViewById(R.id.imageViewUnit1Score);
        imageViewUnit2Score = findViewById(R.id.imageViewUnit2Score);
        imageViewUnit3Score = findViewById(R.id.imageViewUnit3Score);
        imageViewUnit4Score = findViewById(R.id.imageViewUnit4Score);
        imageViewUnit5Score = findViewById(R.id.imageViewUnit5Score);
        imageViewUnit6Score = findViewById(R.id.imageViewUnit6Score);
        imageViewUnit7Score = findViewById(R.id.imageViewUnit7Score);
        imageViewUnit8Score = findViewById(R.id.imageViewUnit8Score);
        imageViewUnit9Score = findViewById(R.id.imageViewUnit9Score);
        imageViewUnit10Score = findViewById(R.id.imageViewUnit10Score);
        //TextView
        textViewProblemsScore = findViewById(R.id.textViewProblemsScore);
        textViewRevisionTestScore = findViewById(R.id.textViewRevisionTestScore);
        textViewTotalAdditionFaults = findViewById(R.id.textViewTotalAdditionFaults);

        imageViewInfoTeacher2 = findViewById(R.id.imageViewInfoTeacher2);


        //GetIntent
        studentID = getIntent().getStringExtra("studentID");
        studentName = getIntent().getStringExtra("studentName");
        studentEmail = getIntent().getStringExtra("studentEmail");

        //Toast.makeText(getApplicationContext(), studentID + "\n" + studentName + "\n" + studentEmail, Toast.LENGTH_LONG).show();





        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(int i = 0; i <= 9; i++){
                    totalUnitsScoresArray[i] = Integer.parseInt(dataSnapshot.child(String.valueOf(i + 1)).child("unitScore").getValue().toString());
                    viewsArray[i] = Integer.parseInt(dataSnapshot.child(String.valueOf(i + 1)).child("views").getValue().toString());
                }

                //unit1
                textViewUnit1Views.setText(viewsArray[0].toString());
                score1 = totalUnitsScoresArray[0];
                if(score1 == 0){
                    imageViewUnit1Score.setImageResource(R.drawable.stars0);
                }
                else if(score1 == 1){
                    imageViewUnit1Score.setImageResource(R.drawable.stars1);
                }
                else if(score1 == 2){
                    imageViewUnit1Score.setImageResource(R.drawable.stars2);
                }
                else if(score1 == 3){
                    imageViewUnit1Score.setImageResource(R.drawable.stars3);
                }
                else if(score1 == 4){
                    imageViewUnit1Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit1Score.setImageResource(R.drawable.stars5);
                }

                //unit2
                textViewUnit2Views.setText(viewsArray[1].toString());
                score2 = totalUnitsScoresArray[1];
                if(score2 == 0){
                    imageViewUnit2Score.setImageResource(R.drawable.stars0);
                }
                else if(score2 == 1){
                    imageViewUnit2Score.setImageResource(R.drawable.stars1);
                }
                else if(score2 == 2){
                    imageViewUnit2Score.setImageResource(R.drawable.stars2);
                }
                else if(score2 == 3){
                    imageViewUnit2Score.setImageResource(R.drawable.stars3);
                }
                else if(score2 == 4){
                    imageViewUnit2Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit2Score.setImageResource(R.drawable.stars5);
                }

                //unit3
                textViewUnit3Views.setText(viewsArray[2].toString());
                score3 = totalUnitsScoresArray[2];
                if(score3 == 0){
                    imageViewUnit3Score.setImageResource(R.drawable.stars0);
                }
                else if(score3 == 1){
                    imageViewUnit3Score.setImageResource(R.drawable.stars1);
                }
                else if(score3 == 2){
                    imageViewUnit3Score.setImageResource(R.drawable.stars2);
                }
                else if(score3 == 3){
                    imageViewUnit3Score.setImageResource(R.drawable.stars3);
                }
                else if(score3 == 4){
                    imageViewUnit3Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit3Score.setImageResource(R.drawable.stars5);
                }

                //unit4
                textViewUnit4Views.setText(viewsArray[3].toString());
                score4 = totalUnitsScoresArray[3];
                if(score4 == 0){
                    imageViewUnit4Score.setImageResource(R.drawable.stars0);
                }
                else if(score4 == 1){
                    imageViewUnit4Score.setImageResource(R.drawable.stars1);
                }
                else if(score4 == 2){
                    imageViewUnit4Score.setImageResource(R.drawable.stars2);
                }
                else if(score4 == 3){
                    imageViewUnit4Score.setImageResource(R.drawable.stars3);
                }
                else if(score4 == 4){
                    imageViewUnit4Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit4Score.setImageResource(R.drawable.stars5);
                }

                //unit5
                textViewUnit5Views.setText(viewsArray[4].toString());
                score5 = totalUnitsScoresArray[4];
                if(score5 == 0){
                    imageViewUnit5Score.setImageResource(R.drawable.stars0);
                }
                else if(score5 == 1){
                    imageViewUnit5Score.setImageResource(R.drawable.stars1);
                }
                else if(score5 == 2){
                    imageViewUnit5Score.setImageResource(R.drawable.stars2);
                }
                else if(score5 == 3){
                    imageViewUnit5Score.setImageResource(R.drawable.stars3);
                }
                else if(score5 == 4){
                    imageViewUnit5Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit5Score.setImageResource(R.drawable.stars5);
                }

                //unit6
                textViewUnit6Views.setText(viewsArray[5].toString());
                score6 = totalUnitsScoresArray[5];
                if(score6 == 0){
                    imageViewUnit6Score.setImageResource(R.drawable.stars0);
                }
                else if(score6 == 1){
                    imageViewUnit6Score.setImageResource(R.drawable.stars1);
                }
                else if(score6 == 2){
                    imageViewUnit6Score.setImageResource(R.drawable.stars2);
                }
                else if(score6 == 3){
                    imageViewUnit6Score.setImageResource(R.drawable.stars3);
                }
                else if(score6 == 4){
                    imageViewUnit6Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit6Score.setImageResource(R.drawable.stars5);
                }

                //unit7
                textViewUnit7Views.setText(viewsArray[6].toString());
                score7 = totalUnitsScoresArray[6];
                if(score7 == 0){
                    imageViewUnit7Score.setImageResource(R.drawable.stars0);
                }
                else if(score7 == 1){
                    imageViewUnit7Score.setImageResource(R.drawable.stars1);
                }
                else if(score7 == 2){
                    imageViewUnit7Score.setImageResource(R.drawable.stars2);
                }
                else if(score7 == 3){
                    imageViewUnit7Score.setImageResource(R.drawable.stars3);
                }
                else if(score7 == 4){
                    imageViewUnit7Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit7Score.setImageResource(R.drawable.stars5);
                }

                //unit8
                textViewUnit8Views.setText(viewsArray[7].toString());
                score8 = totalUnitsScoresArray[7];
                if(score8 == 0){
                    imageViewUnit8Score.setImageResource(R.drawable.stars0);
                }
                else if(score8 == 1){
                    imageViewUnit8Score.setImageResource(R.drawable.stars1);
                }
                else if(score8 == 2){
                    imageViewUnit8Score.setImageResource(R.drawable.stars2);
                }
                else if(score8 == 3){
                    imageViewUnit8Score.setImageResource(R.drawable.stars3);
                }
                else if(score8 == 4){
                    imageViewUnit8Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit8Score.setImageResource(R.drawable.stars5);
                }

                //unit9
                textViewUnit9Views.setText(viewsArray[8].toString());
                score9 = totalUnitsScoresArray[8];
                if(score9 == 0){
                    imageViewUnit9Score.setImageResource(R.drawable.stars0);
                }
                else if(score9 == 1){
                    imageViewUnit9Score.setImageResource(R.drawable.stars1);
                }
                else if(score9 == 2){
                    imageViewUnit9Score.setImageResource(R.drawable.stars2);
                }
                else if(score9 == 3){
                    imageViewUnit9Score.setImageResource(R.drawable.stars3);
                }
                else if(score9 == 4){
                    imageViewUnit9Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit9Score.setImageResource(R.drawable.stars5);
                }

                //unit10
                textViewUnit10Views.setText(viewsArray[9].toString());
                score10 = totalUnitsScoresArray[9];
                if(score10 == 0){
                    imageViewUnit10Score.setImageResource(R.drawable.stars0);
                }
                else if(score10 == 1){
                    imageViewUnit10Score.setImageResource(R.drawable.stars1);
                }
                else if(score10 == 2){
                    imageViewUnit10Score.setImageResource(R.drawable.stars2);
                }
                else if(score10 == 3){
                    imageViewUnit10Score.setImageResource(R.drawable.stars3);
                }
                else if(score10 == 4){
                    imageViewUnit10Score.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit10Score.setImageResource(R.drawable.stars5);
                }

                problemsScore = Integer.parseInt(dataSnapshot.child("problemsScore").getValue().toString());
                textViewProblemsScore.setText(String.valueOf(problemsScore));

                revisionTestScore = Integer.parseInt(dataSnapshot.child("revisionTestScore").getValue().toString());
                textViewRevisionTestScore.setText(String.valueOf(revisionTestScore));

                totalAdditionFaults = Integer.parseInt(dataSnapshot.child("totalAdditionFaults").getValue().toString());
                textViewTotalAdditionFaults.setText(String.valueOf(totalAdditionFaults));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // we are showing that error message in toast
                Toast.makeText(StudentsDataActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });


    }


    public void infoTeacher2(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Καλησπέρα!","Σε αυτή την οθόνη βλέπεις όλα τα στατιστικά στοιχεία του μαθητή που επέλεξες.");
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