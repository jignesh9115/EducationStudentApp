package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.GalleryCategoryPOJO;
import com.ai.educationapp.PhotoGalleryActivity;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityPhotoGalleryCategoryBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class PhotoGalleryCategoryAdapter extends BaseAdapter {

    ArrayList<GalleryCategoryPOJO> arrayList_PhotoGalleryCategory;
    Context context;

    EntityPhotoGalleryCategoryBinding binding;

    public PhotoGalleryCategoryAdapter(ArrayList<GalleryCategoryPOJO> arrayList_PhotoGalleryCategory, Context context) {
        this.arrayList_PhotoGalleryCategory = arrayList_PhotoGalleryCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList_PhotoGalleryCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_PhotoGalleryCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList_PhotoGalleryCategory.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding= EntityPhotoGalleryCategoryBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        RequestOptions options=new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.profileblueicon);
        Glide.with(context).load(arrayList_PhotoGalleryCategory.get(0).getCategory_image())
                .apply(options)
                .into(binding.imgPhotoGallery);

        binding.tvPhotoCategoryDesc.setText(""+arrayList_PhotoGalleryCategory.get(position).getCategory_title());

        binding.cardPhotoGalleryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, PhotoGalleryActivity.class);
                intent.putExtra("category_id",arrayList_PhotoGalleryCategory.get(position).getId());
                context.startActivity(intent);

            }
        });

        return view;
    }
}
