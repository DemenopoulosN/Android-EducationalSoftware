package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class LogInActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextUsername;
    Button button1,button2,button3,button4;
    TextView textView1,textView2,textView3;
    CheckBox checkBox, checkBox2;
    ImageView imageView, imageViewProfile;
    String profile;

    //Shared Preferences
    SharedPreferences preferences;

    public FirebaseAuth mAuth;
    FirebaseUser currentUser;

    //Firebase Database
    DatabaseReference databaseRef;

    boolean isSignInPushed = false;







    Random random;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        textView1 = findViewById(R.id.textViewStudentNameTitle);
        textView2 = findViewById(R.id.textViewStudentEmailTitle);
        textView3 = findViewById(R.id.loginTitle2);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        imageView = findViewById(R.id.imageViewInfoLogIn);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        //
        //Shared Preferences
        //
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Email
        editTextEmail.setText("");
        //Password
        editTextPassword.setText("");

        checkBox.setChecked(false);

        profile = getIntent().getStringExtra("profilePicture");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        //databaseRef = FirebaseDatabase.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);

        if(profile.equals("1")){
            imageViewProfile.setImageResource(R.drawable.student);
        }
        else{
            imageViewProfile.setImageResource(R.drawable.professor);

        }
    }

    public void signUp(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.errorToast2), Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.signUpToast), Toast.LENGTH_LONG).show();
                            createUsername(editTextUsername.getText().toString(), currentUser);

                            isSignInPushed = false;
                            writeSP(false);

                            if(profile.equals("1")) {
                                //
                                //creating node for each student in Firebase database
                                //
                                DatabaseReference newStudent = databaseRef.child("Students").push();
                                databaseRef.child("Students").child(currentUser.getUid()).child("totalAdditionFaults").setValue(0);
                                databaseRef.child("Students").child(currentUser.getUid()).child("revisionTestScore").setValue(0);
                                databaseRef.child("Students").child(currentUser.getUid()).child("currentRevisionScore").setValue(0);
                                databaseRef.child("Students").child(currentUser.getUid()).child("problemsScore").setValue(0);
                                databaseRef.child("Students").child(currentUser.getUid()).child("name").setValue(editTextUsername.getText().toString());
                                databaseRef.child("Students").child(currentUser.getUid()).child("email").setValue(editTextEmail.getText().toString());


                                //units
                                for (int i = 1; i <= 10; i++) {
                                    databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child("views").setValue(0);
                                    databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child("unitScore").setValue(0);
                                    //operations
                                    for (int j = 1; j <= 10; j++) {
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("weight").setValue(1);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("score").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("additionFaults").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("lastNo1").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("lastNo2").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("lastNo3").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("lastNo4").setValue(0);
                                        databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child(String.valueOf(j)).child("lastNo5").setValue(0);
                                    }
                                }

                                //go to mainActivity
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userID", currentUser.getUid());
                                intent.putExtra("email", currentUser.getEmail());
                                intent.putExtra("username",currentUser.getDisplayName());
                                startActivity(intent);
                            }
                            else {
                                //go to TeacherActivity
                                Intent intent3 = new Intent(getApplicationContext(), TeacherActivity.class);
                                intent3.putExtra("userID", currentUser.getUid());
                                intent3.putExtra("email", currentUser.getEmail());
                                intent3.putExtra("username",currentUser.getDisplayName());
                                startActivity(intent3);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    public void transitionClick(View view){
        editTextUsername.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.VISIBLE);
        checkBox2.setVisibility(View.VISIBLE);
        String str_email = preferences.getString("myKeyEmail", "");
        editTextEmail.setText(str_email);
        String str_password = preferences.getString("myKeyPassword", "");
        editTextPassword.setText(str_password);
        checkBox2.setChecked(!str_email.equals("") || !str_password.equals(""));

    }

    public void goBack(View view){
        editTextUsername.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        textView1.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        editTextEmail.setText("");
        editTextPassword.setText("");
        checkBox.setChecked(false);
    }


    public void signIn(View view) {
        mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        currentUser = mAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginToast), Toast.LENGTH_LONG).show();

                        isSignInPushed = true;
                        writeSP(true);


                        if(profile.equals("1")) {
                            //go to mainActivity
                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                            intent2.putExtra("userID", currentUser.getUid());
                            intent2.putExtra("email", currentUser.getEmail());
                            intent2.putExtra("username",currentUser.getDisplayName());
                            startActivity(intent2);
                        }
                        else {
                            //go to TeacherActivity
                            Intent intent4 = new Intent(getApplicationContext(), TeacherActivity.class);
                            intent4.putExtra("userID", currentUser.getUid());
                            intent4.putExtra("email", currentUser.getEmail());
                            intent4.putExtra("username",currentUser.getDisplayName());
                            startActivity(intent4);
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void createUsername(String username, FirebaseUser user){
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(task -> Toast.makeText(getApplicationContext(),getResources().getString(R.string.userCratedToast),Toast.LENGTH_LONG).show());
    }


    public void writeSP(Boolean isSignInPushed) {
        SharedPreferences.Editor editor = preferences.edit();
        if (isSignInPushed){
            if(checkBox2.isChecked()) {
                //Email
                editor.putString("myKeyEmail", editTextEmail.getText().toString());
                editor.apply();
                //Password
                editor.putString("myKeyPassword", editTextPassword.getText().toString());
            }
            else {
                //Email
                editor.putString("myKeyEmail", "");
                editor.apply();
                //Password
                editor.putString("myKeyPassword", "");
            }
            editor.apply();
        }
        else
        {
            if(checkBox.isChecked()) {
                //Email
                editor.putString("myKeyEmail", editTextEmail.getText().toString());
                editor.apply();
                //Password
                editor.putString("myKeyPassword", editTextPassword.getText().toString());
            }
            else {
                //Email
                editor.putString("myKeyEmail", "");
                editor.apply();
                //Password
                editor.putString("myKeyPassword", "");
            }
            editor.apply();
        }
    }

    public void infoLogIn(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Γειά σου!","Αν δεν έχεις λογαριασμό μπορείς να φτιάξεις έναν αμέσως μόνο με το email σου καθώς και με έναν κωδικό. Αν έχεις ήδη λογαριασμό, πάτησε το αντίστοιχο κουμπί.");
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

    //
    //Creating node for each exercise in Firebase database
    //
    public void createExercisesDatabase(){
        random = new Random();
        int randomTrueOrFalse, rightAnswer, additionAnswer, randomWrongAnswer1, randomWrongAnswer2, max, min, rightAnswerPosition, additionAnswerPosition, randomWrongAnswer1Position, randomWrongAnswer2Position;
        String strQuestion = "";
        //units
        for (int unit = 1; unit <= 10; unit++) {
            //operations
            for (int operation = 1; operation <= 10; operation++) {
                rightAnswer = unit * operation;
                additionAnswer = unit + operation;

                //questions
                for (int question = 1; question <= 3; question++) {
                    strQuestion = unit + " x " + operation + " = ";
                    //first question
                    if (question == 1){
                        //
                        //type
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("type").setValue("true or false");
                        //
                        //question
                        //
                        randomTrueOrFalse = random.nextInt(2) + 1;
                        //If true
                        if(randomTrueOrFalse == 1) {
                            strQuestion = strQuestion + rightAnswer;
                            databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("question").setValue(strQuestion);
                            //
                            //answer
                            //
                            databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answer").setValue("true");
                        }
                        //If false
                        else {
                            max = unit * (operation + 1);
                            min = unit * (operation - 1);
                            do {
                                randomWrongAnswer1 = random.nextInt(max + 1 - min) + min;
                            } while (randomWrongAnswer1 == rightAnswer || randomWrongAnswer1 == 0);

                            strQuestion = strQuestion + randomWrongAnswer1;
                            databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("question").setValue(strQuestion);
                            //
                            //answer
                            //
                            databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answer").setValue("false");
                        }
                        //
                        //answers
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child("1").setValue("true");
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child("2").setValue("false");
                    }
                    //second question
                    else if (question == 2){
                        //
                        //type
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("type").setValue("multiple choice");
                        //
                        //question
                        //
                        strQuestion = strQuestion + "?";
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("question").setValue(strQuestion);
                        //
                        //answer
                        //
                        rightAnswerPosition = random.nextInt(4) + 1;
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answer").setValue(String.valueOf(rightAnswerPosition));
                        //
                        //additionAnswer
                        //
                        do {
                            additionAnswerPosition = random.nextInt(4) + 1;
                        } while (additionAnswerPosition == rightAnswerPosition);
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("additionAnswer").setValue(String.valueOf(additionAnswerPosition));
                        //
                        //answers
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child(String.valueOf(rightAnswerPosition)).setValue(String.valueOf(rightAnswer));
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child(String.valueOf(additionAnswerPosition)).setValue(String.valueOf(additionAnswer));
                        //int numberOfAnswersFilled = 0;
                        //while (numberOfAnswersFilled < 2){

                        //    numberOfAnswersFilled++;
                        //}

                        max = unit * (operation + 1);
                        min = unit * (operation - 1);

                        //randomWrongAnswer1Position
                        do {
                            randomWrongAnswer1Position = random.nextInt(4) + 1;
                        } while(randomWrongAnswer1Position == rightAnswerPosition || randomWrongAnswer1Position == additionAnswerPosition);
                        //randomWrongAnswer1
                        do {
                            randomWrongAnswer1 = random.nextInt(max + 1 - min) + min;
                        } while(randomWrongAnswer1 == rightAnswer || randomWrongAnswer1 == additionAnswer || randomWrongAnswer1 == 0);

                        //randomWrongAnswer2Position
                        do {
                            randomWrongAnswer2Position = random.nextInt(4) + 1;
                        } while(randomWrongAnswer2Position == rightAnswerPosition || randomWrongAnswer2Position == additionAnswerPosition || randomWrongAnswer2Position == randomWrongAnswer1Position);
                        //randomWrongAnswer2
                        do {
                            randomWrongAnswer2 = random.nextInt(max + 1 - min) + min;
                        } while(randomWrongAnswer2 == rightAnswer || randomWrongAnswer2 == additionAnswer || randomWrongAnswer2 == randomWrongAnswer1 || randomWrongAnswer2 == 0);


                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child(String.valueOf(randomWrongAnswer1Position)).setValue(String.valueOf(randomWrongAnswer1));
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answers").child(String.valueOf(randomWrongAnswer2Position)).setValue(String.valueOf(randomWrongAnswer2));


                    }
                    //third question
                    else{
                        //
                        //type
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("type").setValue("fill in the blank");
                        //
                        //question
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("question").setValue(strQuestion);
                        //
                        //answer
                        //
                        databaseRef.child("Exercises").child(String.valueOf(unit)).child(String.valueOf(operation)).child(String.valueOf(question)).child("answer").setValue(String.valueOf(rightAnswer));
                    }
                }
            }
        }
    }

}