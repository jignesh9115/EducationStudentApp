package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.ExamTypePOJO;
import com.ai.educationapp.ViewExamDetails;
import com.ai.educationapp.databinding.EntityExamListBinding;

import java.util.ArrayList;

public class ExamListAdapter extends BaseAdapter {

    ArrayList<ExamTypePOJO> examTypePOJOArrayList;
    Context context;

    EntityExamListBinding binding;

    public ExamListAdapter(ArrayList<ExamTypePOJO> examTypePOJOArrayList, Context context) {
        this.examTypePOJOArrayList = examTypePOJOArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return examTypePOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return examTypePOJOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return examTypePOJOArrayList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding=EntityExamListBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        binding.tvExamTitle.setText(""+examTypePOJOArrayList.get(position).getExam_type_title());

        binding.cardExamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ViewExamDetails.class);
                intent.putExtra("exam_type_id",examTypePOJOArrayList.get(position).getExam_type_id());
                intent.putExtra("exam_type_title",examTypePOJOArrayList.get(position).getExam_type_title());
                context.startActivity(intent);

            }
        });


        return view;
    }
}
