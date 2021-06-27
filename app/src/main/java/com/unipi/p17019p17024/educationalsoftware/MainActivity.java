package com.unipi.p17019p17024.educationalsoftware;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipi.p17019p17024.educationalsoftware.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String userID, email;
    Button buttonUnits, buttonTests;
    ImageView imageView;
    RadioButton radioButton1, radioButton2, radioButton3;

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
        radioButton1 = findViewById(R.id.radioButtonEasy);
        radioButton2 = findViewById(R.id.radioButtonMedium);
        radioButton3 = findViewById(R.id.radioButtonHard);


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

        userID = getIntent().getStringExtra("userID");
        email = getIntent().getStringExtra("email");


        //Firebase Database
        database = FirebaseDatabase.getInstance();


        //Shared Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


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


        /*
        radioButton1.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
        });

        radioButton2.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
        });

        radioButton3.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
        });  */
    }

    public void buttonUnitsClick() {
        Intent intent = new Intent(getApplicationContext(), UnitsActivity.class);
        intent.putExtra("userID", currentUser.getUid());
        startActivity(intent);
    }

    public void buttonTestsClick() {
        Intent intent2 = new Intent(getApplicationContext(), RevisionTestsActivity.class);
        intent2.putExtra("userID", currentUser.getUid());
        startActivity(intent2);
    }

    /*
    public void writeSP() {
        SharedPreferences.Editor editor = preferences.edit();
        if (){
            if(checkBox2.isChecked()) {
                //Email
                editor.putString("myKeyEmail", editTextEmail.getText().toString());
                editor.apply();
                //Password
                editor.putString("myKeyPassword", editTextPassword.getText().toString());
                editor.apply();
            }
            else {
                //Email
                editor.putString("myKeyEmail", "");
                editor.apply();
                //Password
                editor.putString("myKeyPassword", "");
                editor.apply();
            }
        }
        else
        {
            if(checkBox.isChecked()) {
                //Email
                editor.putString("myKeyEmail", editTextEmail.getText().toString());
                editor.apply();
                //Password
                editor.putString("myKeyPassword", editTextPassword.getText().toString());
                editor.apply();
            }
            else {
                //Email
                editor.putString("myKeyEmail", "");
                editor.apply();
                //Password
                editor.putString("myKeyPassword", "");
                editor.apply();
            }
        }
    } */
}