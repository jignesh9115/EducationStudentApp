package com.ai.educationapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.LeaveListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityLeaveApplicationBinding;

import java.util.ArrayList;

public class LeaveApplicationRVAdapter extends RecyclerView.Adapter<LeaveApplicationRVAdapter.Myholder> {

    ArrayList<LeaveListPOJO> arrayList_leave_list;
    Context context;

    GDateTime gDateTime = new GDateTime();

    public LeaveApplicationRVAdapter(ArrayList<LeaveListPOJO> arrayList_leave_list, Context context) {
        this.arrayList_leave_list = arrayList_leave_list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(EntityLeaveApplicationBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        if (arrayList_leave_list.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.binding.tvLeaveStatus.setTextColor(Color.RED);
            holder.binding.tvLeaveStatus.setText("Pending");
        } else if (arrayList_leave_list.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.binding.tvLeaveStatus.setTextColor(context.getResources().getColor(R.color.ic_launcher_background));
            holder.binding.tvLeaveStatus.setText("Approved");
        }

        holder.binding.tvLeaveType.setText("" + arrayList_leave_list.get(position).getLeave_type());
        holder.binding.tvLeaveDesc.setText("" + arrayList_leave_list.get(position).getLeave_desc());

        if (arrayList_leave_list.get(position).getEnd_date().toString().isEmpty()) {

            holder.binding.tvLeaveDate.setText("" + gDateTime.ymdTodmy(arrayList_leave_list.get(position).getStart_date()));

        } else {

            holder.binding.tvLeaveDate.setText( gDateTime.ymdTodmy(arrayList_leave_list.get(position).getStart_date())+ "  To  " + gDateTime.ymdTodmy(arrayList_leave_list.get(position).getEnd_date()));

        }
    }

    @Override
    public int getItemCount() {
        return arrayList_leave_list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        EntityLeaveApplicationBinding binding;

        public Myholder(EntityLeaveApplicationBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

}
