package com.ai.educationapp.ui.photogallery;

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
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.GalleryCategoryPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentPhotogalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment {

    private PhotoGalleryViewModel bookViewModel;

    FragmentPhotogalleryBinding binding;

    String url = "", response = "";

    ArrayList<GalleryCategoryPOJO> arrayList_PhotoGalleryCategory;
    GalleryCategoryPOJO photoGalleryCategoryPOJO;

    PhotoGalleryCategoryAdapter photoGalleryCategoryAdapter;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookViewModel =
                ViewModelProviders.of(this).get(PhotoGalleryViewModel.class);

        binding = FragmentPhotogalleryBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgPhotoCategoryNotFound.setVisibility(View.GONE);

        arrayList_PhotoGalleryCategory = new ArrayList<>();
        new getPhotoCategory().execute();


        return root;
    }

    //====================get all photo category task starts===========================

    public class getPhotoCategory extends AsyncTask<Void, Void, Void> {

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

            url = getString(R.string.Base_url) + getString(R.string.get_image_gallery_category_url);
            Log.d("photo category url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("photo cat response=>", response + "");

            getPhotoCategorydata();

            if (pdialog.isShowing()) {

                pdialog.dismiss();

            }

        }
    }

    private void getPhotoCategorydata() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgPhotoCategoryNotFound.setVisibility(View.GONE);
                binding.gvPhotoGalleryCategory.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    photoGalleryCategoryPOJO = new GalleryCategoryPOJO(object.getString("id"),
                            object.getString("category_title"),
                            object.getString("category_image"),
                            object.getString("sort_order"));

                    arrayList_PhotoGalleryCategory.add(photoGalleryCategoryPOJO);
                }

                photoGalleryCategoryAdapter = new PhotoGalleryCategoryAdapter(arrayList_PhotoGalleryCategory, getActivity());
                binding.gvPhotoGalleryCategory.setAdapter(photoGalleryCategoryAdapter);

            } else {

                binding.imgPhotoCategoryNotFound.setVisibility(View.GONE);
                binding.gvPhotoGalleryCategory.setVisibility(View.VISIBLE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //====================get all photo category task ends=============================

}