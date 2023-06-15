package com.ai.educationapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.EventPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityEventBinding;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class EventRVAdapter extends RecyclerView.Adapter<EventRVAdapter.MyHolder> {

    ArrayList<EventPOJO> eventPOJOArrayList;
    Context context;

    GDateTime gDateTime = new GDateTime();

    public EventRVAdapter(ArrayList<EventPOJO> eventPOJOArrayList, Context context) {
        this.eventPOJOArrayList = eventPOJOArrayList;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        EntityEventBinding binding;

        public MyHolder(EntityEventBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityEventBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.binding.tvEventTitle.setText("" + eventPOJOArrayList.get(position).getEvent_title());
        holder.binding.tvEventDate.setText("" + gDateTime.ymdTodmy(eventPOJOArrayList.get(position).getEvent_date()) + " " + gDateTime.getDay_name(eventPOJOArrayList.get(position).getEvent_date()));
        holder.binding.tvEventDescription.setText(eventPOJOArrayList.get(position).getEvent_description());


        String title = eventPOJOArrayList.get(position).getEvent_title();

        String finaltitle = "";
        String[] myName = title.split(" ");
        for (int i = 0; i < myName.length; i++) {
            finaltitle += myName[i].charAt(0);
        }

        holder.binding.tvEventIcon.setText("" + finaltitle);


        Random random=new Random();
        int red=random.nextInt(255);
        int green=random.nextInt(255);
        int blue=random.nextInt(255);

        holder.binding.tvEventIcon.setBackgroundColor(Color.rgb(red,green,blue));

        Layout layout = holder.binding.tvEventDescription.getLayout();
        if (layout != null) {
            int lines = layout.getLineCount();
            if (lines > 0) {
                int ellipsisCount = layout.getEllipsisCount(lines - 1);
                if (ellipsisCount > 0) {
                    Log.d(TAG, "Text is ellipsized");
                    holder.binding.tvShowMore.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.binding.tvShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return eventPOJOArrayList.size();
    }


}
