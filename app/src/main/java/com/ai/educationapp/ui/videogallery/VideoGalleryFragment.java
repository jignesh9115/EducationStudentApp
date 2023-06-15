package com.ai.educationapp.ui.videogallery;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ai.educationapp.Adapters.PhotoGalleryCategoryAdapter;
import com.ai.educationapp.Adapters.VideoGalleryCategoryAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.GalleryCategoryPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentVideogalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoGalleryFragment extends Fragment {

    private VideoGalleryViewModel growthViewModel;

    FragmentVideogalleryBinding binding;

    ArrayList<GalleryCategoryPOJO> arrayList_VideoGalleryCategory;
    GalleryCategoryPOJO galleryCategoryPOJO;

    VideoGalleryCategoryAdapter videoGalleryCategoryAdapter;

    String url = "", response = "";

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        growthViewModel =
                ViewModelProviders.of(this).get(VideoGalleryViewModel.class);
        binding = FragmentVideogalleryBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgVideoCategoryNotFound.setVisibility(View.GONE);

        arrayList_VideoGalleryCategory = new ArrayList<>();
        new getVideoCategory().execute();

        return root;
    }

    //====================get all photo category task starts===========================

    public class getVideoCategory extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(getActivity());
            pdialog.setMessage("Loading...");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_video_gallery_category_url);
            Log.d("video category url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("video cat response=>", response + "");

            getVideoCategorydata();

            if (pdialog.isShowing()) {

                pdialog.dismiss();

            }

        }
    }

    private void getVideoCategorydata() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgVideoCategoryNotFound.setVisibility(View.GONE);
                binding.gvVideoGalleryCategory.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    galleryCategoryPOJO = new GalleryCategoryPOJO(object.getString("id"),
                            object.getString("category_title"),
                            object.getString("category_image"),
                            object.getString("sort_order"));

                    arrayList_VideoGalleryCategory.add(galleryCategoryPOJO);
                }

                videoGalleryCategoryAdapter = new VideoGalleryCategoryAdapter(arrayList_VideoGalleryCategory, getActivity());
                binding.gvVideoGalleryCategory.setAdapter(videoGalleryCategoryAdapter);

            } else {

                binding.imgVideoCategoryNotFound.setVisibility(View.VISIBLE);
                binding.gvVideoGalleryCategory.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //====================get all photo category task ends=============================

}