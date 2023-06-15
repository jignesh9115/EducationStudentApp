package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.AssignmentPOJO;
import com.ai.educationapp.SubmitAssignment;
import com.ai.educationapp.databinding.EntityAssignmentListBinding;

import java.util.ArrayList;

public class AssignmentListRVAdapter extends RecyclerView.Adapter<AssignmentListRVAdapter.MyHolder> {

    ArrayList<AssignmentPOJO> arrayList_assignmentlist;
    Context context;

    GDateTime gDateTime=new GDateTime();

    public AssignmentListRVAdapter(ArrayList<AssignmentPOJO> arrayList_assignmentlist, Context context) {
        this.arrayList_assignmentlist = arrayList_assignmentlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityAssignmentListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        if (arrayList_assignmentlist.get(position).getStatus().equalsIgnoreCase("1")) {

            holder.binding.tvAssignmentStatus.setTextColor(Color.parseColor("#FF3DDC84"));
            holder.binding.tvAssignmentStatus.setText("Assigned");

        } else if (arrayList_assignmentlist.get(position).getStatus().equalsIgnoreCase("0")) {

            holder.binding.tvAssignmentStatus.setTextColor(Color.RED);
            holder.binding.tvAssignmentStatus.setText("Missing");

        }

        holder.binding.tvAssignmentTitle.setText("" + arrayList_assignmentlist.get(position).getTitle());
        holder.binding.tvAssignmentDesc.setText("" + arrayList_assignmentlist.get(position).getAssignment_description());
        holder.binding.tvAssignmentDueDate.setText("" + gDateTime.ymdTodmy(arrayList_assignmentlist.get(position).getDue_date()));

        holder.binding.llAssignmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, SubmitAssignment.class);
                intent.putExtra("assign_id",arrayList_assignmentlist.get(position).getId());
                intent.putExtra("assign_title",arrayList_assignmentlist.get(position).getTitle());
                intent.putExtra("assign_status",arrayList_assignmentlist.get(position).getStatus());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList_assignmentlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        EntityAssignmentListBinding binding;

        public MyHolder(EntityAssignmentListBinding b) {
            super(b.getRoot());

            binding = b;

        }
    }

}
