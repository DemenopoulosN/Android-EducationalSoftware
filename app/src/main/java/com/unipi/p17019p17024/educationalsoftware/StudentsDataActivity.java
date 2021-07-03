package com.unipi.p17019p17024.educationalsoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class StudentsDataActivity extends AppCompatActivity {

    String studentID, studentName, studentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_data);


        //GetIntent
        studentID = getIntent().getStringExtra("studentID");
        studentName = getIntent().getStringExtra("studentName");
        studentEmail = getIntent().getStringExtra("studentEmail");

        //Toast.makeText(getApplicationContext(), studentID + "\n" + studentName + "\n" + studentEmail, Toast.LENGTH_LONG).show();


    }
}