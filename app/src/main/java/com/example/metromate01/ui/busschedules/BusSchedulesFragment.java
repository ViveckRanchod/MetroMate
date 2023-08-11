package com.example.metromate01.ui.busschedules;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentBusSchedulesBinding;

public class BusSchedulesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentBusSchedulesBinding binding;
    Spinner scheduleDroplist;
    ImageView scheduleImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentBusSchedulesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        scheduleDroplist = root.findViewById(R.id.scheduleDroplist);
        ArrayAdapter<CharSequence> adaptArr = ArrayAdapter.createFromResource(requireContext(), R.array.areas_array, android.R.layout.simple_spinner_item);
        adaptArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleDroplist.setAdapter(adaptArr);
        scheduleDroplist.setSelection(0); //set default display
        scheduleDroplist.setOnItemSelectedListener(this);

        scheduleImg = root.findViewById(R.id.scheduleImg);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int schedule_selected_id = scheduleDroplist.getSelectedItemPosition();
        switch (schedule_selected_id) {
            case 0:
                scheduleImg.setImageResource(R.drawable.bus_background);
                break;
            case 1:
                scheduleImg.setImageResource(R.drawable.oakdene_schedule);
                break;
            case 2:
                scheduleImg.setImageResource(R.drawable.bedford_schedule);
                break;
            default:
                Toast.makeText(requireContext(), "ERROR: unable to display schedule", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        scheduleImg.setImageResource(R.drawable.bus_background);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

