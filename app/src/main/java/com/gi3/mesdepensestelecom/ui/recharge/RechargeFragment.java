package com.gi3.mesdepensestelecom.ui.recharge;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gi3.mesdepensestelecom.R;

public class RechargeFragment extends Fragment {

    private RechargeViewModel mViewModel;

    public static RechargeFragment newInstance() {
        return new RechargeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recharge, container, false);
        Button RechargeSimple = root.findViewById(R.id.btnRechargeSimple);
        Button RechargeSimpleSupp = root.findViewById(R.id.btnSupplement);


        // Set an OnClickListener to navigate to the register fragment
        RechargeSimple.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.fragment_recharge_simple);
        });
        RechargeSimpleSupp.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.fragment_recharge_supplement);
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RechargeViewModel.class);
        // TODO: Use the ViewModel
    }



}