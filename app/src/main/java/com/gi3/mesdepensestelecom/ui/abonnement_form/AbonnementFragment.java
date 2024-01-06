package com.gi3.mesdepensestelecom.ui.abonnement_form;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.database.AbonnementRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AbonnementFragment extends Fragment {

    private Button btnStartDate;
    private Button btnEndDate;
    private Calendar calendar;

    private HashMap<Integer, String> operatorHashMap;
    private HashMap<Integer, String> TypeAbonnementHashMap;
    private Spinner spinnerType;
    private Spinner spinnerOperator;
    private EditText editTextAmount;
    private Button buttonSubmit;
    private EditText editTextAmount;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abonnement, container, false);

        operatorHashMap = new HashMap<>();
        operatorHashMap.put(1, "IAM");
        operatorHashMap.put(2, "INWI");
        operatorHashMap.put(3, "ORANGE");

        spinnerOperator = view.findViewById(R.id.spinnerOperator);
        btnStartDate = view.findViewById(R.id.btnStartDate);
        btnEndDate = view.findViewById(R.id.btnEndDate);
        spinnerType = view.findViewById(R.id.spinnerType);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        TypeAbonnementHashMap = new HashMap<>();
        TypeAbonnementHashMap.put(1, "Fibre Optique");
        TypeAbonnementHashMap.put(2, "WIFI");
        TypeAbonnementHashMap.put(3, "Mobile Appel");
        TypeAbonnementHashMap.put(4, "Fixe");
        TypeAbonnementHashMap.put(5, "Mobile Internet");

        // Initialiser le calendrier
        calendar = Calendar.getInstance();
        populateOperatorSpinner();
        populateTypeAbonnementSpinner();

        // Gérer les clics sur les boutons
        btnStartDate.setOnClickListener(view13 -> showDatePickerDialog(btnStartDate));

        btnEndDate.setOnClickListener(view12 -> showDatePickerDialog(btnEndDate));

        // Set click listener for the Submit button
        buttonSubmit.setOnClickListener(view1 -> {
            // Call a method to handle the insertion of data
            insertAbonnementData();
        });

        // Set click listener for the Submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle the insertion of data
                insertAbonnementData();
            }
        });

        // Set click listener for the Submit button
        buttonSubmit.setOnClickListener(view1 -> {
            // Call a method to handle the insertion of data
            insertAbonnementData();
        });

        return view;
    }

    private void populateOperatorSpinner() {
        List<String> operatorList = new ArrayList<>(operatorHashMap.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, operatorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOperator.setAdapter(adapter);
    }

    private void populateTypeAbonnementSpinner() {
        List<String> typeList = new ArrayList<>(TypeAbonnementHashMap.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapter);
    }


    private void showDatePickerDialog(final Button targetButton) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Mettez à jour le texte du bouton avec la date sélectionnée
                    String dateString = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetButton.setText(dateString);
                },
                year, month, day);

        datePickerDialog.show();
    }

    // Method to handle the insertion of data
    private void insertAbonnementData() {
        // Retrieve data from UI elements
        String startDate = btnStartDate.getText().toString();
        String endDate = btnEndDate.getText().toString();
        String prix = editTextAmount.getText().toString();

        // Retrieve selected items as strings from spinners
        String selectedOperator = spinnerOperator.getSelectedItem().toString();
        String selectedTypeAbonnement = spinnerType.getSelectedItem().toString();

        // Find the corresponding keys (numbers) from the HashMaps
        int operator = getKeyByValue(operatorHashMap, selectedOperator);
        int typeAbonnement = getKeyByValue(TypeAbonnementHashMap, selectedTypeAbonnement);

        // Assuming you have a user ID, replace "yourUserId" with the actual user ID
        int userId = 1;

        // Create an Abonnement object with the retrieved data
        Abonnement abonnement = new Abonnement(startDate, endDate, Float.parseFloat(prix), operator, userId, typeAbonnement);

        // Insert the Abonnement data into the database
        long result = new AbonnementRepository(requireContext()).insertAbonnement(abonnement);

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

    // Method to handle the insertion of data
    private void insertAbonnementData() {
        // Retrieve data from UI elements
        String startDate = btnStartDate.getText().toString();
        String endDate = btnEndDate.getText().toString();
        String prix = editTextAmount.getText().toString();

        // Retrieve selected items as strings from spinners
        String selectedOperator = spinnerOperator.getSelectedItem().toString();
        String selectedTypeAbonnement = spinnerType.getSelectedItem().toString();

        // Find the corresponding keys (numbers) from the HashMaps
        int operator = getKeyByValue(operatorHashMap, selectedOperator);
        int typeAbonnement = getKeyByValue(TypeAbonnementHashMap, selectedTypeAbonnement);

        // Assuming you have a user ID, replace "yourUserId" with the actual user ID
        int userId = 1;

        // Create an Abonnement object with the retrieved data
        Abonnement abonnement = new Abonnement(startDate, endDate, Float.parseFloat(prix), operator, userId, typeAbonnement);

        // Insert the Abonnement data into the database
        long result = new AbonnementRepository(requireContext()).insertAbonnement(abonnement);

        // Check if the insertion was successful
        if (result != -1) {
            // Data inserted successfully, you can show a success message or perform any other actions
            Toast.makeText(requireContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to insert data, you can show an error message or perform any other actions
            Toast.makeText(requireContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
        }
    }
