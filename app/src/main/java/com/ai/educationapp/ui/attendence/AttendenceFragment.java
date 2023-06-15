package com.ai.educationapp.ui.attendence;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ai.educationapp.Adapters.AttendanceCalendarAdapter;
import com.ai.educationapp.GDateTime;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.CalendarValuesPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentAttendenceBinding;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttendenceFragment extends Fragment {

    private AttendenceViewModel slideshowViewModel;

    FragmentAttendenceBinding binding;

    String url = "", response = "";

    ArrayList<CalendarValuesPOJO> calendarValuesPOJOArrayList;
    CalendarValuesPOJO calendarValuesPOJO;

    AttendanceCalendarAdapter attendanceCalendarAdapter;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    String month = "", year = "";

    GDateTime gDateTime = new GDateTime();


    SharedPreferences sp;

    int student_id = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(AttendenceViewModel.class);
        binding = FragmentAttendenceBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        student_id = sp.getInt("student_id", 0);

        calendarValuesPOJOArrayList = new ArrayList<>();
        loadMonth();

        binding.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.add(Calendar.MONTH, -1);

                loadMonth();
            }
        });

        binding.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.add(Calendar.MONTH, 1);

                loadMonth();

            }
        });

        binding.tvTitleMonthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance(Locale.ENGLISH);

                loadMonth();

            }
        });


        //========================code for pie chart starts===================

        binding.piechart.setUsePercentValues(true);
        binding.piechart.setHighlightPerTapEnabled(true);

        Description description = new Description();
        description.setEnabled(false);
        description.setText("Attendance Chart");
        description.setTextSize(14f);
        binding.piechart.setDescription(description);


        binding.piechart.setHoleRadius(50);
        binding.piechart.setTransparentCircleRadius(60);
        binding.piechart.setTransparentCircleColor(Color.RED);
        binding.piechart.setTransparentCircleAlpha(20);
        binding.piechart.setEntryLabelTextSize(4f);
        binding.piechart.setDrawEntryLabels(false);
        binding.piechart.animateXY(1400, 1500);


        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(70, "Present"));
        pieEntries.add(new PieEntry(20, "Absent"));
        pieEntries.add(new PieEntry(10, "Holiday"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, " ");
        PieData pieData = new PieData(pieDataSet);
        binding.piechart.setData(pieData);


        pieDataSet.setValueTextSize(6);
        pieDataSet.setValueTextColor(Color.WHITE);
        /*pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setValueLinePart1Length(0.3f);
        pieDataSet.setValueLinePart2Length(0.4f);*/

        pieDataSet.setColors(Color.parseColor("#2ECC71"), Color.parseColor("#E74C3C"), Color.parseColor("#3498DB"));
        pieDataSet.setSliceSpace(1);
        pieDataSet.setForm(Legend.LegendForm.CIRCLE);
        pieDataSet.setValueFormatter(new PercentFormatter(binding.piechart));

        binding.piechart.invalidate();

        //========================code for pie chart ends=====================


        return root;
    }


    //http://projects.drbbagpks.org/educationsystem/API/view_attendance.php?month_val=12&year_val=2020

    //===================get attendace data task starts=================

    public class getAttendanceDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {


            Log.d("get Attendance url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            getAttendanceData();

        }
    }

    private void getAttendanceData() {

        calendarValuesPOJOArrayList.clear();

        try {
            JSONObject jsonObject = new JSONObject(response + "");

            JSONArray datarray = jsonObject.getJSONArray("data");

            for (int i = 0; i < datarray.length(); i++) {

                JSONObject object = datarray.getJSONObject(i);

                if (object.has("date")) {
                    calendarValuesPOJO = new CalendarValuesPOJO(object.getString("day"),
                            object.getString("status"),
                            object.getString("holiday"),
                            object.getString("date"));

                } else {

                    calendarValuesPOJO = new CalendarValuesPOJO(object.getString("day"),
                            object.getString("status"),
                            object.getString("holiday"));

                }


                calendarValuesPOJOArrayList.add(calendarValuesPOJO);
            }


            attendanceCalendarAdapter = new AttendanceCalendarAdapter(calendarValuesPOJOArrayList, getActivity());
            binding.gvCalender.setAdapter(attendanceCalendarAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //===================get attendace data task ends===================


    private void loadMonth() {

        calendar.getActualMinimum(Calendar.YEAR);
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        SimpleDateFormat monthformat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat yearformat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String monthName = format.format(calendar.getTime());
        binding.tvTitleMonthYear.setText(monthName);

        month = monthformat.format(calendar.getTime());
        year = yearformat.format(calendar.getTime());

        //Toast.makeText(getActivity(), "month=>" + gDateTime.getMonth_number(month) + "\nyear=>" + year, Toast.LENGTH_SHORT).show();

        url = getString(R.string.Base_url) + getString(R.string.view_attendance_url) + gDateTime.getMonth_number(month) + "&year_val=" + year + "&student_id=" + student_id;

        new getAttendanceDataTask().execute(url);

    }

}