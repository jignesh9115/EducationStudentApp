package com.ai.educationapp.ui.exam;

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

import com.ai.educationapp.Adapters.ExamListAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.ExamTypePOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentExamBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamFragment extends Fragment {

    private ExamViewModel examViewModel;

    FragmentExamBinding binding;

    String url = "", response = "";

    ArrayList<ExamTypePOJO> examTypePOJOArrayList;
    ExamTypePOJO examTypePOJO;

    ExamListAdapter examListAdapter;

    ProgressDialog pdialog;

    SharedPreferences sp;

    int standard_id = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        examViewModel =
                ViewModelProviders.of(this).get(ExamViewModel.class);
        binding = FragmentExamBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgExamNotFound.setVisibility(View.GONE);


        sp = getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        standard_id = sp.getInt("standard_id", 0);


        examTypePOJOArrayList = new ArrayList<>();
        new getExamDataTask().execute();

        return root;
    }


    //====================get exam data task starts==============================

    public class getExamDataTask extends AsyncTask<Void, Void, Void> {
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

            url = getString(R.string.Base_url) + getString(R.string.get_exam_type_detail_url) + standard_id;
            Log.d("get exam data url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            getExamType();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }

        }
    }

    public void getExamType() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgExamNotFound.setVisibility(View.GONE);
                binding.lvExam.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    examTypePOJO = new ExamTypePOJO(object.getString("exam_type_title"), object.getString("exam_type_id"));

                    examTypePOJOArrayList.add(examTypePOJO);
                }

                examListAdapter = new ExamListAdapter(examTypePOJOArrayList, getActivity());
                binding.lvExam.setAdapter(examListAdapter);

            } else {

                binding.imgExamNotFound.setVisibility(View.VISIBLE);
                binding.lvExam.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //====================get exam data task ends================================
}