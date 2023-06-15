package com.ai.educationapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.HolidayPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityHolidayBinding;

import java.util.ArrayList;
import java.util.Random;

public class HolidayRVAdapter extends RecyclerView.Adapter<HolidayRVAdapter.MyHolder>{

    ArrayList<HolidayPOJO> holidayPOJOArrayList;
    Context context;

    GDateTime gDateTime=new GDateTime();

    public HolidayRVAdapter(ArrayList<HolidayPOJO> holidayPOJOArrayList, Context context) {
        this.holidayPOJOArrayList = holidayPOJOArrayList;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        EntityHolidayBinding binding;

        public MyHolder(EntityHolidayBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityHolidayBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.binding.tvHolidayTitle.setText(""+holidayPOJOArrayList.get(position).getHoliday_title());
        holder.binding.tvStartDate.setText(""+gDateTime.ymdTodmy(holidayPOJOArrayList.get(position).getHoliday_date_start())+" "+gDateTime.getDay_name(holidayPOJOArrayList.get(position).getHoliday_date_start()));
        holder.binding.tvEndDate.setText(" TO "+gDateTime.ymdTodmy(holidayPOJOArrayList.get(position).getHoliday_date_end())+" "+gDateTime.getDay_name(holidayPOJOArrayList.get(position).getHoliday_date_end()));

        String title=holidayPOJOArrayList.get(position).getHoliday_title();


        String finaltitle = "";
        String[] myName = title.split(" ");
        for (int i = 0; i < myName.length; i++) {
            finaltitle += myName[i].charAt(0);
        }

        holder.binding.tvHolidayIcon.setText(""+finaltitle);

        Random random=new Random();
        int red=random.nextInt(255);
        int green=random.nextInt(255);
        int blue=random.nextInt(255);

        holder.binding.tvHolidayIcon.setBackgroundColor(Color.rgb(red,green,blue));


    }

    @Override
    public int getItemCount() {
        return holidayPOJOArrayList.size();
    }



}


