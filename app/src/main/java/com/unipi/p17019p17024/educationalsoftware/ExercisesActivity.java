package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    String userID, questionID, difficulty;
    TextView textView1;
    EditText editText1;
    Button button1,button2,button3,button4;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    ImageView imageView;
    Integer selectedUnit, randomOperation, randomQuestion;
    ArrayList<String> selectedQuestions = new ArrayList<>();
    Boolean condition1, condition2, condition3;
    Integer count = 1;

    Random random;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference unitRef;

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
                    do {
                        randomOperation = random.nextInt(10 + 1) + 1;
                        //randomQuestion = random.nextInt(3 + 1) + 1;


                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                        Log.d("condition1",condition1.toString());
                        condition2 = difficultyCheck(difficulty, count, randomQuestion);
                        Log.d("condition2",condition2.toString());


                        questionID = randomOperation.toString() + randomQuestion.toString();
                        Log.d("questionID",questionID);

                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                        Log.d("condition1",condition1.toString());
                        condition2 = difficultyCheck(difficulty, count, randomQuestion);
                        Log.d("condition2",condition2.toString());
                    }
                    while (condition1 && condition2);
                    count++;

                    selectedQuestions.add(questionID);
                    textView1.setText(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("question").getValue()).toString());

                    if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("type").getValue()).toString().equals("true or false")){
                        button1.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.VISIBLE);
                    }
                    else if(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("type").getValue()).toString().equals("multiple choice")){
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

    }

    public void generateExercise(){
        unitRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    do {
                        randomOperation = random.nextInt(10 + 1) + 1;
                        randomQuestion = random.nextInt(3 + 1) + 1;
                        questionID = randomOperation.toString() + randomQuestion.toString();
                        Log.d("questionID",questionID);

                        condition1 = questionAlreadyChosen(selectedQuestions, questionID);
                        Log.d("condition1",condition1.toString());
                        condition2 = difficultyCheck(difficulty, count, randomQuestion);
                        Log.d("condition2",condition2.toString());
                    }
                    while (condition1 && condition2);
                    count++;

                    selectedQuestions.add(questionID);
                    textView1.setText(Objects.requireNonNull(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("question").getValue()).toString());

                    if(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("type").getValue() == "true or false"){
                        button1.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.VISIBLE);
                    }
                    else if(dataSnapshot.child(String.valueOf(randomOperation)).child(String.valueOf(randomQuestion)).child("type").getValue() == "multiple choice"){
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
    }

    public boolean questionAlreadyChosen(ArrayList<String> list, String id){
        return list.contains(id);
    }

    public boolean difficultyCheck(String difficultyLevel, Integer index, Integer questionType){
        if(difficultyLevel.equals("Easy")){
            Log.d("α","Βρίσκομαι στο easy");
            if(index >= 1 && index <= 5){
                Log.d("β","Βρίσκομαι στην ερώτηση 1:5");
                return questionType == 1;
            }
            else if(index >= 6 && index <= 9){
                return questionType == 2;
            }
            else{
                return questionType == 3;
            }
        }
        else if(difficultyLevel.equals("Medium")){
            Log.d("γ","Βρίσκομαι στο medium");
            if(index >= 1 && index <= 3){
                Log.d("δ","Βρίσκομαι στην ερώτηση 1:3");
                return questionType == 1;
            }
            else if(index >= 4 && index <= 8){
                return questionType == 2;
            }
            else{
                return questionType == 3;
            }
        }
        else{
            Log.d("ε","Βρίσκομαι στο Hard");
            if(index >= 1 && index <= 2){
                Log.d("ζ","Βρίσκομαι στην ερώτηση 1:2");
                return questionType == 1;
            }
            else if(index >= 3 && index <= 5){
                return questionType == 2;
            }
            else{
                return questionType == 3;
            }
        }
    }

    public void buttonCorrectOnClick(){
        generateExercise();
    }

    public void buttonFalseOnClick(){
        generateExercise();
    }

    public void buttonSubmitMultipleChoiceOnClick(){
        //TO-DO
        //έλεγχος για το αν έχει επιλέξει κάποια απάντηση
        generateExercise();
    }

    public void buttonSubmitFillInTheGapOnClick(){
        //TO-DO
        //έλεγχος για το αν έχει συμπληρώσει κάτι στο κενό
        generateExercise();


        if(count == 10) {
            Toast.makeText(this,"Goodnight Papatzh", Toast.LENGTH_LONG).show();
        }
    }

}