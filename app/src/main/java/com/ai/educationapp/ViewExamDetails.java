package com.ai.educationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ai.educationapp.Adapters.ExamDetailAdapter;
import com.ai.educationapp.POJOs.ExamDetailsPOJO;
import com.ai.educationapp.databinding.ActivityViewExamDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewExamDetails extends AppCompatActivity {


    ActivityViewExamDetailsBinding binding;

    String url = "", response = "", exam_type_title = "", exam_type_id = "";

    ArrayList<ExamDetailsPOJO> examDetailsPOJOArrayList;
    ExamDetailsPOJO examDetailsPOJO;

    ExamDetailAdapter examDetailAdapter;

    int toatl_marks = 0, subject_marks = 0;

    SharedPreferences sp;

    int standard_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewExamDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        standard_id = sp.getInt("standard_id", 0);

        Bundle bundle = getIntent().getExtras();
        exam_type_id = bundle.getString("exam_type_id");
        exam_type_title = bundle.getString("exam_type_title");

        binding.tvExamTitleViewDetail.setText("" + exam_type_title);

        examDetailsPOJOArrayList = new ArrayList<>();
        new getExamDetailTask().execute();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //===============get exam details task starts===============

    public class getExamDetailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_exam_detail_url) + standard_id + "&exam_type_id=" + exam_type_id;
            Log.d("get exam detail url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            getExamDetails();

        }
    }

    public void getExamDetails() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                examDetailsPOJO = new ExamDetailsPOJO(object.getString("exam_title"),
                        object.getString("exam_standard"),
                        object.getString("exam_date"),
                        object.getString("subject_title"),
                        object.getString("exam_total_marks"),
                        object.getString("exam_type_title"),
                        object.getString("exam_id"));

                examDetailsPOJOArrayList.add(examDetailsPOJO);

                subject_marks = Integer.parseInt(object.getString("exam_total_marks"));
                toatl_marks = toatl_marks + subject_marks;
            }

            examDetailAdapter = new ExamDetailAdapter(examDetailsPOJOArrayList, getApplicationContext());
            binding.lvSubjects.setAdapter(examDetailAdapter);

            binding.tvGrandTotalMarks.setText("" + toatl_marks);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //===============get exam details task ends=================
}