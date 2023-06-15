package com.ai.educationapp.ui.teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.ai.educationapp.Adapters.TeacherListRVAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.TeacherListPOJO;
import com.ai.educationapp.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherFragment extends Fragment {

    private TeacherViewModel teacherViewModel;

    com.ai.educationapp.databinding.FragmentTeacherBinding binding;

    String url = "", response = "";

    RecyclerView.LayoutManager layoutManager;

    ArrayList<TeacherListPOJO> teacherListPOJOArrayList;
    TeacherListPOJO teacherListPOJO;
    TeacherListRVAdapter teacherListRVAdapter;

    ProgressDialog pdialog;

    SharedPreferences sp;

    int standard_id = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teacherViewModel =
                ViewModelProviders.of(this).get(TeacherViewModel.class);
        binding = com.ai.educationapp.databinding.FragmentTeacherBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgTeacherNotFound.setVisibility(View.GONE);

        sp = getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        standard_id = sp.getInt("standard_id", 0);

        teacherListPOJOArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        new getTeacherListTask().execute();

        return root;
    }

    //=========================get all teacher task starts========================

    public class getTeacherListTask extends AsyncTask<Void, Void, Void> {
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

            url = getString(R.string.Base_url) + getString(R.string.teacher_list_url) + standard_id;
            Log.d("get Teachet List Url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response = >", response + "");

            getTeacherList();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
        }
    }

    public void getTeacherList() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgTeacherNotFound.setVisibility(View.GONE);
                binding.rvTeacherlist.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    teacherListPOJO = new TeacherListPOJO(object.getString("subject_title"), object.getString("teacher_name"));

                    teacherListPOJOArrayList.add(teacherListPOJO);
                }

                teacherListRVAdapter = new TeacherListRVAdapter(teacherListPOJOArrayList, getActivity());
                binding.rvTeacherlist.setLayoutManager(layoutManager);
                binding.rvTeacherlist.setAdapter(teacherListRVAdapter);

            } else {

                binding.imgTeacherNotFound.setVisibility(View.VISIBLE);
                binding.rvTeacherlist.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //=========================get all teacher task ends==========================

};