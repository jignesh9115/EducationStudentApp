package com.ai.educationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ai.educationapp.databinding.ActivityViewGalleryVideoBinding;

public class ViewGalleryVideoActivity extends AppCompatActivity {

    ActivityViewGalleryVideoBinding binding;

    private int position = 0;
    private MediaController mediaController;

    String video_url="",video_title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewGalleryVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle=getIntent().getExtras();
        video_url=bundle.getString("video_url");
        video_title=bundle.getString("video_title");

        binding.rlTitle.setVisibility(View.VISIBLE);

        binding.tvVideoTitle.setText(""+video_title);

        // Set the media controller buttons
        if (this.mediaController == null) {

            this.mediaController = new MediaController(ViewGalleryVideoActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            this.mediaController.setAnchorView(binding.videoView);



            // Set MediaController for VideoView
            this.binding.videoView.setMediaController(mediaController);



        }

        // When the video file ready for playback.
        this.binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                binding.videoView.seekTo(position);
                if (position == 0) {
                    binding.videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        //mediaController.setAnchorView(binding.videoView);
                    }
                });

                styleMediaController(mediaController);
            }
        });

        Uri uri= Uri.parse(video_url);
        binding.videoView.setVideoURI(uri);
        binding.videoView.requestFocus();


        binding.imgViewVideoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", binding.videoView.getCurrentPosition());
        binding.videoView.pause();
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        binding.videoView.seekTo(position);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            binding.rlTitle.setVisibility(View.GONE);
            //Toast.makeText(this, "ORIENTATION_LANDSCAPE", Toast.LENGTH_SHORT).show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            binding.rlTitle.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "ORIENTATION_PORTRAIT", Toast.LENGTH_SHORT).show();

        }


    }

    //================customize mediacontroller code starts===================

    private void styleMediaController(View view) {
        if (view instanceof MediaController) {
            MediaController v = (MediaController) view;
            for(int i = 0; i < v.getChildCount(); i++) {
                styleMediaController(v.getChildAt(i));
            }
        } else
        if (view instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) view;
            for(int i = 0; i < ll.getChildCount(); i++) {
                styleMediaController(ll.getChildAt(i));
            }
        } else if (view instanceof SeekBar) {
            //((SeekBar) view).setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
            ((SeekBar) view).getThumb().setColorFilter( getResources().getColor(R.color.White),
                    PorterDuff.Mode.SRC_IN);

            ((SeekBar) view).getProgressDrawable()
                    .mutate()
                    .setColorFilter(
                            getResources().getColor(R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN);
        }
    }

    //================customize mediacontroller code ends=======================

}