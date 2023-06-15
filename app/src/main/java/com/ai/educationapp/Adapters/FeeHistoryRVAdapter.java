package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.GDateTime;
import com.ai.educationapp.POJOs.StudentFeeDetailPOJO;
import com.ai.educationapp.databinding.EntityFeeHistoryBinding;

import java.util.ArrayList;

public class FeeHistoryRVAdapter extends RecyclerView.Adapter<FeeHistoryRVAdapter.MyHolder> {

    ArrayList<StudentFeeDetailPOJO> arrayList_fee_history;
    Context context;

    GDateTime gDateTime = new GDateTime();

    public FeeHistoryRVAdapter(ArrayList<StudentFeeDetailPOJO> arrayList_fee_history, Context context) {
        this.arrayList_fee_history = arrayList_fee_history;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityFeeHistoryBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        holder.binding.tvFeeHistorySrno.setText((position + 1) + ".");
        holder.binding.tvFeeHistoryRecieptno.setText("" + arrayList_fee_history.get(position).getReceipt_url());
        holder.binding.tvFeeHistoryTitle.setText("" + arrayList_fee_history.get(position).getTitle());
        holder.binding.tvFeeHistoryDate.setText("" + gDateTime.ymdTodmy(arrayList_fee_history.get(position).getDue_date()));
        holder.binding.tvFeeHistoryComments.setText("" + arrayList_fee_history.get(position).getComments());
        holder.binding.tvFeeHistoryAmount.setText("₹ " + arrayList_fee_history.get(position).getBase_amount());

        holder.binding.tvFeeHistoryRecieptno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = arrayList_fee_history.get(position).getReceipt_url();


                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);

                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList_fee_history.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        EntityFeeHistoryBinding binding;

        public MyHolder(EntityFeeHistoryBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

}
