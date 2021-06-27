package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EntryActivity extends AppCompatActivity {
    Button button1,button2;
    boolean isStudent = false;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        button1 = findViewById(R.id.enterStudentButton);
        button2 = findViewById(R.id.enterProfessorButton);
        imageView = findViewById(R.id.imageViewInfoEntry);
    }

    public void enterStudent(View view){
        isStudent = true;
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        //intent.putExtra("boolean", isStudent);
        startActivity(intent);
    }

    public void enterProfessor(View view){
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }
}