package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.Objects;
import java.util.Random;

public class RevisionTestsActivity extends AppCompatActivity {
    String userID, questionID;
    //Button buttonStart;
    TextView textViewQuestionRevision;
    EditText editTextRevision;
    Button buttonCorrectRevision,buttonWrongRevision,buttonSubmitMultipleChoiceRevision,buttonSubmitFillInTheGapRevision;
    RadioGroup radioGroupRevision;
    RadioButton radioButton1Revision, radioButton2Revision, radioButton3Revision, radioButton4Revision;

    Integer randomUnit, randomOperation, currentRevisionScore;
    ArrayList<String> selectedQuestions = new ArrayList<>();
    Boolean condition1;
    Integer count;
    Integer radiobuttonSelected = 0;
    Random random;


    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference unitRef, studentsRef, exercisesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_tests);
        //buttonStart = findViewById(R.id.buttonStart);
        textViewQuestionRevision = findViewById(R.id.textViewQuestionRevision);
        editTextRevision = findViewById(R.id.editTextAnswerRevision);
        buttonCorrectRevision = findViewById(R.id.buttonCorrectRevision);
        buttonWrongRevision = findViewById(R.id.buttonWrongRevision);
        buttonSubmitMultipleChoiceRevision = findViewById(R.id.buttonSubmitMultipleChoiceRevision);
        buttonSubmitFillInTheGapRevision = findViewById(R.id.buttonSubmitFillInTheGapRevision);
        radioGroupRevision = findViewById(R.id.radioGroupRevision);
        radioButton1Revision = findViewById(R.id.radioButton1Revision);
        radioButton2Revision = findViewById(R.id.radioButton2Revision);
        radioButton3Revision = findViewById(R.id.radioButton3Revision);
        radioButton4Revision = findViewById(R.id.radioButton4Revision);
        questionID = "0";
        currentRevisionScore = 0;


        //GetIntent
        //Recursion
        selectedQuestions = getIntent().getStringArrayListExtra("selectedQuestions");
        count = getIntent().getIntExtra("count", 1);

        random = new Random();

        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userID = getIntent().getStringExtra("userID");

        //Firebase Database
        database = FirebaseDatabase.getInstance();
        unitRef = FirebaseDatabase.getInstance().getReference().child("Exercises");



        //generateExercise();
        unitRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    int exerciseType;
                    do {
                        randomUnit = random.nextInt(10) + 1;
                        randomOperation = random.nextInt(10) + 1;

                        exerciseType = getExerciseType(count);

                        questionID = randomOperation.toString() + exerciseType;

                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                    }
                    while (condition1);
                    count++;

                    selectedQuestions.add(questionID);
                    textViewQuestionRevision.setText(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("question").getValue()).toString());

                    if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("type").getValue()).toString().equals("true or false")){
                        buttonCorrectRevision.setVisibility(View.VISIBLE);
                        buttonWrongRevision.setVisibility(View.VISIBLE);
                    }
                    else if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("type").getValue()).toString().equals("multiple choice")){
                        buttonSubmitMultipleChoiceRevision.setVisibility(View.VISIBLE);
                        radioGroupRevision.setVisibility(View.VISIBLE);

                        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
                        exercisesRef.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child("2").child("answers").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot radiobuttonSnapshot) {
                                radioButton1Revision.setText(radiobuttonSnapshot.child("1").getValue().toString());
                                radioButton2Revision.setText(radiobuttonSnapshot.child("2").getValue().toString());
                                radioButton3Revision.setText(radiobuttonSnapshot.child("3").getValue().toString());
                                radioButton4Revision.setText(radiobuttonSnapshot.child("4").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                // we are showing that error message in toast
                                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    else{
                        buttonSubmitFillInTheGapRevision.setVisibility(View.VISIBLE);
                        editTextRevision.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });



        radioButton1Revision.setOnClickListener(v -> {
            radiobuttonSelected = 1;
        });

        radioButton2Revision.setOnClickListener(v -> {
            radiobuttonSelected = 2;
        });

        radioButton3Revision.setOnClickListener(v -> {
            radiobuttonSelected = 3;
        });

        radioButton4Revision.setOnClickListener(v -> {
            radiobuttonSelected = 4;
        });
    }



    public boolean questionAlreadyChosen(ArrayList<String> list, String id){
        return list.contains(id);
    }

    public int getExerciseType(Integer index) {
        int questionType;

        if(index >= 1 && index <= 10){
            questionType = 1;
        }
        else if(index > 10 && index <= 20){
            questionType = 2;
        }
        else{
            questionType = 3;
        }

        return questionType;
    }



    public void buttonCorrectOnClick(View view){
        //
        // updating Students' Data
        //
        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
        exercisesRef.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child("1").child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot1)
            {
                if (dataSnapshot1.exists()) {

                    studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    studentsRef.child(userID).child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (dataSnapshot1.getValue().toString().equals("true")) {
                                currentRevisionScore = currentRevisionScore + 1;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // we are showing that error message in toast
                            Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        //
        //Intents
        //
        Intent intent = new Intent(getApplicationContext(), RevisionTestsActivity.class);
        intent.putExtra("userID", currentUser.getUid());

        //Intents for recursively calling RevisionTestsActivity
        intent.putExtra("selectedQuestions", selectedQuestions);
        intent.putExtra("count", count);
        startActivity(intent);
    }

    public void buttonFalseOnClick(View view){
        //
        // updating Students' Data
        //
        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
        exercisesRef.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child("1").child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot3)
            {
                if (dataSnapshot3.exists()) {

                    studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    studentsRef.child(userID).child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                            if (dataSnapshot3.getValue().toString().equals("false")) {
                                currentRevisionScore = currentRevisionScore + 1;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // we are showing that error message in toast
                            Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        //
        //Intents
        //
        Intent intent = new Intent(getApplicationContext(), RevisionTestsActivity.class);
        intent.putExtra("userID", currentUser.getUid());

        //Intents for recursively calling RevisionTestsActivity
        intent.putExtra("selectedQuestions", selectedQuestions);
        intent.putExtra("count", count);
        startActivity(intent);
    }

    public void buttonSubmitMultipleChoiceOnClick(View view){
        if(!radioButton1Revision.isChecked() && !radioButton2Revision.isChecked() && !radioButton3Revision.isChecked() && !radioButton4Revision.isChecked()) {
            Toast.makeText(this, "You must select an answer first!", Toast.LENGTH_LONG).show();
        }
        else {
            //
            // updating Students' Data
            //
            exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
            exercisesRef.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child("2").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot5) {
                    if (dataSnapshot5.exists()) {

                        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                        studentsRef.child(userID).child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot6) {
                                if (dataSnapshot5.child("answer").getValue().toString().equals(radiobuttonSelected.toString())) {
                                    currentRevisionScore = currentRevisionScore + 1;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // we are showing that error message in toast
                                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            //
            //Intents
            //
            Intent intent = new Intent(getApplicationContext(), RevisionTestsActivity.class);
            intent.putExtra("userID", currentUser.getUid());

            //Intents for recursively calling RevisionTestsActivity
            intent.putExtra("selectedQuestions", selectedQuestions);
            intent.putExtra("count", count);
            startActivity(intent);
        }
    }

    public void buttonSubmitFillInTheGapOnClick(View view){
        if (TextUtils.isEmpty(editTextRevision.getText().toString())) {
            Toast.makeText(this, "You must fill in the gap first!", Toast.LENGTH_LONG).show();
        }
        else {
            exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
            exercisesRef.child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).child("3").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot9)
                {
                    if (dataSnapshot9.exists()) {

                        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                        studentsRef.child(userID).child(String.valueOf(randomUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot10) {
                                if (dataSnapshot9.child("answer").getValue().toString().equals(editTextRevision.getText().toString())) {
                                    currentRevisionScore = currentRevisionScore + 1;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // we are showing that error message in toast
                                Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            //
            //Intents
            //
            if(count > 30) {
                studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot myDataSnapshot) {
                        if(currentRevisionScore > Integer.parseInt(myDataSnapshot.child("revisionTestScore").getValue().toString())){
                            studentsRef.child(userID).child("revisionTestScore").setValue(currentRevisionScore);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(RevisionTestsActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });


                //Go to MainActivity
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.putExtra("userID", currentUser.getUid());
                startActivity(intent2);
            }
            else {
                //Recursively calling RevisionTestsActivity
                Intent intent = new Intent(getApplicationContext(), RevisionTestsActivity.class);
                intent.putExtra("userID", currentUser.getUid());

                //Intents for recursively calling RevisionTestsActivity
                intent.putExtra("selectedQuestions", selectedQuestions);
                intent.putExtra("count", count);
                startActivity(intent);
            }
        }
    }
}