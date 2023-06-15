package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.GalleryCategoryPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.VideoGalleryActivity;
import com.ai.educationapp.databinding.EntityVideoGalleryCategoryBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class VideoGalleryCategoryAdapter extends BaseAdapter {

    ArrayList<GalleryCategoryPOJO> arrayList_VideoGalleryCategory;
    Context context;

    EntityVideoGalleryCategoryBinding binding;

    public VideoGalleryCategoryAdapter(ArrayList<GalleryCategoryPOJO> arrayList_VideoGalleryCategory, Context context) {
        this.arrayList_VideoGalleryCategory = arrayList_VideoGalleryCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList_VideoGalleryCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_VideoGalleryCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList_VideoGalleryCategory.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding= EntityVideoGalleryCategoryBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        RequestOptions options=new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.profileblueicon);
        Glide.with(context).load(arrayList_VideoGalleryCategory.get(0).getCategory_image())
                .apply(options)
                .into(binding.imgVideoGallery);

        binding.tvVideoCategoryDesc.setText(""+arrayList_VideoGalleryCategory.get(position).getCategory_title());

        binding.cardVideoGalleryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, VideoGalleryActivity.class);
                intent.putExtra("category_id",arrayList_VideoGalleryCategory.get(position).getId());
                context.startActivity(intent);

            }
        });

        return view;
    }
}
