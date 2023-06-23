package com.example.metromate01.ui.bushome;

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
import com.example.metromate01.databinding.FragmentBusHomeBinding;

public class BusHomeFragment extends Fragment {

    private FragmentBusHomeBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        BusHomeViewModel busHomeViewModel = new ViewModelProvider(this).get(BusHomeViewModel.class);

        binding = FragmentBusHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBusHome;
        busHomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}