package com.unipi.p17019p17024.educationalsoftware.ui.myProgress;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipi.p17019p17024.educationalsoftware.MainActivity;
import com.unipi.p17019p17024.educationalsoftware.R;
import com.unipi.p17019p17024.educationalsoftware.databinding.FragmentMyProgressBinding;

import org.jetbrains.annotations.NotNull;

public class MyProgressFragment extends Fragment {
    ImageView imageViewInfoMyProgress;
    Integer[] totalUnitsScoresArray = new Integer[]{0,0,0,0,0,0,0,0,0,0};


    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference studentsRef;

    private FragmentMyProgressBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyProgressViewModel myProgressViewModel = new ViewModelProvider(this).get(MyProgressViewModel.class);

        binding = FragmentMyProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyProgress;
        myProgressViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        imageViewInfoMyProgress = root.findViewById(R.id.imageViewInfoMyProgress);
        imageViewInfoMyProgress.setOnClickListener(v -> {
            ((MainActivity)getActivity()).infoMain3();
        });



        //MainActivity mainActivity = (MainActivity)getActivity();
        //String userID = mainActivity.getUserID();










        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}