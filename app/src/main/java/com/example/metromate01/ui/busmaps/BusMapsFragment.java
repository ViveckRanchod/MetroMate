package com.example.metromate01.ui.busmaps;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentBusMapsBinding;

public class BusMapsFragment extends Fragment {

    private FragmentBusMapsBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       BusMapsViewModel busMapsViewModel = new ViewModelProvider(this).get(BusMapsViewModel.class);
       binding = FragmentBusMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBusMaps;
        busMapsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}