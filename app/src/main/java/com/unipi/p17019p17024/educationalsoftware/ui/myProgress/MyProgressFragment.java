package com.unipi.p17019p17024.educationalsoftware.ui.myProgress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.unipi.p17019p17024.educationalsoftware.databinding.FragmentMyProgressBinding;

public class MyProgressFragment extends Fragment {

    private FragmentMyProgressBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyProgressViewModel myProgressViewModel = new ViewModelProvider(this).get(MyProgressViewModel.class);

        binding = FragmentMyProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyProgress;
        myProgressViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}