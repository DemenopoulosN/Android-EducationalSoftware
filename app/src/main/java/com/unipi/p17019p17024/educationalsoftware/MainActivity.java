package com.unipi.p17019p17024.educationalsoftware;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipi.p17019p17024.educationalsoftware.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String userID, email;
    Button buttonUnits, buttonTests, buttonProblems, buttonLogOut;
    ImageView imageView1, imageView2, imageView3;
    RadioButton radioButtonEasy, radioButtonMedium, radioButtonHard;
    String difficulty = "Easy";

    //Intents for RevisionTestsActivity initialization
    ArrayList<String> selectedQuestions = new ArrayList<>();
    Integer count = 1;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference studentsRef;

    //Shared Preferences
    SharedPreferences preferences;


    ProgressBar progressBarUnit1;
    //int pStatus = 0;
    //private Handler handler = new Handler();
    TextView textViewProgressUnit1;
    Double[] totalUnitsScoresArray = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    TextView textView11;
    Integer score1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView1 = findViewById(R.id.imageViewInfoHome);
        imageView2 = findViewById(R.id.imageViewInfoSettings);
        imageView3 = findViewById(R.id.imageViewInfoMyProgress);
        buttonUnits = findViewById(R.id.buttonUnits);
        buttonTests = findViewById(R.id.buttonTests);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonProblems = findViewById(R.id.buttonProblems);
        radioButtonEasy = findViewById(R.id.radioButtonEasy);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHard = findViewById(R.id.radioButtonHard);
        textViewProgressUnit1 = findViewById(R.id.textViewProgressUnit1);
        progressBarUnit1 = findViewById(R.id.progressBarUnit1);
        textView11 = findViewById(R.id.textView11);



        com.unipi.p17019p17024.educationalsoftware.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_myProgress, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //
        //If you don't want to use the action bar. Remove the below code
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //Or change the NoActionBar to DarkActionBar (στο AndroidManifest ή στο themes) or any others.
        //
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //GetIntent
        userID = getIntent().getStringExtra("userID");
        email = getIntent().getStringExtra("email");


        //Firebase Database
        database = FirebaseDatabase.getInstance();


        //Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //For saving Shared Preferences on launching MainActivity when opening the app
        difficulty = preferences.getString("difficultyKey", "Easy");


        //When buttonUnits is clicked in MainActivity
        buttonUnits = findViewById(R.id.buttonUnits);
        buttonUnits.setOnClickListener(v -> {
            buttonUnitsClick();
        });

        //When buttonTests is clicked in MainActivity
        buttonTests = findViewById(R.id.buttonTests);
        buttonTests.setOnClickListener(v -> {
            buttonTestsClick();
        });

        //When buttonProblems is clicked in MainActivity
        buttonProblems = findViewById(R.id.buttonProblems);
        buttonProblems.setOnClickListener(v -> {
            buttonProblemsClick();
        });






        /*
        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(int i = 0; i <= 9; i++){
                    for(int j = 1; j <= 10; j++){
                        totalUnitsScoresArray[i] = totalUnitsScoresArray[i] + Double.parseDouble(dataSnapshot.child(String.valueOf(i + 1)).child(String.valueOf(j)).child("score").getValue().toString());
                    }
                    totalUnitsScoresArray[i] = totalUnitsScoresArray[i]/10;
                    score1 = (int) Math.round(totalUnitsScoresArray[0]);
                    textView11.setText(score1);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // we are showing that error message in toast
                Toast.makeText(MainActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        }); */
    }

    public void buttonUnitsClick() {
        Intent intent = new Intent(getApplicationContext(), UnitsActivity.class);
        intent.putExtra("userID", currentUser.getUid());
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void buttonTestsClick() {
        Intent intent2 = new Intent(getApplicationContext(), RevisionTestsActivity.class);
        intent2.putExtra("userID", currentUser.getUid());

        //Intents for RevisionTestsActivity initialization
        intent2.putExtra("selectedQuestions", selectedQuestions);
        intent2.putExtra("count", count);
        startActivity(intent2);
    }

    public void buttonProblemsClick() {
        Intent intent3 = new Intent(getApplicationContext(), ProblemsActivity.class);
        intent3.putExtra("userID", currentUser.getUid());
        startActivity(intent3);
    }


    public void setDifficulty(String diff) {
        //Updating SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("difficultyKey", diff);
        editor.apply();
        //Updating difficulty variable
        difficulty = preferences.getString("difficultyKey", diff);

    }

    public String getDifficulty() {
        return difficulty;
    }

    public void infoMain(){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Βρίσκεσαι στην κεντρική σελίδα!","Πάτησε το κουμπί 'ΕΝΟΤΗΤΕΣ' για να περιηγηθείς στην θεωρία και να τεστάρεις τι έμαθες σε κάθε κεφάλαιο.\nΠάτησε το κουμπί 'ΕΠΑΝΑΛΗΠΤΙΚΑ ΤΕΣΤ' για να τεστάρεις τις γνώσεις σου συνολικά για όλα τα κεφάλαια.\nΠάτησε το κουμπί 'ΕΠΑΝΑΛΗΠΤΙΚΑ ΠΡΟΒΛΗΜΑΤΑ' για να δοκιμάσεις το κάτι παραπάνω!\nΕπιλέγοντας το κουμπί 'Η πρόοδός μου' θα δεις αναλυτικά τα σκόρ σου στα τεστ για να μπορείς να τα συγκρίνεις με τους φίλους σου!\nΕπιλέγοντας το κουμπί 'Ρυθμίσεις' μπορείς να αλλάξεις την δυσκολία των ερωτήσεων των κεφαλαίων αλλά και να αποσυνδεθείς από την εφαρμογή εφόσον το επιθυμείς.\nΚαλό παιχνίδι!");
    }

    public void infoMain2(){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Γειά σου!","Σε αυτή τη σελίδα μπορείς να επιλέξεις ή να αλλάξεις τη δυσκολία των ερωτήσεων στα τεστ κάθε ενότητας! Ακόμη, εφόσον το επιθυμείς, μπορείς να αποσυνδεθείς από την εφαρμογή. Μην ανησυχείς! Τα σκορ που έχεις πετύχει και η πρόοδος που έχεις κάνει μέχρι τώρα είναι αποθηκευμένα και ασφαλή στη βάση δεδομένων μας. Θα τα ξαναβρείς όποτε ξανά-συνδεθείς!");
    }

    public void infoMain3(){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Καλώς ήρθες στην σελίδα της προόδου σου!","Σε αυτή τη σελίδα μπορείς να δεις συγκεντρωμένη όλη την πρόοδο και τα σκορ σου για κάθε ενότητα αλλά και για τα επαναλητπικά τέστ και τα προβλήματα!");
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

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent4 = new Intent(getApplicationContext(), EntryActivity.class);
        startActivity(intent4);
    }

    /*public void setProgressBar(){
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circle);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarUnit1);
        progressBar.setProgress(0);   // Main Progress
        progressBar.setSecondaryProgress(100); // Secondary Progress
        progressBar.setMax(100); // Maximum Progress
        progressBar.setProgressDrawable(drawable);


        textViewProgressUnit1 = (TextView) findViewById(R.id.textViewProgressUnit1);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            textViewProgressUnit1.setText(pStatus + "%");

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(8); //thread will take approx 1.5 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }*/

    public void setProgressBar(Integer[] scoresArray){
        //int score1 = scoresArray[0]*20;
        //progressBarUnit1.setProgress(scoresArray[0]);
        //textViewProgressUnit1.setText(score1+"%");
    }


    public String getUserID() {
        return userID;
    }


    /* studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(int i = 0; i <= 9; i++){
                    totalUnitsScoresArray[i] = Integer.parseInt(dataSnapshot.child(String.valueOf(i + 1)).child("unitScore").getValue().toString());
                    Log.d("score", String.valueOf(totalUnitsScoresArray[i]));
                }
                int score1 = totalUnitsScoresArray[0]*20;
                progressBarUnit1.setProgress(score1);
                textViewProgressUnit1.setText(score1+"%");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        }); */

}