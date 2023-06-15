package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.StudentFeeDetailPOJO;
import com.ai.educationapp.databinding.EntityFeeHistoryBinding;
import com.ai.educationapp.databinding.EntityFeeInstallmentBinding;

import java.util.ArrayList;

public class FeeInstallmentRVAdapter extends RecyclerView.Adapter<FeeInstallmentRVAdapter.MyHolder>{

    ArrayList<StudentFeeDetailPOJO> arrayList_fee_installment;
    Context context;

    GDateTime gDateTime = new GDateTime();

    public FeeInstallmentRVAdapter(ArrayList<StudentFeeDetailPOJO> arrayList_fee_installment, Context context) {
        this.arrayList_fee_installment = arrayList_fee_installment;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityFeeInstallmentBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.binding.tvFeeInstallSrno.setText((position+1)+".");
        holder.binding.tvFeeInstallTitle.setText(""+arrayList_fee_installment.get(position).getTitle());
        holder.binding.tvFeeInstallDates.setText(""+gDateTime.ymdTodmy(arrayList_fee_installment.get(position).getDue_date()));
        holder.binding.tvFeeInstallAmount.setText("₹ "+arrayList_fee_installment.get(position).getBase_amount());
    }

    @Override
    public int getItemCount() {
        return arrayList_fee_installment.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        EntityFeeInstallmentBinding binding;

        public MyHolder(EntityFeeInstallmentBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }

}
