package com.ai.educationapp.ui.leaveapplication;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.ai.educationapp.Adapters.LeaveApplicationRVAdapter;
import com.ai.educationapp.AddLeaveApplication;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.AssignmentSubjectListPOJO;
import com.ai.educationapp.POJOs.LeaveListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentLeaveApplicationBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LeaveApplicationFragment extends Fragment {

    private LeaveApplicationViewModel quizeViewModel;

    FragmentLeaveApplicationBinding binding;

    ArrayList<LeaveListPOJO> arrayList_Leave_list;
    LeaveListPOJO leaveListPOJO;

    LeaveApplicationRVAdapter leaveApplicationRVAdapter;

    String url = "", response = "";

    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizeViewModel =
                ViewModelProviders.of(this).get(LeaveApplicationViewModel.class);
        binding = FragmentLeaveApplicationBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgLeaveNotFound.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        arrayList_Leave_list = new ArrayList<>();
        new getLeaveListTask().execute();

        binding.fabAddLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddLeaveApplication.class));
            }
        });


        return root;
    }


    //=====================get Leave List task starts=========================

    public class getLeaveListTask extends AsyncTask<Void, Void, Void> {

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

            url = getString(R.string.Base_url) + getString(R.string.get_leave_list_url);
            Log.d("get Leave List url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("leave list response=>", response + "");

            getLeaveList();

            if (pdialog.isShowing()) {

                pdialog.dismiss();

            }
        }
    }

    private void getLeaveList() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgLeaveNotFound.setVisibility(View.GONE);
                binding.rvLeaveApplicationList.setVisibility(View.VISIBLE);



           /* for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                leaveListPOJO = new LeaveListPOJO(object.getString("id"),
                        object.getString("leave_desc"),
                        object.getString("leave_type"),
                        object.getString("status"),
                        object.getString("start_date"),
                        object.getString("end_date"));

                arrayList_Leave_list.add(leaveListPOJO);

            }*/

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<LeaveListPOJO>>() {
                }.getType();
                arrayList_Leave_list = gson.fromJson(jsonArray.toString(), listType);


                leaveApplicationRVAdapter = new LeaveApplicationRVAdapter(arrayList_Leave_list, getActivity());
                binding.rvLeaveApplicationList.setLayoutManager(layoutManager);
                binding.rvLeaveApplicationList.setAdapter(leaveApplicationRVAdapter);

            } else {

                binding.imgLeaveNotFound.setVisibility(View.VISIBLE);
                binding.rvLeaveApplicationList.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //=====================get Leave List task ends===========================

}