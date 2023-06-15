package com.ai.educationapp.ui.schoolprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.SchoolProfilePOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentSchoolprofileBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SchoolProfileFragment extends Fragment {

    private SchoolProfileViewModel schoolProfileViewModel;

    FragmentSchoolprofileBinding binding;

    String url="",response="";

    ArrayList<SchoolProfilePOJO> schoolProfilePOJOArrayList;
    SchoolProfilePOJO schoolProfilePOJO;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        schoolProfileViewModel =
                ViewModelProviders.of(this).get(SchoolProfileViewModel.class);
        binding=FragmentSchoolprofileBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        schoolProfilePOJOArrayList=new ArrayList<>();

        new getSchoolProfiledata().execute();


        return root;
    }

    //=========================get school profile data task starts==========

    public class getSchoolProfiledata extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url=getString(R.string.Base_url)+getString(R.string.school_detail_url);
            Log.d("School Profile url=>",url+"");

            HttpHandler httpHandler = new HttpHandler();
            response=httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>",response+"");

            //Toast.makeText(getActivity(), "response=>"+response, Toast.LENGTH_SHORT).show();

            getSchoolProfile();

        }
    }

    private void getSchoolProfile() {

        try
        {
            JSONObject jsonObject=new JSONObject(response+"");
           /* JSONArray jsonArray=jsonObject.getJSONArray("data");

            for (int i=0;i<jsonArray.length();i++){*/

                JSONObject object=jsonObject.getJSONObject("data");
                schoolProfilePOJO=new SchoolProfilePOJO(object.getString("school_id"),
                        object.getString("user_id"),
                        object.getString("school_name"),
                        object.getString("school_address"),
                        object.getString("school_state"),
                        object.getString("school_city"),
                        object.getString("school_postal_code"),
                        object.getString("school_phone1"),
                        object.getString("school_phone2"),
                        object.getString("school_email"),
                        object.getString("school_fax"),
                        object.getString("school_logo"),
                        object.getString("school_facebook"),
                        object.getString("school_person_name"));

                schoolProfilePOJOArrayList.add(schoolProfilePOJO);



            RequestOptions options=new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.imagenotfoundicon);
            Glide.with(getActivity()).load(schoolProfilePOJOArrayList.get(0).getSchool_logo())
                    .apply(options)
                    .into(binding.imgSchoolLogo);


            binding.tvSchoolName.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_name());
            binding.tvSchoolAddress.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_address());
            binding.tvSchoolState.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_state());
            binding.tvSchoolCity.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_city());
            binding.tvSchoolPincode.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_postal_code());
            binding.tvSchoolContact1.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_phone1());
            binding.tvSchoolContact2.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_phone2());
            binding.tvSchoolEmail.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_email());
            binding.tvSchoolFax.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_fax());
            binding.tvSchoolFacebook.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_facebook());
            binding.tvSchoolPersonName.setText(""+schoolProfilePOJOArrayList.get(0).getSchool_person_name());


            binding.imgCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+schoolProfilePOJOArrayList.get(0).getSchool_phone1()));
                    startActivity(intent);
                }
            });

            binding.imgCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+schoolProfilePOJOArrayList.get(0).getSchool_phone2()));
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //=========================get school profile data task ends============

}