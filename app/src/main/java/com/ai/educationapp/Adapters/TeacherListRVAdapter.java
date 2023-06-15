package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.POJOs.TeacherListPOJO;
import com.ai.educationapp.databinding.EntityTeacherListBinding;

import java.util.ArrayList;

public class TeacherListRVAdapter extends RecyclerView.Adapter<TeacherListRVAdapter.MyHolder>{

    ArrayList<TeacherListPOJO> teacherListPOJOArrayList;
    Context context;

    public TeacherListRVAdapter(ArrayList<TeacherListPOJO> teacherListPOJOArrayList, Context context) {
        this.teacherListPOJOArrayList = teacherListPOJOArrayList;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        EntityTeacherListBinding binding;

        public MyHolder(EntityTeacherListBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityTeacherListBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.binding.tvSubjectTeacherlist.setText(""+teacherListPOJOArrayList.get(position).getSubject_title());
        holder.binding.tvTeacherTeacherlist.setText(""+teacherListPOJOArrayList.get(position).getTeacher_name());

    }

    @Override
    public int getItemCount() {
        return teacherListPOJOArrayList.size();
    }


}
