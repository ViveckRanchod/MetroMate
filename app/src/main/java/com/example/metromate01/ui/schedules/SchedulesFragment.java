package com.example.metromate01.ui.schedules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentActivitySchedulePageBinding;
import com.example.metromate01.ui.schedules.SchedulesViewModel;

public class SchedulesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentActivitySchedulePageBinding binding;
    Spinner schedule_droplist;
    ImageView schedule_img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SchedulesViewModel schedulesViewModel =
                new ViewModelProvider(this).get(SchedulesViewModel.class);

        binding = FragmentActivitySchedulePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        schedule_droplist = root.findViewById(R.id.schedule_droplist);
        ArrayAdapter<CharSequence> adaptArr = ArrayAdapter.createFromResource(requireContext(), R.array.areas_array, android.R.layout.simple_spinner_item);
        adaptArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_droplist.setAdapter(adaptArr);
        schedule_droplist.setSelection(0); //set default display
        schedule_droplist.setOnItemSelectedListener(this);

        schedule_img = root.findViewById(R.id.schedule_img);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int schedule_selected_id = schedule_droplist.getSelectedItemPosition();
        switch (schedule_selected_id) {
            case 0:
                schedule_img.setImageResource(R.drawable.bus_background);
                break;
            case 1:
                schedule_img.setImageResource(R.drawable.oakdene_schedule);
                break;
            case 2:
                schedule_img.setImageResource(R.drawable.bedford_schedule);
                break;
            default:
                Toast.makeText(requireContext(), "ERROR: unable to display schedule", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        schedule_img.setImageResource(R.drawable.bus_background);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
