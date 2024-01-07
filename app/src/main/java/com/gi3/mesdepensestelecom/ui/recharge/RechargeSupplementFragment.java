package com.gi3.mesdepensestelecom.ui.recharge;

import static com.gi3.mesdepensestelecom.R.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.RechargeRepository;
import com.gi3.mesdepensestelecom.database.SupplementRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechargeSupplementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechargeSupplementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private HashMap<Integer, String> TypeAbonnementHashMap;
    private Spinner spinnerAbonnement;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_recharge_supplement, container, false) ;
        TypeAbonnementHashMap = new HashMap<>();
        TypeAbonnementHashMap.put(1, "Fibre Optique");
        TypeAbonnementHashMap.put(2, "WIFI");
        TypeAbonnementHashMap.put(3, "Mobile Appel");
        TypeAbonnementHashMap.put(4, "Fixe");
        TypeAbonnementHashMap.put(5, "Mobile Internet");


        spinnerAbonnement = view.findViewById(id.spinnerAbonnement);
        populateTypeAbonnementSpinner();
        return view;
    }

    private void populateTypeAbonnementSpinner(){
        // Ici je vais faire appel à une methode pour afficher la liste des abonnements de l'utilisateur avec id= id_session
        HashMap<Integer,String> abonnementHashMap = new SupplementRepository(requireContext()).getAbonnementsMapByUserId(1);
        List<String> nameAbonnementList = new ArrayList<>(abonnementHashMap.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nameAbonnementList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAbonnement.setAdapter(adapter);
    }

}