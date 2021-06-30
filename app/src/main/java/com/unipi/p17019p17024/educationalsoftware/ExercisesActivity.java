package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ExercisesActivity extends AppCompatActivity {
    String userID, email, questionID, difficulty;
    TextView textView1;
    EditText editText1;
    Button button1,button2,button3,button4;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    ImageView imageView;
    Integer selectedUnit, randomOperation;
    ArrayList<String> selectedQuestions = new ArrayList<>();
    Boolean condition1, condition3; //, condition2
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
        setContentView(R.layout.activity_exercises);
        textView1 = findViewById(R.id.textViewQuestion);
        editText1 = findViewById(R.id.editTextAnswer);
        button1 = findViewById(R.id.buttonCorrect);
        button2 = findViewById(R.id.buttonWrong);
        button3 = findViewById(R.id.buttonSubmitMultipleChoice);
        button4 = findViewById(R.id.buttonSubmitFillInTheGap);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        imageView = findViewById(R.id.imageViewInfoExercises);
        questionID = "0";

        //GetIntent
        //Recursion
        selectedQuestions = getIntent().getStringArrayListExtra("selectedQuestions");
        count = getIntent().getIntExtra("count", 1);
        email = getIntent().getStringExtra("email");


        random = new Random();

        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userID = getIntent().getStringExtra("userID");
        selectedUnit = getIntent().getIntExtra("selectedUnit", 0);
        difficulty = getIntent().getStringExtra("difficulty");
        Toast.makeText(getApplicationContext(), "unit: "+ selectedUnit, Toast.LENGTH_LONG).show();

        //Firebase Database
        database = FirebaseDatabase.getInstance();
        unitRef = FirebaseDatabase.getInstance().getReference().child("Exercises").child(String.valueOf(selectedUnit));


        //generateExercise();
        unitRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    int exerciseType;
                    do {
                        randomOperation = random.nextInt(10) + 1;

                        exerciseType = getExerciseType(difficulty, count);

                        questionID = randomOperation.toString() + exerciseType;

                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                    }
                    while (condition1);
                    count++;

                    selectedQuestions.add(questionID);
                    textView1.setText(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("question").getValue()).toString());

                    if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("type").getValue()).toString().equals("true or false")){
                        button1.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.VISIBLE);
                    }
                    else if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(exerciseType)).child("type").getValue()).toString().equals("multiple choice")){
                        button3.setVisibility(View.VISIBLE);
                        radioGroup.setVisibility(View.VISIBLE);

                        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
                        exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("2").child("answers").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot radiobuttonSnapshot) {
                                radioButton1.setText(radiobuttonSnapshot.child("1").getValue().toString());
                                radioButton2.setText(radiobuttonSnapshot.child("2").getValue().toString());
                                radioButton3.setText(radiobuttonSnapshot.child("3").getValue().toString());
                                radioButton4.setText(radiobuttonSnapshot.child("4").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        button4.setVisibility(View.VISIBLE);
                        editText1.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });


        radioButton1.setOnClickListener(v -> {
            radiobuttonSelected = 1;
        });

        radioButton2.setOnClickListener(v -> {
            radiobuttonSelected = 2;
        });

        radioButton3.setOnClickListener(v -> {
            radiobuttonSelected = 3;
        });

        radioButton4.setOnClickListener(v -> {
            radiobuttonSelected = 4;
        });
    }


    public boolean questionAlreadyChosen(ArrayList<String> list, String id){
        return list.contains(id);
    }

    public int getExerciseType(String difficultyLevel, Integer index) {
        int questionType;

        if(difficultyLevel.equals("Easy")){
            if(index >= 1 && index <= 5){
                questionType = 1;
            }
            else if(index >= 6 && index <= 9){
                questionType = 2;
            }
            else{
                questionType = 3;
            }
        }
        else if(difficultyLevel.equals("Medium")){
            if(index >= 1 && index <= 3){
                questionType = 1;
            }
            else if(index >= 4 && index <= 8){
                questionType = 2;
            }
            else{
                questionType = 3;
            }
        }
        else{
            if(index >= 1 && index <= 2){
                questionType = 1;
            }
            else if(index >= 3 && index <= 5){
                questionType = 2;
            }
            else{
                questionType = 3;
            }
        }

        return questionType;
    }


    public void buttonCorrectOnClick(View view){
        //
        // updating Students' Data
        //
        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
        exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("1").child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot1)
            {
                if (dataSnapshot1.exists()) {

                    studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (dataSnapshot1.getValue().toString().equals("true")) {
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot2.child("lastNo1").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot2.child("lastNo2").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot2.child("lastNo3").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot2.child("lastNo4").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(1);

                                //Call Thread
                                MyThread myThread = new MyThread(true);
                                myThread.start();

                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);
                            }
                            else{
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot2.child("lastNo1").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot2.child("lastNo2").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot2.child("lastNo3").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot2.child("lastNo4").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(0);

                                //Call Thread
                                MyThread myThread = new MyThread(true);
                                myThread.start();

                                Integer newWeight = Integer.parseInt(dataSnapshot2.child("weight").getValue().toString()) + 1;
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // we are showing that error message in toast
                            Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        //
        //Intents
        //
        Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
        intent.putExtra("userID", currentUser.getUid());
        intent.putExtra("selectedUnit", selectedUnit);
        intent.putExtra("difficulty", difficulty);

        //Intents for recursively calling ExercisesActivity
        intent.putExtra("selectedQuestions", selectedQuestions);
        intent.putExtra("count", count);

        startActivity(intent);
    }

    public void buttonFalseOnClick(View view){
        //
        // updating Students' Data
        //
        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
        exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("1").child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot3)
            {
                if (dataSnapshot3.exists()) {

                    studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                            if (dataSnapshot3.getValue().toString().equals("true")) {
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot4.child("lastNo1").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot4.child("lastNo2").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot4.child("lastNo3").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot4.child("lastNo4").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(0);

                                //Call Thread
                                MyThread myThread = new MyThread(true);
                                myThread.start();

                                Integer newWeight = Integer.parseInt(dataSnapshot4.child("weight").getValue().toString()) + 1;
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);
                            }
                            else{
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot4.child("lastNo1").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot4.child("lastNo2").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot4.child("lastNo3").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot4.child("lastNo4").getValue());
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(1);

                                //Call Thread
                                MyThread myThread = new MyThread(true);
                                myThread.start();

                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // we are showing that error message in toast
                            Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        //
        //Intents
        //
        Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
        intent.putExtra("userID", currentUser.getUid());
        intent.putExtra("selectedUnit", selectedUnit);
        intent.putExtra("difficulty", difficulty);

        //Intents for recursively calling ExercisesActivity
        intent.putExtra("selectedQuestions", selectedQuestions);
        intent.putExtra("count", count);

        startActivity(intent);
    }

    public void buttonSubmitMultipleChoiceOnClick(View view){
        if(!radioButton1.isChecked() && !radioButton2.isChecked() && !radioButton3.isChecked() && !radioButton4.isChecked()) {
            Toast.makeText(this, "You must select an answer first!", Toast.LENGTH_LONG).show();
        }
        else {
            //
            // updating Students' Data
            //
            exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
            exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("2").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot5) {
                    if (dataSnapshot5.exists()) {

                        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                        studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot6) {
                                if (dataSnapshot5.child("answer").getValue().toString().equals(radiobuttonSelected.toString())) {
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot6.child("lastNo1").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot6.child("lastNo2").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot6.child("lastNo3").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot6.child("lastNo4").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(1);

                                    //Call Thread
                                    MyThread myThread = new MyThread(true);
                                    myThread.start();

                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);

                                    //
                                    //Addition fault
                                    //
                                    //Subtract one to totalAdditionFaults only if additionFaults was correct before
                                    if (dataSnapshot6.child("additionFaults").getValue().toString().equals("1")) {
                                        //Total addition fault
                                        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot7) {
                                                Integer total = Integer.parseInt(dataSnapshot7.child("totalAdditionFaults").getValue().toString()) - 1;
                                                studentsRef.child(userID).child("totalAdditionFaults").setValue(total);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                // we are showing that error message in toast
                                                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        //Addition fault
                                        studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("additionFaults").setValue(0);
                                    }

                                } else {
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot6.child("lastNo1").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot6.child("lastNo2").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot6.child("lastNo3").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot6.child("lastNo4").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(0);

                                    //Call Thread
                                    MyThread myThread = new MyThread(true);
                                    myThread.start();

                                    Integer newWeight = Integer.parseInt(dataSnapshot6.child("weight").getValue().toString()) + 1;
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);

                                    //
                                    //Addition fault
                                    //
                                    //If student answered addition's answer
                                    if (dataSnapshot5.child("additionAnswer").getValue().toString().equals(radiobuttonSelected.toString())) {
                                        //Add one to totalAdditionFaults only if additionFaults was correct before
                                        if (dataSnapshot6.child("additionFaults").getValue().toString().equals("0")) {
                                            //Addition fault
                                            studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("additionFaults").setValue(1);

                                            //Total addition fault
                                            studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot8) {
                                                    Integer total = Integer.parseInt(dataSnapshot8.child("totalAdditionFaults").getValue().toString()) + 1;
                                                    studentsRef.child(userID).child("totalAdditionFaults").setValue(total);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                    // we are showing that error message in toast
                                                    Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // we are showing that error message in toast
                                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            //
            //Intents
            //
            Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            intent.putExtra("selectedUnit", selectedUnit);
            intent.putExtra("difficulty", difficulty);

            //Intents for recursively calling ExercisesActivity
            intent.putExtra("selectedQuestions", selectedQuestions);
            intent.putExtra("count", count);

            startActivity(intent);
        }
    }

    public void buttonSubmitFillInTheGapOnClick(View view){
        if (TextUtils.isEmpty(editText1.getText().toString())) {
            Toast.makeText(this, "You must fill in the gap first!", Toast.LENGTH_LONG).show();
        }
        else {
            exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
            exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("3").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot9)
                {
                    if (dataSnapshot9.exists()) {

                        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                        studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot10) {
                                if (dataSnapshot9.child("answer").getValue().toString().equals(editText1.getText().toString())) {
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot10.child("lastNo1").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot10.child("lastNo2").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot10.child("lastNo3").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot10.child("lastNo4").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(1);

                                    //Call Thread
                                    MyThread myThread = new MyThread(true);
                                    myThread.start();

                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);

                                    //
                                    //Addition fault
                                    //
                                    //Subtract one to totalAdditionFaults only if additionFaults was correct before
                                    /*if(dataSnapshot10.child("additionFaults").getValue().toString().equals("1")){
                                        //Total addition fault
                                        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot7) {
                                                Integer total = Integer.parseInt(dataSnapshot7.child("totalAdditionFaults").getValue().toString()) - 1;
                                                studentsRef.child(userID).child("totalAdditionFaults").setValue(total);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                // we are showing that error message in toast
                                                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        //Addition fault
                                        studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("additionFaults").setValue(0);
                                    }*/

                                }
                                else{
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo2").setValue(dataSnapshot10.child("lastNo1").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo3").setValue(dataSnapshot10.child("lastNo2").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo4").setValue(dataSnapshot10.child("lastNo3").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo5").setValue(dataSnapshot10.child("lastNo4").getValue());
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("lastNo1").setValue(0);

                                    //Call Thread
                                    MyThread myThread = new MyThread(true);
                                    myThread.start();

                                    Integer newWeight = Integer.parseInt(dataSnapshot10.child("weight").getValue().toString()) + 1;
                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);

                                    //
                                    //Addition fault
                                    //
                                    //If student answered addition's answer
                                    /*if (dataSnapshot10.child("additionAnswer").getValue().toString().equals(radiobuttonSelected.toString())){
                                        //Add one to totalAdditionFaults only if additionFaults was correct before
                                        if(dataSnapshot10.child("additionFaults").getValue().toString().equals("0")) {
                                            //Addition fault
                                            studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("additionFaults").setValue(1);

                                            //Total addition fault
                                            studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot8) {
                                                    Integer total = Integer.parseInt(dataSnapshot8.child("totalAdditionFaults").getValue().toString()) + 1;
                                                    studentsRef.child(userID).child("totalAdditionFaults").setValue(total);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                    // we are showing that error message in toast
                                                    Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }

                                    }*/
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // we are showing that error message in toast
                                Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });

            //
            //Intents
            //
            if(count > 10) {
                //Go to MainActivity
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.putExtra("userID", currentUser.getUid());
                intent2.putExtra("email", currentUser.getEmail());
                startActivity(intent2);
            }
            else {
                //Recursively calling ExercisesActivity
                Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                intent.putExtra("userID", currentUser.getUid());
                intent.putExtra("selectedUnit", selectedUnit);
                intent.putExtra("difficulty", difficulty);

                //Intents for recursively calling ExercisesActivity
                intent.putExtra("selectedQuestions", selectedQuestions);
                intent.putExtra("count", count);

                startActivity(intent);
            }
        }
    }


    //
    //Thread for calculating student's score
    //
    class MyThread extends Thread {
        Boolean runThread;

        MyThread(Boolean runThread) {
            this.runThread = runThread;
        }

        public void run() {
            if (runThread) {
                try {
                    //2sec delay
                    MyThread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot threadDataSnapshot) {

                        Integer l1 = Integer.parseInt(threadDataSnapshot.child("lastNo1").getValue().toString());
                        Integer l2 = Integer.parseInt(threadDataSnapshot.child("lastNo2").getValue().toString());
                        Integer l3 = Integer.parseInt(threadDataSnapshot.child("lastNo3").getValue().toString());
                        Integer l4 = Integer.parseInt(threadDataSnapshot.child("lastNo4").getValue().toString());
                        Integer l5 = Integer.parseInt(threadDataSnapshot.child("lastNo5").getValue().toString());
                        Integer newScore = l1 + l2 + l3 + l4 + l5;

                        studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // we are showing that error message in toast
                        Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                    }
                });
                runThread = false;
            }
        }
    }


}