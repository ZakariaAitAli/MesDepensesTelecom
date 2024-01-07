package com.gi3.mesdepensestelecom.ui.recharge;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gi3.mesdepensestelecom.Models.Recharge;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.RechargeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechargeSimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechargeSimpleFragment extends Fragment {


    private Button btnSubmit;
    private EditText editTextAmount;
    private HashMap<Integer, String> operatorHashMap;
    private Spinner spinnerOperator;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RechargeSimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RechargeSimpleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RechargeSimpleFragment newInstance(String param1, String param2) {
        RechargeSimpleFragment fragment = new RechargeSimpleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recharge_simple, container, false) ;
        operatorHashMap = new HashMap<>();
        operatorHashMap.put(1, "IAM");
        operatorHashMap.put(2, "INWI");
        operatorHashMap.put(3, "ORANGE");

        spinnerOperator = view.findViewById(R.id.spinnerOperator);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        editTextAmount = view.findViewById(R.id.editTextAmount);

        populateOperatorSpinner();

        btnSubmit.setOnClickListener(view1 -> {
            // Call a method to handle the insertion of data
            insertRechargeSimpleData();
        });
        return view ;
    }

    private void populateOperatorSpinner() {
        List<String> operatorList = new ArrayList<>(operatorHashMap.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, operatorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOperator.setAdapter(adapter);
    }


    //-------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertRechargeSimpleData() {
        // Retrieve data from UI elements
        String prix = editTextAmount.getText().toString();
        String selectedOperator = spinnerOperator.getSelectedItem().toString();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Format LocalDateTime to a string
        String date = currentDateTime.format(formatter);

        // Find the corresponding keys (numbers) from the HashMaps
        int operator = getKeyByValue(operatorHashMap, selectedOperator);

        // Assuming you have a user ID, replace "yourUserId" with the actual user ID
        int userId = 1;

        // Create an Recharge object with the retrieved data
        Recharge recharge = new Recharge( Float.parseFloat(prix), operator, userId, date);

        // Insert the RechargeRecharge data into the database
        long result = new RechargeRepository(requireContext()).insertRechargeSimple(recharge);

        // Check if the insertion was successful
        if (result != -1) {
            // Data inserted successfully, you can show a success message or perform any other actions
            Toast.makeText(requireContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to insert data, you can show an error message or perform any other actions
            Toast.makeText(requireContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to get the key from the value in a HashMap
    private int getKeyByValue(HashMap<Integer, String> hashMap, String value) {
        for (HashMap.Entry<Integer, String> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        // Handle the case where the value is not found
        return -1;
    }

}