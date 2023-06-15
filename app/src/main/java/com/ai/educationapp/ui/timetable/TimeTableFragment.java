package com.ai.educationapp.ui.timetable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ai.educationapp.Adapters.TimeTableAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.TimeTablePOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentTimetableBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class TimeTableFragment extends Fragment {

    private TimeTableViewModel timeTableViewModel;

    FragmentTimetableBinding binding;

    String url = "", response = "";

    ArrayList<TimeTablePOJO> timeTablePOJOArrayList, timeTablePOJOArrayList_search;
    TimeTablePOJO timeTablePOJO;
    TimeTableAdapter timeTableAdapter;

    ProgressDialog pdialog;

    SharedPreferences sp;

    int standard_id=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timeTableViewModel =
                ViewModelProviders.of(this).get(TimeTableViewModel.class);

        binding = FragmentTimetableBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp=getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        standard_id=sp.getInt("standard_id",0);

        timeTablePOJOArrayList = new ArrayList<>();
        timeTablePOJOArrayList_search = new ArrayList<>();

        new getTimeTableData().execute();


        binding.tvMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvMonday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("1");
            }
        });

        binding.tvTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvTuesday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("2");
            }
        });

        binding.tvWedenesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvWedenesday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("3");
            }
        });

        binding.tvThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvThursday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("4");
            }
        });

        binding.tvFrieday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvFrieday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));
                filterTimetable("5");
            }
        });

        binding.tvSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvSaturday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvSunday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("6");
            }
        });

        binding.tvSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvSunday.setTextColor(Color.parseColor("#ffffff"));
                binding.tvSaturday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvFrieday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvThursday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvWedenesday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvMonday.setTextColor(Color.parseColor("#FC5A5757"));
                binding.tvTuesday.setTextColor(Color.parseColor("#FC5A5757"));

                filterTimetable("7");
            }
        });


        return root;
    }

    //=================get time table data task starts======================

    public class getTimeTableData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(getActivity());
            pdialog.setCancelable(false);
            pdialog.setMessage("Loading...");
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_timetable_detail_by_standard_id)+standard_id;
            Log.d("get timetable url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");
            getTimeTableData();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }

        }
    }

    public void getTimeTableData() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    timeTablePOJO = new TimeTablePOJO(object.getString("subject_title"),
                            object.getString("teacher_name"),
                            object.getString("start_time"),
                            object.getString("end_time"),
                            object.getString("day_id"));

                    timeTablePOJOArrayList.add(timeTablePOJO);
                    timeTablePOJOArrayList_search.add(timeTablePOJO);

                }

            } else {

                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }

            timeTableAdapter = new TimeTableAdapter(timeTablePOJOArrayList, getActivity());
            binding.lvTimetable.setAdapter(timeTableAdapter);

            filterTimetable("1");

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

    }

    //=================get time table data task ends========================


    public void filterTimetable(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("filter==", charText);

        timeTablePOJOArrayList.clear();

        if (charText.length() == 0) {
            timeTablePOJOArrayList.addAll(timeTablePOJOArrayList_search);
        } else {
            Log.d("load data", "filtered");
            for (TimeTablePOJO timeTablePOJO : timeTablePOJOArrayList_search) {
                if (timeTablePOJO.getDay_id().contains(charText)) {
                    timeTablePOJOArrayList.add(timeTablePOJO);
                }

                if (timeTablePOJOArrayList.size() == 0) {
                    binding.imgNotfound.setVisibility(View.VISIBLE);
                    binding.lvTimetable.setVisibility(View.GONE);
                } else {
                    timeTableAdapter.notifyDataSetChanged();
                    binding.imgNotfound.setVisibility(View.GONE);
                    binding.lvTimetable.setVisibility(View.VISIBLE);
                }
                timeTableAdapter.notifyDataSetChanged();
            }
        }

    }
}