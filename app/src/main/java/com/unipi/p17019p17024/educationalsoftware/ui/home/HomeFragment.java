package com.unipi.p17019p17024.educationalsoftware.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipi.p17019p17024.educationalsoftware.MainActivity;
import com.unipi.p17019p17024.educationalsoftware.R;
import com.unipi.p17019p17024.educationalsoftware.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HomeFragment extends Fragment {
    //ImageView
    ImageView imageViewInfo;
    //Button
    Button buttonUnits, buttonTests, buttonProblems;
    DatabaseReference studentsRef;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity mainActivity = (MainActivity)getActivity();
        String userID = mainActivity.getUserID();


        imageViewInfo = root.findViewById(R.id.imageViewInfoHome);
        imageViewInfo.setOnClickListener(v -> {
            ((MainActivity)getActivity()).infoMain();
        });

        //When buttonUnits is clicked in HomeFragment
        buttonUnits = root.findViewById(R.id.buttonUnits);
        buttonUnits.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).buttonUnitsClick();
        });

        //When buttonTests is clicked in HomeFragment
        buttonTests = root.findViewById(R.id.buttonTests);
        buttonTests.setOnClickListener(v -> {
            studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
            studentsRef.child(userID).child(String.valueOf(10)).child("unitScore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot testSnapshot) {
                    if(Integer.parseInt(testSnapshot.getValue().toString()) >= 4){
                        ((MainActivity) requireActivity()).buttonTestsClick();
                    }
                    else{
                        showMessage("Προσοχή!", "Πρέπει να ολοκληρώσεις επιτυχώς όλες τις ενότητες με 4 ή 5 αστέρια για να μπορείς να κάνεις το επαναληπτικό τεστ.");
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    // we are showing that error message in toast
                    //Toast.makeText(MainActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });
        });

        //When buttonProblems is clicked in HomeFragment
        buttonProblems = root.findViewById(R.id.buttonProblems);
        buttonProblems.setOnClickListener(v -> {
            studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
            studentsRef.child(userID).child(String.valueOf(10)).child("unitScore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot testSnapshot2) {
                    if(Integer.parseInt(testSnapshot2.getValue().toString()) >= 4){
                        ((MainActivity) requireActivity()).buttonProblemsClick();
                    }
                    else{
                        showMessage("Προσοχή!", "Πρέπει να ολοκληρώσεις επιτυχώς όλες τις ενότητες με 4 ή 5 αστέρια για να μπορείς να κάνεις τα επαναληπτικά προβλήματα.");
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    // we are showing that error message in toast
                    //Toast.makeText(MainActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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