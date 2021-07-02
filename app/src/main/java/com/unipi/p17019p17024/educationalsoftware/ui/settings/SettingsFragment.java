package com.unipi.p17019p17024.educationalsoftware.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.unipi.p17019p17024.educationalsoftware.MainActivity;
import com.unipi.p17019p17024.educationalsoftware.R;
import com.unipi.p17019p17024.educationalsoftware.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    RadioButton radioButtonEasy, radioButtonMedium, radioButtonHard;
    String selectedDifficulty;
    ImageView imageViewInfoSettings;


    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        radioButtonEasy = root.findViewById(R.id.radioButtonEasy);
        radioButtonMedium = root.findViewById(R.id.radioButtonMedium);
        radioButtonHard = root.findViewById(R.id.radioButtonHard);

        imageViewInfoSettings = root.findViewById(R.id.imageViewInfoSettings);
        imageViewInfoSettings.setOnClickListener(v -> {
            ((MainActivity)getActivity()).infoMain2();
        });


        //Starting Activity with pre-selected difficulty
        MainActivity mainActivity = new MainActivity();
        selectedDifficulty = mainActivity.getDifficulty();

        if(selectedDifficulty.equals("Easy")){
            radioButtonEasy.setChecked(true);
            radioButtonMedium.setChecked(false);
            radioButtonHard.setChecked(false);
        }
        else if(selectedDifficulty.equals("Medium")){
            radioButtonEasy.setChecked(false);
            radioButtonMedium.setChecked(true);
            radioButtonHard.setChecked(false);
        }
        else{
            radioButtonEasy.setChecked(false);
            radioButtonMedium.setChecked(false);
            radioButtonHard.setChecked(true);
        }

        //When radioButtonEasy is clicked in SettingsFragment
        radioButtonEasy.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setDifficulty("Easy");
        });

        //When radioButtonMedium is clicked in SettingsFragment
        radioButtonMedium.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setDifficulty("Medium");
        });

        //When radioButtonHard is clicked in SettingsFragment
        radioButtonHard.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setDifficulty("Hard");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}