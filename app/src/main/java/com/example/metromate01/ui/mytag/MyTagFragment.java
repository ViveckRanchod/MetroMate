package com.example.metromate01.ui.mytag;

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
import com.example.metromate01.databinding.FragmentMyTag2Binding;

public class MyTagFragment extends Fragment {

    private MyTagViewModel mViewModel;
    private FragmentMyTag2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MyTagViewModel myTagViewModel = new ViewModelProvider(this).get(MyTagViewModel.class);

        binding = FragmentMyTag2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textMytag;
        myTagViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}