package com.ai.educationapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.ai.educationapp.Adapters.VideoGalleryAdapter;
import com.ai.educationapp.POJOs.VideoGalleryPOJO;
import com.ai.educationapp.databinding.ActivityVideoGalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoGalleryActivity extends AppCompatActivity {

    ActivityVideoGalleryBinding binding;

    String url = "", response = "", category_id = "";

    ArrayList<VideoGalleryPOJO> arrayList_video_gallery;
    VideoGalleryPOJO videoGalleryPOJO;

    VideoGalleryAdapter videoGalleryAdapter;

    AlertDialog ad;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgAssignmentNotFound.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        category_id = bundle.getString("category_id");

        arrayList_video_gallery = new ArrayList<>();
        new getVideoGalleryTask().execute();

        binding.imgVideoGalleryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* binding.gvVideoGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showVideoDialog();

            }
        });*/

    }


    //========================get photo gallery images task starts=========================

    public class getVideoGalleryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + getString(R.string.get_video_gallery_url) + category_id;
            Log.d("get video gallery url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("videogallery response=>", response + "");

            //Toast.makeText(VideoGalleryActivity.this, ""+response, Toast.LENGTH_SHORT).show();

            getPhotoGallery();

        }
    }

    private void getPhotoGallery() {

        try {

            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgAssignmentNotFound.setVisibility(View.GONE);
                binding.gvVideoGallery.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    videoGalleryPOJO = new VideoGalleryPOJO(object.getString("id"),
                            object.getString("video_title"),
                            object.getString("video_src"),
                            object.getString("video_category"),
                            object.getString("sort_order"));

                    arrayList_video_gallery.add(videoGalleryPOJO);
                }

                videoGalleryAdapter = new VideoGalleryAdapter(arrayList_video_gallery, VideoGalleryActivity.this);
                binding.gvVideoGallery.setAdapter(videoGalleryAdapter);

            } else {

                binding.imgAssignmentNotFound.setVisibility(View.VISIBLE);
                binding.gvVideoGallery.setVisibility(View.GONE);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //========================get photo gallery images task ends===========================


   /* public void showVideoDialog() {

        DialogShowVideoBinding binding1;

        builder = new AlertDialog.Builder(VideoGalleryActivity.this);
        binding1 = DialogShowVideoBinding.inflate(LayoutInflater.from(getApplicationContext()));
        builder.setView(binding1.getRoot());

        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        arrayList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");

        ShowVideoVPAdapter showVideoVPAdapter = new ShowVideoVPAdapter(arrayList, VideoGalleryActivity.this);
        binding1.vpShowVideo.setAdapter(showVideoVPAdapter);

        ad = builder.create();
        ad.show();
    }


    //=====================View Pager Adapter code starts================================

    public class ShowVideoVPAdapter extends PagerAdapter {

        ArrayList<String> arrayList_video_gallery;
        Context context;

        public ShowVideoVPAdapter(ArrayList<String> arrayList_video_gallery, Context context) {
            this.arrayList_video_gallery = arrayList_video_gallery;
            this.context = context;
        }


        @Override
        public int getCount() {
            return arrayList_video_gallery.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            EntityShowVideoBinding video_binding;

            video_binding = EntityShowVideoBinding.inflate(LayoutInflater.from(context));
            View view = video_binding.getRoot();

            MediaController mediaController= new MediaController(context);
            mediaController.setAnchorView(video_binding.vvShowVideo);
            video_binding.vvShowVideo.setVideoPath("" + arrayList_video_gallery.get(position));
            video_binding.vvShowVideo.setMediaController(mediaController);
            video_binding.vvShowVideo.requestFocus();
            video_binding.vvShowVideo.start();

            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    //=====================View Pager Adapter code starts================================*/


}