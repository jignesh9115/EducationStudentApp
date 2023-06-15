package com.ai.educationapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ai.educationapp.databinding.ActivityAddLeaveApplicationBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddLeaveApplication extends AppCompatActivity {

    ActivityAddLeaveApplicationBinding binding;

    ArrayList<String> arrayList_leave_reason;
    ArrayAdapter<String> arrayAdapter_leave_reason;

    DatePickerDialog dp;
    Calendar cal;
    int m, y, d;

    GDateTime gDateTime = new GDateTime();

    String start_date="",end_date="",leave_desc="",leave_type="",url="",response="";

    AlertDialog ad;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddLeaveApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayList_leave_reason=new ArrayList<>();
        arrayList_leave_reason.add("Medical");
        arrayList_leave_reason.add("Personal");

        arrayAdapter_leave_reason=new ArrayAdapter<>(getApplicationContext(),R.layout.mytextview,arrayList_leave_reason);
        binding.spnLeaveType.setAdapter(arrayAdapter_leave_reason);

        binding.imgAddLeaveApplicationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.tvFromDate.setText("" + gDateTime.getDatedmy());
        start_date = gDateTime.getDateymd();

        binding.tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                y = Integer.parseInt(gDateTime.getYear());
                m = Integer.parseInt(gDateTime.getMonth()) - 1;
                d = Integer.parseInt(gDateTime.getDay());

                dp = new DatePickerDialog(AddLeaveApplication.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        DateFormat dff = new SimpleDateFormat("dd-MM-yyyy");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        start_date = df.format(cal.getTime());
                        binding.tvFromDate.setText("" + dff.format(cal.getTime()));
                    }
                }, y, m, d);
                dp.show();
            }
        });


        binding.tvToDate.setText("" + gDateTime.getDatedmy());
        end_date = gDateTime.getDateymd();

        binding.tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                y = Integer.parseInt(gDateTime.getYear());
                m = Integer.parseInt(gDateTime.getMonth()) - 1;
                d = Integer.parseInt(gDateTime.getDay());

                dp = new DatePickerDialog(AddLeaveApplication.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        DateFormat dff = new SimpleDateFormat("dd-MM-yyyy");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        end_date = df.format(cal.getTime());
                        binding.tvToDate.setText("" + dff.format(cal.getTime()));
                    }
                }, y, m, d);
                dp.show();
            }
        });

        binding.spnLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                leave_type=arrayList_leave_reason.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSubmitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leave_desc=binding.edtLeaveDesc.getText().toString().trim();

                //Toast.makeText(AddLeaveApplication.this, "start_date=>"+start_date+"\nend_date=>"+end_date+"\nleave_desc=>"+leave_desc+"\nleave_type=>"+leave_type, Toast.LENGTH_SHORT).show();

                new submitLeaveTask().execute();

            }
        });

    }


    //====================submit laeve task starts=============================

    public class submitLeaveTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url=getString(R.string.Base_url)+getString(R.string.add_leave_url)+start_date+"&end_date="+end_date+"&leave_desc="+leave_desc+"&leave_type="+leave_type;
            Log.d("submit Leave url=>",url+"");

            HttpHandler httpHandler = new HttpHandler();
            response=httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("Apply Leave response=>",response+"");

            if (response.contains("added successfully.")){

                showDialog();

            }else {

                Toast.makeText(AddLeaveApplication.this, "something went wrong...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //====================submit laeve task ends===============================

    public void showDialog(){

        builder=new AlertDialog.Builder(AddLeaveApplication.this);
        builder.setCancelable(false);
        builder.setMessage("Apply Leave Successfully...");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        ad=builder.create();
        ad.show();

        ad.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }

}