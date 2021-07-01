package com.unipi.p17019p17024.educationalsoftware;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipi.p17019p17024.educationalsoftware.databinding.ActivityMainBinding;
import com.unipi.p17019p17024.educationalsoftware.ui.settings.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String userID, email;
    Button buttonUnits, buttonTests, buttonProblems;
    ImageView imageView;
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
    DatabaseReference myRef;

    //Shared Preferences
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.imageViewInfoHome);
        buttonUnits = findViewById(R.id.buttonUnits);
        buttonTests = findViewById(R.id.buttonTests);
        buttonProblems = findViewById(R.id.buttonProblems);
        radioButtonEasy = findViewById(R.id.radioButtonEasy);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHard = findViewById(R.id.radioButtonHard);


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