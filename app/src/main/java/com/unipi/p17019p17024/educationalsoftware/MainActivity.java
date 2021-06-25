package com.unipi.p17019p17024.educationalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

    //
    //Button
    //
    Button buttonUnits;
    Button buttonTests;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.unipi.p17019p17024.educationalsoftware.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
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
        myRef = database.getReference("message");



        buttonUnits = findViewById(R.id.buttonUnits);
        buttonUnits.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UnitsActivity.class);
            intent.putExtra("userID", currentUser.getUid());
            startActivity(intent);
        });

        buttonTests = findViewById(R.id.buttonTests);
        buttonTests.setOnClickListener(v -> {
            Intent intent2 = new Intent(getApplicationContext(), RevisionTestsActivity.class);
            intent2.putExtra("userID", currentUser.getUid());
            startActivity(intent2);
        });


    }

}