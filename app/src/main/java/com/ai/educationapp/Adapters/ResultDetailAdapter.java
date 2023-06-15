package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.ResultDetailsPOJO;
import com.ai.educationapp.databinding.EntityExamSubjectDetailsBinding;
import com.ai.educationapp.databinding.EntityResultSubjectDetailsBinding;

import java.util.ArrayList;

public class ResultDetailAdapter extends BaseAdapter {

    ArrayList<ResultDetailsPOJO> resultDetailsPOJOArrayList;
    Context context;

    EntityResultSubjectDetailsBinding binding;

    public ResultDetailAdapter(ArrayList<ResultDetailsPOJO> resultDetailsPOJOArrayList, Context context) {
        this.resultDetailsPOJOArrayList = resultDetailsPOJOArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultDetailsPOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultDetailsPOJOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return resultDetailsPOJOArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding= EntityResultSubjectDetailsBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        binding.tvSubjectName.setText(""+resultDetailsPOJOArrayList.get(position).getSubject_title());
        binding.tvExamDate.setText(""+resultDetailsPOJOArrayList.get(position).getExam_date());
        binding.tvTotalMarks.setText(""+resultDetailsPOJOArrayList.get(position).getExam_total_marks());
        binding.tvObtainMarks.setText(""+resultDetailsPOJOArrayList.get(position).getMark_obtain());

        return view;
    }
}
