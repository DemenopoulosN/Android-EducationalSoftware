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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipi.p17019p17024.educationalsoftware.MainActivity;
import com.unipi.p17019p17024.educationalsoftware.R;
import com.unipi.p17019p17024.educationalsoftware.databinding.FragmentHomeBinding;

import java.util.Objects;

public class HomeFragment extends Fragment {
    //ImageView
    ImageView imageViewInfo;
    //Button
    Button buttonUnits, buttonTests;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


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
            ((MainActivity) requireActivity()).buttonTestsClick();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
    public void infoMain(){
        //showMessage(getResources().getString(R.string.errorSavingImageTitle),getResources().getString(R.string.errorSavingImageMessage)+ message);
        showMessage("Βρίσκεσαι στην κεντρική σελίδα!","Πάτησε το κουμπί 'ΕΝΟΤΗΤΕΣ' για να περιηγηθείς στην θεωρία και να τεστάρεις τι έμαθες σε κάθε κεφάλαιο.\nΠάτησε το κουμπί 'ΕΠΑΝΑΛΗΠΤΙΚΑ ΤΕΣΤ' για να τεστάρεις τις γνώσεις σου συνολικά για όλα τα κεφάλαια.\nΠάτησε το κουμπί 'ΕΠΑΝΑΛΗΠΤΙΚΑ ΠΡΟΒΛΗΜΑΤΑ' για να δοκιμάσεις κάτι το παραπάνω!\nΕπιλέγοντας το κουμπί 'Η πρόοδός μου' θα δεις αναλυτικά τα σκόρ σου στα τεστ για να μπορείς να τα συγκρίνεις με τους φίλους σου!\nΕπιλέγοντας το κουμπί 'Ρυθμίσεις' μπορείς να αλλάξεις την δυσκολία των ερωτήσεων των κεφαλαίων αλλά και να αποσυνδεθείς από την εφαρμογή εφόσον το επιθυμείς.\nΚαλό παιχνίδι!");
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder();

        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.application_photo_round)
                .setPositiveButton("Ok", (dialog, which) -> {
                    //do nothing
                })
                .show();
    } */
}