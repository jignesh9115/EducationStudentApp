package com.ai.educationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ai.educationapp.Adapters.ExamDetailAdapter;
import com.ai.educationapp.Adapters.ResultDetailAdapter;
import com.ai.educationapp.Adapters.ResultExamListAdapter;
import com.ai.educationapp.POJOs.ExamDetailsPOJO;
import com.ai.educationapp.POJOs.ResultDetailsPOJO;
import com.ai.educationapp.databinding.ActivityViewResultDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewResultDetails extends AppCompatActivity {

    ActivityViewResultDetailsBinding binding;

    String url = "", response = "", exam_type_title = "", exam_type_id = "";

    ArrayList<ResultDetailsPOJO> resultDetailsPOJOArrayList;
    ResultDetailsPOJO resultDetailsPOJO;

    ResultDetailAdapter resultDetailAdapter;

    int toatl_marks = 0, subject_marks = 0;
    int toatl_obtain_marks = 0, subject_obtain_marks = 0;

    SharedPreferences sp;

    int standard_id = 0, student_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewResultDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("student_id", Context.MODE_PRIVATE);
        student_id = sp.getInt("student_id", 0);
        standard_id = sp.getInt("standard_id", 0);


        Bundle bundle = getIntent().getExtras();
        exam_type_id = bundle.getString("exam_type_id");
        exam_type_title = bundle.getString("exam_type_title");

        binding.tvExamTitleViewResult.setText("" + exam_type_title);

        resultDetailsPOJOArrayList = new ArrayList<>();
        new getResultDetailTask().execute();

        binding.imgBackViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //===============get exam details task starts===============

    public class getResultDetailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_result_detail_url) + standard_id + "&exam_type_id=" + exam_type_id + "&student_id=" + student_id;
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
                resultDetailsPOJO = new ResultDetailsPOJO(object.getString("exam_title"),
                        object.getString("exam_standard"),
                        object.getString("exam_date"),
                        object.getString("subject_title"),
                        object.getString("exam_total_marks"),
                        object.getString("exam_type_title"),
                        object.getString("exam_id"),
                        object.getString("mark_obtain"));

                resultDetailsPOJOArrayList.add(resultDetailsPOJO);

                subject_marks = Integer.parseInt(object.getString("exam_total_marks"));
                toatl_marks = toatl_marks + subject_marks;

                subject_obtain_marks = Integer.parseInt(object.getString("mark_obtain"));
                toatl_obtain_marks = toatl_obtain_marks + subject_obtain_marks;
            }

            resultDetailAdapter = new ResultDetailAdapter(resultDetailsPOJOArrayList, getApplicationContext());
            binding.lvSubjectsViewResult.setAdapter(resultDetailAdapter);

            binding.tvGrandTotalMarksViewResult.setText(toatl_obtain_marks + " / " + toatl_marks);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //===============get exam details task ends=================


}