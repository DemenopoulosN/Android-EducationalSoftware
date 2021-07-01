package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EntryActivity extends AppCompatActivity {
    Button button1,button2;
    String profile;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        button1 = findViewById(R.id.enterStudentButton);
        button2 = findViewById(R.id.enterProfessorButton);
        imageView = findViewById(R.id.imageViewInfoEntry);
        profile = "0";
    }

    public void enterStudent(View view){
        profile = "1";
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        intent.putExtra("profilePicture", profile);
        startActivity(intent);
    }

    public void enterProfessor(View view){
        profile = "2";
        Intent intent2 = new Intent(getApplicationContext(), LogInActivity.class);
        intent2.putExtra("profilePicture", profile);
        startActivity(intent2);
    }

    public void infoEntry(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Καλώς ήρθες!","Αυτό το καλοκαίρι θα μάθεις την προπαίδεια με τον πιο διασκεδαστικό τρόπο! Συνδέσου είτε ως μαθητής είτε ως καθηγητής πατώντας το αντίστοιχο κουμπί. Καλό παιχνίδι!");
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