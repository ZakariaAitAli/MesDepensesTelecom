package com.gi3.mesdepensestelecom.ui.recharge;

import static android.content.Context.MODE_PRIVATE;
import static com.gi3.mesdepensestelecom.R.*;
import static com.gi3.mesdepensestelecom.ui.abonnement_form.AbonnementFragment.getKeyByValue;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.Models.Recharge;
import com.gi3.mesdepensestelecom.Models.Supplement;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.RechargeRepository;
import com.gi3.mesdepensestelecom.database.SupplementRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechargeSupplementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechargeSupplementFragment extends Fragment {

    SharedPreferences sharedPreferences;

    private Button btnSubmit;
    private EditText editTextAmount;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spinnerAbonnement;
    HashMap<Integer,String> abonnementHashMap ;

    public RechargeSupplementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RechargeSupplementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RechargeSupplementFragment newInstance(String param1, String param2) {
        RechargeSupplementFragment fragment = new RechargeSupplementFragment();
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
        View view = inflater.inflate(layout.fragment_recharge_supplement, container, false) ;

        sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE);

        String displayName = sharedPreferences.getString("display_name", "");
        String userIdString = sharedPreferences.getString("user_id", "");
        int userId = Integer.parseInt(userIdString);

        abonnementHashMap = new SupplementRepository(requireContext()).getAbonnementsMapByUserId(userId);

        spinnerAbonnement = view.findViewById(id.spinnerAbonnement);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        populateAbonnementSpinner();

        btnSubmit.setOnClickListener(view1 -> {
            // Call a method to handle the insertion of data
            insertRechargeSupplementData();
        });
        return view;
    }

    private void populateAbonnementSpinner(){
        // Ici je vais faire appel à une methode pour afficher la liste des abonnements de l'utilisateur avec id= id_session
        List<String> nameAbonnementList = new ArrayList<>(abonnementHashMap.values());

        Log.d("AbonnementListSize", String.valueOf(nameAbonnementList.size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nameAbonnementList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAbonnement.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertRechargeSupplementData() {
        // Retrieve data from UI elements
        String prix = editTextAmount.getText().toString();
        String selectedAbonnement = spinnerAbonnement.getSelectedItem().toString();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Format LocalDateTime to a string
        String date = currentDateTime.format(formatter);

        int idAbonnement = getKeyByValue(abonnementHashMap, selectedAbonnement);


        // Create an Recharge object with the retrieved data
        Supplement supplement = new Supplement(idAbonnement, Float.parseFloat(prix), date);

        // Insert the RechargeRecharge data into the database
        long result = new SupplementRepository(requireContext()).insertRechargeSupplement(supplement);

        // Check if the insertion was successful
        if (result != -1) {
            // Data inserted successfully, you can show a success message or perform any other actions
            Toast.makeText(requireContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to insert data, you can show an error message or perform any other actions
            Toast.makeText(requireContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
        }

    }
}