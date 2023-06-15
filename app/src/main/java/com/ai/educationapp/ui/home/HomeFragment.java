package com.ai.educationapp.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.ai.educationapp.Adapters.MainSliderVPAdapter;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentHomeBinding;
import com.ai.educationapp.ui.assignment.AssignmentFragment;
import com.ai.educationapp.ui.attendence.AttendenceFragment;
import com.ai.educationapp.ui.exam.ExamFragment;
import com.ai.educationapp.ui.fees.FeesFragment;
import com.ai.educationapp.ui.holiday.HolidayFragment;
import com.ai.educationapp.ui.leaveapplication.LeaveApplicationFragment;
import com.ai.educationapp.ui.newsandevent.NewsAndEventFragment;
import com.ai.educationapp.ui.notice.NoticeFragment;
import com.ai.educationapp.ui.notification.NotificationFragment;
import com.ai.educationapp.ui.photogallery.PhotoGalleryFragment;
import com.ai.educationapp.ui.profile.ProfileFragment;
import com.ai.educationapp.ui.result.ResultFragment;
import com.ai.educationapp.ui.schoolprofile.SchoolProfileFragment;
import com.ai.educationapp.ui.teacher.TeacherFragment;
import com.ai.educationapp.ui.timetable.TimeTableFragment;
import com.ai.educationapp.ui.videogallery.VideoGalleryFragment;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ArrayList<String> arrayList_images;

    FragmentHomeBinding binding;
    MainSliderVPAdapter mainSliderVPAdapter;

    Timer timer;
    int current_position = 0;

   /* private int position=0;
    private boolean end=false;*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();


        arrayList_images = new ArrayList<>();
        arrayList_images.add("https://psmag.com/.image/t_share/MTI4NTEwMTQ0MzQ5Nzk0Mjc0/shutterstock_211281496jpg.jpg");
        arrayList_images.add("https://static.wixstatic.com/media/77fb94_1e15e5f68cd54e168336139adca1ca12~mv2.jpg");
        arrayList_images.add("https://cdn9.dissolve.com/p/D430_35_457/D430_35_457_1200.jpg");

        mainSliderVPAdapter = new MainSliderVPAdapter(arrayList_images, getActivity());
        binding.mainSliderView.setAdapter(mainSliderVPAdapter);

     /*   Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AutoScrollTask(), 3000, 5000);*/

        createAutoSlide();

        binding.mainSliderIndicatorView.setSliderColor(Color.parseColor("#ffffff"), Color.parseColor("#FF4500"))
                .setSliderWidth(30)
                .setSliderHeight(30)
                .setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setupWithViewPager(binding.mainSliderView);


        binding.cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ProfileFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new AttendenceFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new AssignmentFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ExamFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ResultFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new TeacherFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardVideoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new VideoGalleryFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new HolidayFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardNewsandevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new NewsAndEventFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new NoticeFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardSchoolprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new SchoolProfileFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new TimeTableFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardLeaveApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new LeaveApplicationFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new FeesFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new PhotoGalleryFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        binding.cardNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new NotificationFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        current_position = 0;
        createAutoSlide();

    }

    private void createAutoSlide() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (current_position == arrayList_images.size()) {
                    current_position = 0;
                }

                binding.mainSliderView.setCurrentItem(current_position++, true);

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(runnable);

            }
        }, 20000, 5000);

    }

   /* private class AutoScrollTask extends TimerTask {
        @Override
        public void run() {
            if(position == arrayList_images.size() -1){
                end = true;
            } else if (position == 0) {
                end = false;
            }
            if(!end){
                position++;
            } else {
                position--;
            }

            binding.mainSliderView.setCurrentItem(position,true);

        }
    }*/

}