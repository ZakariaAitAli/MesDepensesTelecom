package com.gi3.mesdepensestelecom.ui.reflow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gi3.mesdepensestelecom.DTO.DepenseDTO;
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
import java.util.List;

public class ReflowFragment extends Fragment {

    private FragmentReflowBinding binding;
    private DatabaseHelper databaseHelper;
    private final List<String> xValues = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reflow, container, false);
        databaseHelper = new DatabaseHelper(requireContext());

        BarChart barChart = root.findViewById(R.id.chart) ;
        Spinner spinnerOptions = root.findViewById(R.id.spinnerOptions);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected option here
                String selectedOption = parentView.getItemAtPosition(position).toString();
                // Perform actions based on the selected option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });



        barChart.getAxisRight().setDrawLabels(false);

        List<BarEntry> entries = new ArrayList<BarEntry>() ;
        entries.add(new BarEntry(0,45)) ;
        entries.add(new BarEntry(1,80)) ;
        entries.add(new BarEntry(2,55)) ;
        entries.add(new BarEntry(3,25)) ;

        YAxis yAxis = barChart.getAxisLeft() ;
        yAxis.setAxisMaximum(0f) ;
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f) ;
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


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