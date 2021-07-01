package com.unipi.p17019p17024.educationalsoftware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class TheoryActivity extends AppCompatActivity {
    String userID, email, difficulty;
    Integer selectedUnit;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    Button button1;
    ImageView imageView;
    ImageView imageViewStars1, imageViewStars2, imageViewStars3, imageViewStars4, imageViewStars5, imageViewStars6, imageViewStars7, imageViewStars8, imageViewStars9, imageViewStars10;

    //Intents for ExercisesActivity initialization
    ArrayList<String> selectedQuestions = new ArrayList<>();
    Integer count = 1;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference unitRef, studentsRef;

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
        button1 = findViewById(R.id.buttonGoToExercises);
        imageView = findViewById(R.id.imageViewInfoTheory);
        imageViewStars1 =findViewById(R.id.imageViewOperation1Stars);
        imageViewStars2 =findViewById(R.id.imageViewOperation2Stars);
        imageViewStars3 =findViewById(R.id.imageViewOperation3Stars);
        imageViewStars4 =findViewById(R.id.imageViewOperation4Stars);
        imageViewStars5 =findViewById(R.id.imageViewOperation5Stars);
        imageViewStars6 =findViewById(R.id.imageViewOperation6Stars);
        imageViewStars7 =findViewById(R.id.imageViewOperation7Stars);
        imageViewStars8 =findViewById(R.id.imageViewOperation8Stars);
        imageViewStars9 =findViewById(R.id.imageViewOperation9Stars);
        imageViewStars10 =findViewById(R.id.imageViewOperation10Stars);


        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //GetIntent
        userID = getIntent().getStringExtra("userID");
        selectedUnit = getIntent().getIntExtra("selectedUnit", 0);
        difficulty = getIntent().getStringExtra("difficulty");
        //Toast.makeText(getApplicationContext(), "unit: "+ selectedUnit, Toast.LENGTH_LONG).show();
        email = getIntent().getStringExtra("email");


        //Firebase Database
        database = FirebaseDatabase.getInstance();
        unitRef = FirebaseDatabase.getInstance().getReference().child("Units").child(String.valueOf(selectedUnit));

        unitRef.addListenerForSingleValueEvent(new ValueEventListener(){
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


        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).child(String.valueOf(selectedUnit)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot2) {
                //operation1
                Integer score1 = Integer.parseInt(dataSnapshot2.child("1").child("score").getValue().toString());
                if(score1 == 0){
                    imageViewStars1.setImageResource(R.drawable.stars0);
                }
                else if(score1 == 1){
                    imageViewStars1.setImageResource(R.drawable.stars1);
                }
                else if(score1 == 2){
                    imageViewStars1.setImageResource(R.drawable.stars2);
                }
                else if(score1 == 3){
                    imageViewStars1.setImageResource(R.drawable.stars3);
                }
                else if(score1 == 4){
                    imageViewStars1.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars1.setImageResource(R.drawable.stars5);
                }

                //operation2
                Integer score2 = Integer.parseInt(dataSnapshot2.child("2").child("score").getValue().toString());
                if(score2 == 0){
                    imageViewStars2.setImageResource(R.drawable.stars0);
                }
                else if(score2 == 1){
                    imageViewStars2.setImageResource(R.drawable.stars1);
                }
                else if(score2 == 2){
                    imageViewStars2.setImageResource(R.drawable.stars2);
                }
                else if(score2 == 3){
                    imageViewStars2.setImageResource(R.drawable.stars3);
                }
                else if(score2 == 4){
                    imageViewStars2.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars2.setImageResource(R.drawable.stars5);
                }

                //operation3
                Integer score3 = Integer.parseInt(dataSnapshot2.child("3").child("score").getValue().toString());
                if(score3 == 0){
                    imageViewStars3.setImageResource(R.drawable.stars0);
                }
                else if(score3 == 1){
                    imageViewStars3.setImageResource(R.drawable.stars1);
                }
                else if(score3 == 2){
                    imageViewStars3.setImageResource(R.drawable.stars2);
                }
                else if(score3 == 3){
                    imageViewStars3.setImageResource(R.drawable.stars3);
                }
                else if(score3 == 4){
                    imageViewStars3.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars3.setImageResource(R.drawable.stars5);
                }

                //operation4
                Integer score4 = Integer.parseInt(dataSnapshot2.child("4").child("score").getValue().toString());
                if(score4 == 0){
                    imageViewStars4.setImageResource(R.drawable.stars0);
                }
                else if(score4 == 1){
                    imageViewStars4.setImageResource(R.drawable.stars1);
                }
                else if(score4 == 2){
                    imageViewStars4.setImageResource(R.drawable.stars2);
                }
                else if(score4 == 3){
                    imageViewStars4.setImageResource(R.drawable.stars3);
                }
                else if(score4 == 4){
                    imageViewStars4.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars4.setImageResource(R.drawable.stars5);
                }

                //operation5
                Integer score5 = Integer.parseInt(dataSnapshot2.child("5").child("score").getValue().toString());
                if(score5 == 0){
                    imageViewStars5.setImageResource(R.drawable.stars0);
                }
                else if(score5 == 1){
                    imageViewStars5.setImageResource(R.drawable.stars1);
                }
                else if(score5 == 2){
                    imageViewStars5.setImageResource(R.drawable.stars2);
                }
                else if(score5 == 3){
                    imageViewStars5.setImageResource(R.drawable.stars3);
                }
                else if(score5 == 4){
                    imageViewStars5.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars5.setImageResource(R.drawable.stars5);
                }

                //operation6
                Integer score6 = Integer.parseInt(dataSnapshot2.child("6").child("score").getValue().toString());
                if(score6 == 0){
                    imageViewStars6.setImageResource(R.drawable.stars0);
                }
                else if(score6 == 1){
                    imageViewStars6.setImageResource(R.drawable.stars1);
                }
                else if(score6 == 2){
                    imageViewStars6.setImageResource(R.drawable.stars2);
                }
                else if(score6 == 3){
                    imageViewStars6.setImageResource(R.drawable.stars3);
                }
                else if(score6 == 4){
                    imageViewStars6.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars6.setImageResource(R.drawable.stars5);
                }

                //operation7
                Integer score7 = Integer.parseInt(dataSnapshot2.child("7").child("score").getValue().toString());
                if(score7 == 0){
                    imageViewStars7.setImageResource(R.drawable.stars0);
                }
                else if(score7 == 1){
                    imageViewStars7.setImageResource(R.drawable.stars1);
                }
                else if(score7 == 2){
                    imageViewStars7.setImageResource(R.drawable.stars2);
                }
                else if(score7 == 3){
                    imageViewStars7.setImageResource(R.drawable.stars3);
                }
                else if(score7 == 4){
                    imageViewStars7.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars7.setImageResource(R.drawable.stars5);
                }

                //operation8
                Integer score8 = Integer.parseInt(dataSnapshot2.child("8").child("score").getValue().toString());
                if(score8 == 0){
                    imageViewStars8.setImageResource(R.drawable.stars0);
                }
                else if(score8 == 1){
                    imageViewStars8.setImageResource(R.drawable.stars1);
                }
                else if(score8 == 2){
                    imageViewStars8.setImageResource(R.drawable.stars2);
                }
                else if(score8 == 3){
                    imageViewStars8.setImageResource(R.drawable.stars3);
                }
                else if(score8 == 4){
                    imageViewStars8.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars8.setImageResource(R.drawable.stars5);
                }

                //operation9
                Integer score9 = Integer.parseInt(dataSnapshot2.child("9").child("score").getValue().toString());
                if(score9 == 0){
                    imageViewStars9.setImageResource(R.drawable.stars0);
                }
                else if(score9 == 1){
                    imageViewStars9.setImageResource(R.drawable.stars1);
                }
                else if(score9 == 2){
                    imageViewStars9.setImageResource(R.drawable.stars2);
                }
                else if(score9 == 3){
                    imageViewStars9.setImageResource(R.drawable.stars3);
                }
                else if(score9 == 4){
                    imageViewStars9.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars9.setImageResource(R.drawable.stars5);
                }

                //operation10
                Integer score10 = Integer.parseInt(dataSnapshot2.child("10").child("score").getValue().toString());
                if(score10 == 0){
                    imageViewStars10.setImageResource(R.drawable.stars0);
                }
                else if(score10 == 1){
                    imageViewStars10.setImageResource(R.drawable.stars1);
                }
                else if(score10 == 2){
                    imageViewStars10.setImageResource(R.drawable.stars2);
                }
                else if(score10 == 3){
                    imageViewStars10.setImageResource(R.drawable.stars3);
                }
                else if(score10 == 4){
                    imageViewStars10.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewStars10.setImageResource(R.drawable.stars5);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // we are showing that error message in toast
                Toast.makeText(TheoryActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });


        button1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            intent.putExtra("selectedUnit", selectedUnit);
            intent.putExtra("difficulty", difficulty);

            //Intents for ExercisesActivity initialization
            intent.putExtra("selectedQuestions", selectedQuestions);
            intent.putExtra("count", count);
            intent.putExtra("email", email);

            startActivity(intent);
        });
    }

    public void infoTheory(View view){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Γειά σου και πάλι!","Σε αυτή την οθόνη βλέπεις την θεωρία για την ενότητα που επέλεξες καθώς και το σκόρ που έχεις με βάση την απόδοσή σου στα τεστ που έχεις κάνει μέχρι τώρα. Πατώντας το μοναδικό κουμπί της οθόνης, μεταβαίνεις αυτόματα στο τεστ της ενότητας.");
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