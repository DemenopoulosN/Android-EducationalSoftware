package com.unipi.p17019p17024.educationalsoftware.ui.myProgress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    ImageView imageViewUnit1ScoreMyProgress, imageViewUnit2ScoreMyProgress, imageViewUnit3ScoreMyProgress, imageViewUnit4ScoreMyProgress, imageViewUnit5ScoreMyProgress, imageViewUnit6ScoreMyProgress, imageViewUnit7ScoreMyProgress, imageViewUnit8ScoreMyProgress, imageViewUnit9ScoreMyProgress, imageViewUnit10ScoreMyProgress;
    TextView textViewProblemsScoreMyProgress, textViewRevisionTestScoreMyProgress;
    Integer[] totalUnitsScoresArray = new Integer[]{0,0,0,0,0,0,0,0,0,0};
    int score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    int problemsScore, revisionTestScore;
    String userID;


    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference studentsRef;

    private FragmentMyProgressBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyProgressViewModel myProgressViewModel = new ViewModelProvider(this).get(MyProgressViewModel.class);

        binding = FragmentMyProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textMyProgress;
        //myProgressViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //ImageView
        imageViewUnit1ScoreMyProgress = root.findViewById(R.id.imageViewUnit1ScoreMyProgress);
        imageViewUnit2ScoreMyProgress = root.findViewById(R.id.imageViewUnit2ScoreMyProgress);
        imageViewUnit3ScoreMyProgress = root.findViewById(R.id.imageViewUnit3ScoreMyProgress);
        imageViewUnit4ScoreMyProgress = root.findViewById(R.id.imageViewUnit4ScoreMyProgress);
        imageViewUnit5ScoreMyProgress = root.findViewById(R.id.imageViewUnit5ScoreMyProgress);
        imageViewUnit6ScoreMyProgress = root.findViewById(R.id.imageViewUnit6ScoreMyProgress);
        imageViewUnit7ScoreMyProgress = root.findViewById(R.id.imageViewUnit7ScoreMyProgress);
        imageViewUnit8ScoreMyProgress = root.findViewById(R.id.imageViewUnit8ScoreMyProgress);
        imageViewUnit9ScoreMyProgress = root.findViewById(R.id.imageViewUnit9ScoreMyProgress);
        imageViewUnit10ScoreMyProgress = root.findViewById(R.id.imageViewUnit10ScoreMyProgress);
        //TextView
        textViewProblemsScoreMyProgress = root.findViewById(R.id.textViewProblemsScoreMyProgress);
        textViewRevisionTestScoreMyProgress = root.findViewById(R.id.textViewRevisionTestScoreMyProgress);


        imageViewInfoMyProgress = root.findViewById(R.id.imageViewInfoMyProgress);
        imageViewInfoMyProgress.setOnClickListener(v -> {
            ((MainActivity)getActivity()).infoMain3();
        });



        MainActivity mainActivity = (MainActivity)getActivity();
        String userID = mainActivity.getUserID();
        //userID = getArguments().getString("userID");
        Log.d("Fragment's userID: ", userID);
        //return inflater.inflate(R.layout.fragment_my_progress, container, false);


        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");
        studentsRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshotMyProgress) {
                for(int i = 0; i <= 9; i++){
                    totalUnitsScoresArray[i] = Integer.parseInt(dataSnapshotMyProgress.child(String.valueOf(i + 1)).child("unitScore").getValue().toString());
                }

                //unit1
                score1 = totalUnitsScoresArray[0];
                if(score1 == 0){
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score1 == 1){
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score1 == 2){
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score1 == 3){
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score1 == 4){
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit1ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit2
                score2 = totalUnitsScoresArray[1];
                if(score2 == 0){
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score2 == 1){
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score2 == 2){
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score2 == 3){
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score2 == 4){
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit2ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit3
                score3 = totalUnitsScoresArray[2];
                if(score3 == 0){
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score3 == 1){
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score3 == 2){
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score3 == 3){
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score3 == 4){
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit3ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit4
                score4 = totalUnitsScoresArray[3];
                if(score4 == 0){
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score4 == 1){
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score4 == 2){
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score4 == 3){
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score4 == 4){
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit4ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit5
                score5 = totalUnitsScoresArray[4];
                if(score5 == 0){
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score5 == 1){
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score5 == 2){
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score5 == 3){
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score5 == 4){
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit5ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit6
                score6 = totalUnitsScoresArray[5];
                if(score6 == 0){
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score6 == 1){
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score6 == 2){
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score6 == 3){
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score6 == 4){
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit6ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit7
                score7 = totalUnitsScoresArray[6];
                if(score7 == 0){
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score7 == 1){
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score7 == 2){
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score7 == 3){
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score7 == 4){
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit7ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit8
                score8 = totalUnitsScoresArray[7];
                if(score8 == 0){
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score8 == 1){
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score8 == 2){
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score8 == 3){
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score8 == 4){
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit8ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit9
                score9 = totalUnitsScoresArray[8];
                if(score9 == 0){
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score9 == 1){
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score9 == 2){
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score9 == 3){
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score9 == 4){
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit9ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                //unit10
                score10 = totalUnitsScoresArray[9];
                if(score10 == 0){
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars0);
                }
                else if(score10 == 1){
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars1);
                }
                else if(score10 == 2){
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars2);
                }
                else if(score10 == 3){
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars3);
                }
                else if(score10 == 4){
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars4);
                }
                else{
                    imageViewUnit10ScoreMyProgress.setImageResource(R.drawable.stars5);
                }

                problemsScore = Integer.parseInt(dataSnapshotMyProgress.child("problemsScore").getValue().toString());
                textViewProblemsScoreMyProgress.setText(String.valueOf(problemsScore));

                revisionTestScore = Integer.parseInt(dataSnapshotMyProgress.child("revisionTestScore").getValue().toString());
                textViewRevisionTestScoreMyProgress.setText(String.valueOf(revisionTestScore));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // we are showing that error message in toast
                //Toast.makeText(StudentsDataActivity.this, getResources().getString(R.string.errorToast), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}