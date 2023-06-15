package com.ai.educationapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.VideoGalleryPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.ViewGalleryVideoActivity;
import com.ai.educationapp.databinding.EntityVideoGalleryBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class VideoGalleryAdapter extends BaseAdapter {

    ArrayList<VideoGalleryPOJO> arrayList_video_gallery;
    Context context;

    EntityVideoGalleryBinding binding;

    public VideoGalleryAdapter(ArrayList<VideoGalleryPOJO> arrayList_video_gallery, Context context) {
        this.arrayList_video_gallery = arrayList_video_gallery;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList_video_gallery.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_video_gallery.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList_video_gallery.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=convertView;

        if (view==null){

            binding=EntityVideoGalleryBinding.inflate(LayoutInflater.from(context));
            view=binding.getRoot();
        }

        RequestOptions options=new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.profileblueicon);
        Glide.with(context).load(arrayList_video_gallery.get(position).getVideo_src())
                .apply(options)
                .into(binding.imgVideo);

        binding.cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ViewGalleryVideoActivity.class);
                intent.putExtra("video_title",arrayList_video_gallery.get(position).getVideo_title());
                intent.putExtra("video_url",arrayList_video_gallery.get(position).getVideo_src());
                context.startActivity(intent);

            }
        });


        return view;
    }
}
