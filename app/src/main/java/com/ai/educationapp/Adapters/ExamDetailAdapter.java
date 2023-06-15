package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.ExamDetailsPOJO;
import com.ai.educationapp.databinding.EntityExamSubjectDetailsBinding;

import java.util.ArrayList;

public class ExamDetailAdapter extends BaseAdapter {

    ArrayList<ExamDetailsPOJO> examDetailsPOJOArrayList;
    Context context;

    EntityExamSubjectDetailsBinding binding;

    public ExamDetailAdapter(ArrayList<ExamDetailsPOJO> examDetailsPOJOArrayList, Context context) {
        this.examDetailsPOJOArrayList = examDetailsPOJOArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return examDetailsPOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return examDetailsPOJOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return examDetailsPOJOArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding=EntityExamSubjectDetailsBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        binding.tvSubjectName.setText(""+examDetailsPOJOArrayList.get(position).getSubject_title());
        binding.tvExamDate.setText(""+examDetailsPOJOArrayList.get(position).getExam_date());
        binding.tvTotalMarks.setText(""+examDetailsPOJOArrayList.get(position).getExam_total_marks());

        return view;
    }
}
