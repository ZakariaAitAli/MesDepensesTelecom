package com.gi3.mesdepensestelecom.ui.reflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.DatabaseHelper;
import com.gi3.mesdepensestelecom.databinding.FragmentReflowBinding;

public class ReflowFragment extends Fragment {

    private FragmentReflowBinding binding;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reflow, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}