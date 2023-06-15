package com.ai.educationapp.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.POJOs.FeeDetailPOJO;
import com.ai.educationapp.databinding.DialogFeeDetailBinding;
import com.ai.educationapp.databinding.EntityFeeDetailBinding;

import java.util.ArrayList;

public class FeeDetailRVAdapter extends RecyclerView.Adapter<FeeDetailRVAdapter.MyHolder>{

    ArrayList<FeeDetailPOJO> arrayList_fee_detail;
    Context context;

    public FeeDetailRVAdapter(ArrayList<FeeDetailPOJO> arrayList_fee_detail, Context context) {
        this.arrayList_fee_detail = arrayList_fee_detail;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityFeeDetailBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.binding.tvStudentFeeDetailSrno.setText((position+1)+".");
        holder.binding.tvStudentFeeDetailTitle.setText(""+arrayList_fee_detail.get(position).getTitle());
        holder.binding.tvStudentFeeDetailAmount.setText("₹ "+arrayList_fee_detail.get(position).getBase_amount());
    }

    @Override
    public int getItemCount() {
        return arrayList_fee_detail.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        EntityFeeDetailBinding binding;

        public MyHolder(EntityFeeDetailBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }

}
