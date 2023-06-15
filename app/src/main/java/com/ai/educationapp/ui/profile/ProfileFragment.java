package com.ai.educationapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.ai.educationapp.Adapters.StudentListRVAdapter;
import com.ai.educationapp.ChildListActivity;
import com.ai.educationapp.GDateTime;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.StudentDetailPOJO;
import com.ai.educationapp.POJOs.StudentListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    FragmentProfileBinding binding;

    String url="",response="";

    ArrayList<StudentDetailPOJO> studentListPOJOArrayList;
    StudentDetailPOJO  studentDetailPOJO;

    SharedPreferences sp;

    int student_id=0;

    GDateTime gDateTime=new GDateTime();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        binding=FragmentProfileBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp=getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        student_id=sp.getInt("student_id",0);

        //Toast.makeText(getActivity(), "student_id=>"+student_id, Toast.LENGTH_SHORT).show();

        studentListPOJOArrayList=new ArrayList<>();
        new getStudentDetail().execute();

        return root;
    }

    //===========get student details task starts================

    public class getStudentDetail extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url=getString(R.string.Base_url)+getString(R.string.student_detail_url)+student_id;
            Log.d("student details url=>",url+"");

            HttpHandler httpHandler = new HttpHandler();
            response=httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>",response+"");
            getStudentDetail();

        }
    }

    public void getStudentDetail(){


        try {

            JSONObject jsonObject = new JSONObject(response + "");

            //for (int i = 0; i < jsonObject.length(); i++) {

            if (jsonObject.getString("status").equalsIgnoreCase("1") && jsonObject.getString("html").equalsIgnoreCase("View successfully.")) {

                //JSONArray jsonArray=jsonObject.getJSONArray("data");

                JSONObject object = jsonObject.getJSONObject("data");

                studentDetailPOJO = new StudentDetailPOJO(object.getString("student_name"),
                        object.getString("student_birthdate"),
                        object.getString("student_roll_no"),
                        object.getString("student_photo"),
                        object.getString("student_standard"),
                        object.getString("student_division"),
                        object.getString("student_gnr_no"),
                        object.getString("student_address"),
                        object.getString("student_address2"),
                        object.getString("student_city"),
                        object.getString("student_district"),
                        object.getString("student_state"),
                        object.getString("student_pincode"),
                        object.getString("student_phone"),
                        object.getString("student_email"),
                        object.getString("student_gender"),
                        object.getString("standard_id"));

                studentListPOJOArrayList.add(studentDetailPOJO);

            }else {
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }

            RequestOptions options=new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.profileblueicon);
            Glide.with(getActivity()).load(studentListPOJOArrayList.get(0).getStudent_photo())
                    .apply(options)
                    .into(binding.cimgStudentProfile);

            binding.tvStudentNameViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_name());
            binding.tvStudentStandardViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_standard());
            binding.tvStudentDivisionViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_division());
            binding.tvStudentRollnoViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_roll_no());
            binding.tvStudentGrnoViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_gnr_no());
            binding.tvStudentBirthdateViewProfile.setText(gDateTime.ymdTodmy(studentListPOJOArrayList.get(0).getStudent_birthdate()));
            binding.tvStudentGenderViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_gender());
            binding.tvStudentAddressViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_address()+" "+studentListPOJOArrayList.get(0).getStudent_address2());
            binding.tvStudentCityViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_city());
            binding.tvStudentDistrictViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_district());
            binding.tvStudentStateViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_state());
            binding.tvStudentPincodeViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_pincode());
            binding.tvStudentPhoneViewProfile.setText(studentListPOJOArrayList.get(0).getStudent_phone());


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    //===========get student details task ends==================
}