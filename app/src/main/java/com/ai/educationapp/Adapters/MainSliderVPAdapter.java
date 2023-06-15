package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ai.educationapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MainSliderVPAdapter extends PagerAdapter {

    ArrayList<String> arrayList;
    Context context;

    public MainSliderVPAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view=LayoutInflater.from(context).inflate(R.layout.entity_main_home_slider_view_pager,container,false);


        ImageView img_property=view.findViewById(R.id.img_property);


        RequestOptions options=new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.nodatafound_img);
        Glide.with(context).load(arrayList.get(position))
                .apply(options)
                .into(img_property);

        container.addView(view,0);
        return view;
    }


    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
