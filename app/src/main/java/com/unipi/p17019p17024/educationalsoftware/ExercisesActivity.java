package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    Integer selectedUnit, randomOperation; //, randomQuestion
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

    //Shared Preferences
    SharedPreferences preferences;


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

        //Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //For saving Shared Preferences on launching MainActivity when opening the app
        //int spScore1 = preferences.getInt("score1", 0);
        //int spScore2 = preferences.getInt("score2", 0);
        //int spScore3 = preferences.getInt("score3", 0);
        //int spScore4 = preferences.getInt("score4", 0);
        //int spScore5 = preferences.getInt("score5", 0);


        //GetIntent
        //Recursion
        selectedQuestions = getIntent().getStringArrayListExtra("selectedQuestions");
        Log.d("selectedQuestions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", String.valueOf(selectedQuestions.size()));
        // = selectedQuestions.size() + 1;
        count = getIntent().getIntExtra("count", 1);
        Log.d("count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",count.toString());
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
                        //randomQuestion = random.nextInt(3) + 1;
                        exerciseType = getExerciseType(difficulty, count);


                        questionID = randomOperation.toString() + exerciseType;
                        Log.d("questionID",questionID);

                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                        Log.d("condition1",condition1.toString());
                        //condition2 = difficultyCheck(difficulty, count, randomQuestion);
                        //Log.d("condition2",condition2.toString());
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
        int questionType = 0;

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
                                if(Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) < 5){
                                    //Integer newScore = Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) + 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) + 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "1";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 1);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);
                            }
                            else{
                                if(Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) > 0){
                                    //Integer newScore = Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) - 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot2.child("score").getValue().toString()) - 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "1";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 0);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
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
                                if(Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) > 0){
                                    //Integer newScore = Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) - 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) - 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "1";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 0);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
                                Integer newWeight = Integer.parseInt(dataSnapshot4.child("weight").getValue().toString()) + 1;
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);
                            }
                            else{
                                if(Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) < 5){
                                    //Integer newScore = Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) + 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot4.child("score").getValue().toString()) + 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "1";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 1);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
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
        //
        // updating Students' Data
        //
        exercisesRef = FirebaseDatabase.getInstance().getReference().child("Exercises");
        exercisesRef.child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot5)
            {
                if (dataSnapshot5.exists()) {

                    studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot6) {
                            if (dataSnapshot5.child("answer").getValue().toString().equals(radiobuttonSelected.toString())) {
                                if(Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) < 5){
                                    //Integer newScore = Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) + 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) + 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "2";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 1);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(1);

                                //
                                //Addition fault
                                //
                                //Subtract one to totalAdditionFaults only if additionFaults was correct before
                                if(dataSnapshot6.child("additionFaults").getValue().toString().equals("1")){
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

                            }
                            else{
                                if(Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) > 0){
                                    //Integer newScore = Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) - 1;



                                    //
                                    //
                                    //FOR SCORE:
                                    //CHANGE THE FIRST LINE FOR THOSE BELOW IT
                                    //
                                    //
                                    //Integer newScore = Integer.parseInt(dataSnapshot6.child("score").getValue().toString()) - 1;
                                    //
                                    String id = String.valueOf(selectedUnit) + String.valueOf(randomOperation) + "2";
                                    int spAnswer2 = preferences.getInt("answer1"+id, 0);
                                    int spAnswer3 = preferences.getInt("answer2"+id, 0);
                                    int spAnswer4 = preferences.getInt("answer3"+id, 0);
                                    int spAnswer5 = preferences.getInt("answer4"+id, 0);
                                    //Update student's last submitted answer in Shared Preferences
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("answer1"+id, 0);
                                    editor.putInt("answer2"+id, spAnswer2);
                                    editor.putInt("answer3"+id, spAnswer3);
                                    editor.putInt("answer4"+id, spAnswer4);
                                    editor.putInt("answer5"+id, spAnswer5);
                                    editor.apply();
                                    //Update the first Shared Preferences' value
                                    int spAnswer1 = preferences.getInt("answer1"+id, 0);

                                    Integer newScore = spAnswer1 + spAnswer2 + spAnswer3 + spAnswer4 + spAnswer5;
                                    //
                                    //
                                    //



                                    studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("score").setValue(newScore);
                                }
                                Integer newWeight = Integer.parseInt(dataSnapshot6.child("weight").getValue().toString()) + 1;
                                studentsRef.child(userID).child(String.valueOf(selectedUnit)).child(String.valueOf(randomOperation)).child("weight").setValue(newWeight);

                                //If student answered addition's answer
                                if (dataSnapshot5.child("additionAnswer").getValue().toString().equals(radiobuttonSelected.toString())){
                                    //Add one to totalAdditionFaults only if additionFaults was correct before
                                    if(dataSnapshot6.child("additionFaults").getValue().toString().equals("0")) {
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

    public void buttonSubmitFillInTheGapOnClick(View view){

        //
        //Intents
        //

        //TO-DO
        //έλεγχος για το αν έχει συμπληρώσει κάτι στο κενό
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