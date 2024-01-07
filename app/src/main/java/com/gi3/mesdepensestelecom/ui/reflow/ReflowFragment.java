package com.gi3.mesdepensestelecom.ui.reflow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.NumberPicker;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gi3.mesdepensestelecom.DTO.DepenseDTO;
import com.gi3.mesdepensestelecom.MainActivity;
import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.AbonnementRepository;
import com.gi3.mesdepensestelecom.database.DatabaseHelper;
import com.gi3.mesdepensestelecom.databinding.FragmentReflowBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReflowFragment extends Fragment {

    private FragmentReflowBinding binding;
    private DatabaseHelper databaseHelper;
    private DatePicker datePicker;
    private Spinner yearPicker;
    public HashMap<String, Float> abonnements = new HashMap<>();
    List<BarEntry> entries = new ArrayList<BarEntry>() ;


    private final List<String> xValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reflow, container, false);
        databaseHelper = new DatabaseHelper(requireContext());

        BarChart barChart = root.findViewById(R.id.chart) ;
        // Reference the Spinner
        yearPicker = root.findViewById(R.id.yearPicker);

        // Set the ArrayAdapter for the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.years_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearPicker.setAdapter(adapter);

        // Set the OnItemSelectedListener for the Spinner
        yearPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected value
                String  selectedYear = parentView.getItemAtPosition(position).toString();


                AbonnementRepository abo = new AbonnementRepository(getContext()) ;
                abonnements = abo.GetAbonnements(String.valueOf(selectedYear)) ;
                entries.clear();

                for (int month = 1; month <= 12; month++) {



                    String key = String.format("%04d-%02d", Integer.parseInt(selectedYear), month);


                    if (abonnements.containsKey(key)) {
                        entries.add(new BarEntry(month, abonnements.get(key)));
                    } else {
                        entries.add(new BarEntry(month, 0));
                    }
                    barChart.notifyDataSetChanged();
                    barChart.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if nothing is selected
            }
        });






        barChart.getAxisRight().setDrawLabels(false);

        entries.clear();
        for (int month = 1; month <= 12; month++) {


            String key = String.format("%04d-%02d", Integer.parseInt("2023"), month);

            if (abonnements.containsKey(key)) {

                entries.add(new BarEntry(month, abonnements.get(key)));
            } else {
                entries.add(new BarEntry(month, 0));
            }
        }


        YAxis yAxis = barChart.getAxisLeft() ;
        yAxis.setAxisMaximum(0f) ;
        yAxis.setAxisMaximum(400f);
        yAxis.setAxisLineWidth(2f) ;
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(100);


        BarDataSet dataSet = new BarDataSet(entries , "Months")  ;
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);


        BarData barData = new BarData(dataSet) ;
        barChart.setData(barData) ;

        barChart.getDescription().setEnabled(false) ;
        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues)) ;
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM) ;
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);






        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}