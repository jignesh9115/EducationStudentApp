package com.ai.educationapp.ui.holiday;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.Adapters.HolidayRVAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.HolidayPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentHolidayBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HolidayFragment extends Fragment {

    private HolidayViewModel holidayViewModel;

    FragmentHolidayBinding binding;

    ArrayList<HolidayPOJO> holidayPOJOArrayList;
    HolidayPOJO holidayPOJO;
    HolidayRVAdapter holidayRVAdapter;

    String url = "", response = "";

    RecyclerView.LayoutManager layoutManager;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        holidayViewModel =
                ViewModelProviders.of(this).get(HolidayViewModel.class);
        binding = FragmentHolidayBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgHolidayNotFound.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(getActivity());

        holidayPOJOArrayList = new ArrayList<>();
        new getHolidayList().execute();

        return root;
    }


    //=================get holidays list task starts=====================

    public class getHolidayList extends AsyncTask<Void, Void, Void> {
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

            url = getString(R.string.Base_url) + getString(R.string.get_holiday_list_url);
            Log.d("get Holiday List url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            if (pdialog.isShowing()) {

                pdialog.dismiss();

            }

            getHoliday();
        }
    }

    public void getHoliday() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgHolidayNotFound.setVisibility(View.GONE);
                binding.rvHoliday.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    holidayPOJO = new HolidayPOJO(object.getString("id"),
                            object.getString("holiday_title"),
                            object.getString("holiday_date_start"),
                            object.getString("holiday_date_end"));

                    holidayPOJOArrayList.add(holidayPOJO);
                }

                holidayRVAdapter = new HolidayRVAdapter(holidayPOJOArrayList, getActivity());
                binding.rvHoliday.setLayoutManager(layoutManager);
                binding.rvHoliday.setAdapter(holidayRVAdapter);


            } else {

                binding.imgHolidayNotFound.setVisibility(View.VISIBLE);
                binding.rvHoliday.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //=================get holidays list task ends=======================

}