package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.Objects;

public class TheoryActivity extends AppCompatActivity {
    String userID;
    Integer selectedUnit;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference unitRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        textView1 = findViewById(R.id.textViewOperation1);
        textView2 = findViewById(R.id.textViewOperation2);
        textView3 = findViewById(R.id.textViewOperation3);
        textView4 = findViewById(R.id.textViewOperation4);
        textView5 = findViewById(R.id.textViewOperation5);
        textView6 = findViewById(R.id.textViewOperation6);
        textView7 = findViewById(R.id.textViewOperation7);
        textView8 = findViewById(R.id.textViewOperation8);
        textView9 = findViewById(R.id.textViewOperation9);
        textView10 = findViewById(R.id.textViewOperation10);

        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userID = getIntent().getStringExtra("userID");
        selectedUnit = getIntent().getIntExtra("selectedUnit", 0);
        Toast.makeText(getApplicationContext(), "unit: "+ selectedUnit, Toast.LENGTH_LONG).show();


        //Firebase Database
        database = FirebaseDatabase.getInstance();
        unitRef = FirebaseDatabase.getInstance().getReference().child("Units").child(String.valueOf(selectedUnit));

        unitRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    textView1.setText(Objects.requireNonNull(dataSnapshot.child("1").getValue()).toString());
                    textView2.setText(Objects.requireNonNull(dataSnapshot.child("2").getValue()).toString());
                    textView3.setText(Objects.requireNonNull(dataSnapshot.child("3").getValue()).toString());
                    textView4.setText(Objects.requireNonNull(dataSnapshot.child("4").getValue()).toString());
                    textView5.setText(Objects.requireNonNull(dataSnapshot.child("5").getValue()).toString());
                    textView6.setText(Objects.requireNonNull(dataSnapshot.child("6").getValue()).toString());
                    textView7.setText(Objects.requireNonNull(dataSnapshot.child("7").getValue()).toString());
                    textView8.setText(Objects.requireNonNull(dataSnapshot.child("8").getValue()).toString());
                    textView9.setText(Objects.requireNonNull(dataSnapshot.child("9").getValue()).toString());
                    textView10.setText(Objects.requireNonNull(dataSnapshot.child("10").getValue()).toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(TheoryActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

    }
}