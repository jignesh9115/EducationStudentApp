package com.ai.educationapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.CalendarValuesPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.DialogHolidayDetailBinding;
import com.ai.educationapp.databinding.EntityStudentAttendanceBinding;


import java.util.ArrayList;

public class AttendanceCalendarAdapter extends BaseAdapter {

    ArrayList<CalendarValuesPOJO> calendarValuesPOJOArrayList;
    Context context;

    EntityStudentAttendanceBinding binding;
    DialogHolidayDetailBinding holidayDetailBinding;

    AlertDialog ad;
    AlertDialog.Builder builder;

    public AttendanceCalendarAdapter(ArrayList<CalendarValuesPOJO> calendarValuesPOJOArrayList, Context context) {
        this.calendarValuesPOJOArrayList = calendarValuesPOJOArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return calendarValuesPOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarValuesPOJOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return calendarValuesPOJOArrayList.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            binding=EntityStudentAttendanceBinding.inflate(LayoutInflater.from(context),parent,false);
            view = binding.getRoot();
        }

        binding.imgAttendenceHilight.setVisibility(View.GONE);

        if (calendarValuesPOJOArrayList.get(position).getStatus().equalsIgnoreCase("0")) {
            binding.imgAttendenceHilight.setVisibility(View.GONE);
        }else if (calendarValuesPOJOArrayList.get(position).getStatus().equalsIgnoreCase("1")) {
            binding.imgAttendenceHilight.setVisibility(View.VISIBLE);
            binding.imgAttendenceHilight.setBackgroundResource(R.drawable.present_dot);
        }else if (calendarValuesPOJOArrayList.get(position).getStatus().equalsIgnoreCase("2")) {
            binding.imgAttendenceHilight.setVisibility(View.VISIBLE);
            binding.imgAttendenceHilight.setBackgroundResource(R.drawable.holiday_dot);
        }else if (calendarValuesPOJOArrayList.get(position).getStatus().equalsIgnoreCase("3")) {
            binding.imgAttendenceHilight.setVisibility(View.VISIBLE);
            binding.imgAttendenceHilight.setBackgroundResource(R.drawable.absent_dot);
        }

        binding.cardAttendenceEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (calendarValuesPOJOArrayList.get(position).getHoliday().equalsIgnoreCase("")){

                }else {

                    builder=new AlertDialog.Builder(context);
                    holidayDetailBinding=DialogHolidayDetailBinding.inflate(LayoutInflater.from(context));
                    builder.setView(holidayDetailBinding.getRoot());

                    holidayDetailBinding.tvHolidayDetailDate.setText("Date : "+calendarValuesPOJOArrayList.get(position).getDate());
                    holidayDetailBinding.tvHolidayDetail.setText(""+calendarValuesPOJOArrayList.get(position).getHoliday());

                    ad=builder.create();
                    ad.show();

                    //============code for customize dialog size starts===============

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(ad.getWindow().getAttributes());
                    lp.width = 800;
                    //lp.height = 300;
                    //lp.x=-170;
                    //lp.y=100;
                    ad.getWindow().setAttributes(lp);

                    //============code for customize dialog size ends================

                }

            }
        });



        binding.tvAttendanceDay.setText(""+calendarValuesPOJOArrayList.get(position).getDay());

        return view;
    }
}
