package com.unipi.p17019p17024.educationalsoftware.ui.home;

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
    //
    //ImageView
    //
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
        imageViewInfo.setOnClickListener(v -> Toast.makeText(getContext(), "This is my Toast message!",
                Toast.LENGTH_LONG).show());

        //When buttonUnits is clicked in HomeFragment
        buttonUnits = root.findViewById(R.id.buttonUnits);
        buttonUnits.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).buttonUnitsClick();
        });
        //When buttonTests is clicked in HomeFragment
        buttonTests = root.findViewById(R.id.buttonTests);
        buttonTests.setOnClickListener(v -> {
            ((MainActivity)getActivity()).buttonTestsClick();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}