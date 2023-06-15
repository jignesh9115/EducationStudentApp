package com.ai.educationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.PhotoGalleryPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.EntityPhotoGalleryBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class PhotoGalleryAdapter extends BaseAdapter {

    ArrayList<PhotoGalleryPOJO> arrayList_photo_gallery;
    Context context;

    EntityPhotoGalleryBinding binding;

    public PhotoGalleryAdapter(ArrayList<PhotoGalleryPOJO> arrayList_photo_gallery, Context context) {
        this.arrayList_photo_gallery = arrayList_photo_gallery;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList_photo_gallery.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_photo_gallery.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList_photo_gallery.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View  view=convertView;

        if (view==null){

            binding=EntityPhotoGalleryBinding.inflate(LayoutInflater.from(context),parent,false);
            view=binding.getRoot();
        }

        RequestOptions options=new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.profileblueicon);

        Glide.with(context).load(arrayList_photo_gallery.get(position).getImage_img())
                .apply(options)
                .into(binding.imgPhoto);

        return view;
    }
}
