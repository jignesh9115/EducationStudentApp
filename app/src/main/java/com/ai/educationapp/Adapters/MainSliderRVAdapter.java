package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityMainHomeSliderViewPagerBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MainSliderRVAdapter extends  RecyclerView.Adapter<MainSliderRVAdapter.MyHolder>{

    ArrayList<String> arrayList;
    Context context;

    public MainSliderRVAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(EntityMainHomeSliderViewPagerBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        RequestOptions options=new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.nodatafound_img);
        Glide.with(context).load(arrayList.get(position))
                .apply(options)
                .into(holder.binding.imgProperty);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        EntityMainHomeSliderViewPagerBinding binding;

        public MyHolder(EntityMainHomeSliderViewPagerBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }

}
