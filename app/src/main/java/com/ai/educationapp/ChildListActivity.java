package com.ai.educationapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ai.educationapp.Adapters.StudentListRVAdapter;
import com.ai.educationapp.POJOs.StudentListPOJO;
import com.ai.educationapp.databinding.ActivityChildListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChildListActivity extends AppCompatActivity {

    ActivityChildListBinding binding;

    String url = "", response = "";

    ArrayList<StudentListPOJO> studentListPOJOArrayList;
    StudentListPOJO studentListPOJO;
    StudentListRVAdapter studentListRVAdapter;

    RecyclerView.LayoutManager layoutManager;

    ProgressDialog pdialog;

    String mobile_number = "";

    SharedPreferences sp;

    AlertDialog ad;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        layoutManager = new LinearLayoutManager(getApplicationContext());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon_appbar_education);

        sp = getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        mobile_number = sp.getString("mobile_number", null);

        //Toast.makeText(this, ""+mobile_number, Toast.LENGTH_SHORT).show();

        studentListPOJOArrayList = new ArrayList<>();
        new getChildList().execute();
    }

    //================get child list task starts====================

    public class getChildList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(ChildListActivity.this);
            pdialog.setCancelable(false);
            pdialog.setMessage("Loading...");
            pdialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //url = getString(R.string.Base_url) + getString(R.string.student_list_url) + "9723466180";
            url = getString(R.string.Base_url) + getString(R.string.student_list_url) + mobile_number;
            Log.d("get child list url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(ChildListActivity.this, "response=>" + response, Toast.LENGTH_SHORT).show();
            Log.d("Response=>", response + "");

            getChildList();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
        }
    }

    public void getChildList() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");

            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    studentListPOJO = new StudentListPOJO(object.getString("student_name"),
                            object.getString("student_birthdate"),
                            object.getString("student_roll_no"),
                            object.getString("student_photo"),
                            object.getString("student_standard"),
                            object.getString("student_division"),
                            object.getString("student_gnr_no"),
                            object.getString("student_id"),
                            object.getString("standard_id"));

                    studentListPOJOArrayList.add(studentListPOJO);

                }

                studentListRVAdapter = new StudentListRVAdapter(studentListPOJOArrayList, ChildListActivity.this);
                binding.rvChildlist.setLayoutManager(layoutManager);
                binding.rvChildlist.setAdapter(studentListRVAdapter);

            } else {

                showDialog();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //================get child list task ends======================


    public void showDialog() {

        builder = new AlertDialog.Builder(ChildListActivity.this);
        builder.setMessage("Your mobile number is not Register!");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        ad = builder.create();
        ad.show();

        ad.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }

}