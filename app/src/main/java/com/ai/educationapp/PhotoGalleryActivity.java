package com.ai.educationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ai.educationapp.Adapters.PhotoGalleryAdapter;
import com.ai.educationapp.POJOs.PhotoGalleryPOJO;
import com.ai.educationapp.databinding.ActivityPhotoGalleryBinding;
import com.ai.educationapp.databinding.DialogShowImageBinding;
import com.ai.educationapp.databinding.EntityShowImageBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoGalleryActivity extends AppCompatActivity {

    ActivityPhotoGalleryBinding binding;

    String url = "", response = "";

    ArrayList<PhotoGalleryPOJO> arrayList_photo_gallery;
    PhotoGalleryPOJO photoGalleryPOJO;

    PhotoGalleryAdapter photoGalleryAdapter;

    String category_id = "";

    AlertDialog ad;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotoGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        category_id = bundle.getString("category_id");

        arrayList_photo_gallery = new ArrayList<>();
        new getPhotoGalleryTask().execute();

        binding.imgPhotoGalleryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.gvPhotoGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showImageDialog();

            }
        });

    }

    //========================get photo gallery images task starts=========================

    public class getPhotoGalleryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_photo_gallery_url) + category_id;
            Log.d("get photo gallery url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("photogallery response=>", response + "");

            getPhotoGallery();

        }
    }

    private void getPhotoGallery() {

        try {

            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgAssignmentNotFound.setVisibility(View.GONE);
                binding.gvPhotoGallery.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    photoGalleryPOJO = new PhotoGalleryPOJO(object.getString("id"),
                            object.getString("image_title"),
                            object.getString("image_img"),
                            object.getString("image_category"),
                            object.getString("sort_order"));

                    arrayList_photo_gallery.add(photoGalleryPOJO);
                }

                photoGalleryAdapter = new PhotoGalleryAdapter(arrayList_photo_gallery, PhotoGalleryActivity.this);
                binding.gvPhotoGallery.setAdapter(photoGalleryAdapter);

            } else {

                binding.imgAssignmentNotFound.setVisibility(View.GONE);
                binding.gvPhotoGallery.setVisibility(View.VISIBLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //========================get photo gallery images task ends===========================


    public void showImageDialog() {

        DialogShowImageBinding binding1;

        builder = new AlertDialog.Builder(PhotoGalleryActivity.this);
        binding1 = DialogShowImageBinding.inflate(LayoutInflater.from(getApplicationContext()));
        builder.setView(binding1.getRoot());


        ShowPhotoVPAdapter showPhotoVPAdapter = new ShowPhotoVPAdapter(arrayList_photo_gallery, getApplicationContext());
        binding1.vpShowImgae.setAdapter(showPhotoVPAdapter);

        ad = builder.create();
        ad.show();
    }


    //=====================View Pager Adapter code starts================================

    public class ShowPhotoVPAdapter extends PagerAdapter {

        ArrayList<PhotoGalleryPOJO> arrayList_photo_gallery;
        Context context;

        public ShowPhotoVPAdapter(ArrayList<PhotoGalleryPOJO> arrayList_photo_gallery, Context context) {
            this.arrayList_photo_gallery = arrayList_photo_gallery;
            this.context = context;
        }

        @Override
        public int getCount() {
            return arrayList_photo_gallery.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            EntityShowImageBinding entity_binding;

            entity_binding = EntityShowImageBinding.inflate(LayoutInflater.from(getApplicationContext()));
            View view = entity_binding.getRoot();

            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.profileblueicon);
            Glide.with(context).load(arrayList_photo_gallery.get(position).getImage_img())
                    .apply(options)
                    .into(entity_binding.imgShowImage);

            container.addView(view, 0);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    //=====================View Pager Adapter code ends==================================


}