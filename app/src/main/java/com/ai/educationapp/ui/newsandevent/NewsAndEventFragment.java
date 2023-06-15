package com.ai.educationapp.ui.newsandevent;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.Adapters.EventRVAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.EventPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentNewsandeventBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsAndEventFragment extends Fragment {

    private NewsAndEventViewModel newsAndEventViewModel;

    FragmentNewsandeventBinding binding;

    String url = "", response = "";

    ArrayList<EventPOJO> eventPOJOArrayList;
    EventPOJO eventPOJO;

    EventRVAdapter eventRVAdapter;

    RecyclerView.LayoutManager layoutManager;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsAndEventViewModel =
                ViewModelProviders.of(this).get(NewsAndEventViewModel.class);

        binding = FragmentNewsandeventBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgEventsNotFound.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(getActivity());

        eventPOJOArrayList = new ArrayList<>();

        new getEventTask().execute();

        return root;
    }

    //==================get events task starts===============

    public class getEventTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(getActivity());
            pdialog.setMessage("Loading...");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_event_url);
            Log.d("get Events url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response);
            getEvent();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }

        }
    }

    public void getEvent() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgEventsNotFound.setVisibility(View.GONE);
                binding.rvEvent.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    eventPOJO = new EventPOJO(object.getString("id"),
                            object.getString("event_title"),
                            object.getString("event_date"),
                            object.getString("event_description"));

                    eventPOJOArrayList.add(eventPOJO);

                }

                eventRVAdapter = new EventRVAdapter(eventPOJOArrayList, getActivity());
                binding.rvEvent.setLayoutManager(layoutManager);
                binding.rvEvent.setAdapter(eventRVAdapter);

            } else {

                binding.imgEventsNotFound.setVisibility(View.VISIBLE);
                binding.rvEvent.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //==================get events task starts===============

}