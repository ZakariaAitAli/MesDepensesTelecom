package com.gi3.mesdepensestelecom.ui.reflow;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DashboardCompFragement extends Fragment {

    private FragmentReflowBinding binding;
    private DatabaseHelper databaseHelper;

    private Spinner yearPicker;
    public HashMap<String, Float> abonnements = new HashMap<>();
    List<BarEntry> entries = new ArrayList<BarEntry>() ;


    private Button btnStartDate;
    private Button btnEndDate;
    private Calendar calendar;
    private Button buttonSubmit;

    private final List<String> xValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboar_comp, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        calendar = Calendar.getInstance();
        //GETTING ALL THE BUTTONS
        BarChart barChart = root.findViewById(R.id.chart) ;
        btnStartDate = root.findViewById(R.id.btnStartDate);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);
        btnEndDate = root.findViewById(R.id.btnEndDate);
        //LISTENERS
        btnStartDate.setOnClickListener(view13 -> showDatePickerDialog(btnStartDate));
        btnEndDate.setOnClickListener(view12 -> showDatePickerDialog(btnEndDate));


        buttonSubmit.setOnClickListener(view1 -> {

                String startDate = btnStartDate.getText().toString();
                String endDate = btnEndDate.getText().toString();

                String selectedYearStart = startDate.split("/")[2] ;
                String selectedYearEnd = endDate.split("/")[2] ;
                int selectedMonthStart = Integer.valueOf(startDate.split("/")[1]);
            int selectedMonthEnd= Integer.valueOf(endDate.split("/")[1]);

                AbonnementRepository abo = new AbonnementRepository(getContext()) ;
                abonnements = abo.GetAbonnements(String.valueOf(selectedYearEnd)) ;
                entries.clear();

                for (int month = 1; month <= 12; month++) {

                    String key = String.format("%04d-%02d", Integer.parseInt(selectedYearStart), month);

                    if (abonnements.containsKey(key)) {
                        if (month+1 == selectedMonthStart || month+1 == selectedMonthEnd) {
                            entries.add(new BarEntry(month, abonnements.get(key)));

                        }

                    }
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

    private void showDatePickerDialog(final Button targetButton) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String dateString = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetButton.setText(dateString);
                },
                year, month, day);

        datePickerDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}