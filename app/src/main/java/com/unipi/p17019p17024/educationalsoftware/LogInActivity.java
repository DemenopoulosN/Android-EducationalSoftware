package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.unipi.p17019p17024.educationalsoftware.ui.home.HomeFragment;

public class LogInActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button button1,button2,button3,button4;
    TextView textView1,textView2,textView3;
    CheckBox checkBox, checkBox2;
    ImageView imageView;

    //Shared Preferences
    SharedPreferences preferences;

    public FirebaseAuth mAuth;
    FirebaseUser currentUser;

    //Firebase Database
    DatabaseReference databaseRef;

    boolean isSignInPushed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        textView1 = findViewById(R.id.loginTitle1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.loginTitle2);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        imageView = findViewById(R.id.imageViewInfoLogIn);

        //
        //Shared Preferences
        //
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Email
        editTextEmail.setText("");
        //Password
        editTextPassword.setText("");

        checkBox.setChecked(false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        //databaseRef = FirebaseDatabase.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
    }

    public void signUp(View view) {
        if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.errorToast2), Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.signUpToast), Toast.LENGTH_LONG).show();

                            isSignInPushed = false;
                            writeSP(false);

                            //
                            //creating node for each student in Firebase database
                            //
                            DatabaseReference newStudent = databaseRef.child("Students").push();
                            databaseRef.child("Students").child(currentUser.getUid()).child("totalAdditionFaults").setValue(0);

                            //units
                            for(int i = 1; i<=10; i++){
                                databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child("views").setValue(0);
                                databaseRef.child("Students").child(currentUser.getUid()).child(String.valueOf(i)).child("unitScore").setValue(0);
                                //operations
                                for(int j = 1; j<=10; j++){
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
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    public void transitionClick(View view){
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
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

                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("userID", currentUser.getUid());
                        intent2.putExtra("email", currentUser.getEmail());
                        startActivity(intent2);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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
}