package com.ai.educationapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.MainActivity;
import com.ai.educationapp.POJOs.StudentListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityStudentListBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class StudentListRVAdapter extends RecyclerView.Adapter<StudentListRVAdapter.MyHolder> {

    ArrayList<StudentListPOJO> studentListPOJOArrayList;
    Context context;

    SharedPreferences sp;

    public StudentListRVAdapter(ArrayList<StudentListPOJO> studentListPOJOArrayList, Context context) {
        this.studentListPOJOArrayList = studentListPOJOArrayList;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        EntityStudentListBinding binding;

        public MyHolder(EntityStudentListBinding b) {
            super(b.getRoot());
            binding = b;
        }

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityStudentListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {

        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.profileblueicon);
        Glide.with(context).load(studentListPOJOArrayList.get(position).getStudent_photo())
                .apply(options)
                .into(holder.binding.cimgStudentPhoto);



        holder.binding.tvStudentName.setText("" + studentListPOJOArrayList.get(position).getStudent_name());
        holder.binding.tvStudentMedium.setText("" + studentListPOJOArrayList.get(position).getStudent_division());
        holder.binding.tvStudentStandard.setText("" + studentListPOJOArrayList.get(position).getStudent_standard());
        holder.binding.tvStudentGrno.setText("" + studentListPOJOArrayList.get(position).getStudent_gnr_no());
        holder.binding.tvStudentRollno.setText("" + studentListPOJOArrayList.get(position).getStudent_roll_no());

        holder.binding.cardChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = context.getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("student_id", Integer.parseInt(studentListPOJOArrayList.get(position).getStudent_id()));
                editor.putInt("standard_id", Integer.parseInt(studentListPOJOArrayList.get(position).getStandard_id()));
                editor.apply();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentListPOJOArrayList.size();
    }

}
