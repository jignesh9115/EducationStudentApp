package com.ai.educationapp.ui.assignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.Adapters.AssignmentListRVAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.AssignmentPOJO;
import com.ai.educationapp.POJOs.AssignmentSubjectListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.SubmitAssignment;
import com.ai.educationapp.databinding.EntityAssignmentListBinding;
import com.ai.educationapp.databinding.EntityAssignmentSubjectListBinding;
import com.ai.educationapp.databinding.FragmentAssignmentBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {

    private AssignmentViewModel assignmentViewModel;

    FragmentAssignmentBinding binding;

    String url = "", response = "";

    ArrayList<AssignmentPOJO> arrayList_assignmentlist;
    AssignmentPOJO assignmentPOJO;

    List<AssignmentSubjectListPOJO> arrayList_subject_list;
    AssignmentSubjectListPOJO assignmentSubjectListPOJO;

    AssignmentListRVAdapter assignmentListRVAdapter;

    RecyclerView.LayoutManager layoutManager, layoutManager_assignment_list;

    SharedPreferences sp;

    int student_id = 0, standard_id = 0;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assignmentViewModel =
                ViewModelProviders.of(this).get(AssignmentViewModel.class);

        binding = FragmentAssignmentBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgAssignmentNotFound.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);


        sp = getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        student_id = sp.getInt("student_id", 0);
        standard_id = sp.getInt("standard_id", 0);

        arrayList_subject_list = new ArrayList<>();


      /*  arrayList_subject_list.add("Maths");
        arrayList_subject_list.add("Science");
        arrayList_subject_list.add("English");*/

        new getAssignmentTask().execute();

        binding.fabSubmitWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), SubmitAssignment.class));

            }
        });


        return root;
    }

    //====================get assignment list task starts=============

    public class getAssignmentTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = new ProgressDialog(getActivity());
            pdialog.setCancelable(false);
            pdialog.setMessage("Loading...");
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = getString(R.string.Base_url) + "listassignment.php?standard_id=" + standard_id + "&student_id=1";
            Log.d("get Assignment list=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            getAssignmentdata();

            if (pdialog.isShowing()) {

                pdialog.dismiss();

            }
        }
    }

    public void getAssignmentdata() {

        try {

            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.rvAssignmentSubjectList.setVisibility(View.VISIBLE);
                binding.imgAssignmentNotFound.setVisibility(View.GONE);

          /*  Gson gson = new Gson();
            Type listType = new TypeToken<List<AssignmentSubjectListPOJO>>() {
            }.getType();
            arrayList_subject_list = gson.fromJson(jsonArray.toString(), listType);


            AssignmentSubjectListAdapter assignmentSubjectListAdapter=new AssignmentSubjectListAdapter(arrayList_subject_list,getActivity());
            binding.rvAssignmentSubjectList.setLayoutManager(layoutManager);
            binding.rvAssignmentSubjectList.setAdapter(assignmentSubjectListAdapter);*/


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject dataobject = jsonArray.getJSONObject(i);


                    JSONArray assignment_details_array = dataobject.getJSONArray("assignment_details");

                    arrayList_assignmentlist = new ArrayList<>();

                    for (int j = 0; j < assignment_details_array.length(); j++) {

                        JSONObject assignment_details_object = assignment_details_array.getJSONObject(j);

                        assignmentPOJO = new AssignmentPOJO(assignment_details_object.getString("id"),
                                assignment_details_object.getString("due_date"),
                                assignment_details_object.getString("assignment_description"),
                                assignment_details_object.getString("title"),
                                assignment_details_object.getString("teacher_id"),
                                assignment_details_object.getString("standard_id"),
                                assignment_details_object.getString("subject_id"),
                                assignment_details_object.getString("assign_date"),
                                assignment_details_object.getString("status"));

                        arrayList_assignmentlist.add(assignmentPOJO);

                    }


                    assignmentSubjectListPOJO = new AssignmentSubjectListPOJO(dataobject.getString("id"),
                            dataobject.getString("subject_title"),
                            arrayList_assignmentlist);


                    arrayList_subject_list.add(assignmentSubjectListPOJO);

                    AssignmentSubjectListAdapter assignmentSubjectListAdapter = new AssignmentSubjectListAdapter(arrayList_subject_list, getActivity());
                    binding.rvAssignmentSubjectList.setLayoutManager(layoutManager);
                    binding.rvAssignmentSubjectList.setAdapter(assignmentSubjectListAdapter);

                }
            } else {

                binding.imgAssignmentNotFound.setVisibility(View.VISIBLE);
                binding.rvAssignmentSubjectList.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //====================get assignment list task ends===============


    //=====================Custome Adapter for subject list code starts==================

    public class AssignmentSubjectListAdapter extends RecyclerView.Adapter<AssignmentSubjectListAdapter.MyHolder> {

        List<AssignmentSubjectListPOJO> arrayList_subject_list;
        Context context;


        public AssignmentSubjectListAdapter(List<AssignmentSubjectListPOJO> arrayList_subject_list, Context context) {
            this.arrayList_subject_list = arrayList_subject_list;
            this.context = context;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyHolder(EntityAssignmentSubjectListBinding.inflate(LayoutInflater.from(context), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            holder.binding.tvAssignmentSubjectName.setText(arrayList_subject_list.get(position).getSubject_title());


            layoutManager_assignment_list = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

            assignmentListRVAdapter = new AssignmentListRVAdapter(arrayList_subject_list.get(position).getArrayList_assignment_list(), getActivity());
            holder.binding.rvAssignmentList.setLayoutManager(layoutManager_assignment_list);
            holder.binding.rvAssignmentList.setAdapter(assignmentListRVAdapter);

        }

        @Override
        public int getItemCount() {
            return arrayList_subject_list.size();

        }

        public class MyHolder extends RecyclerView.ViewHolder {

            EntityAssignmentSubjectListBinding binding;

            public MyHolder(EntityAssignmentSubjectListBinding b) {
                super(b.getRoot());
                binding = b;

            }
        }

    }

    //=====================Custome Adapter for subject list code ends====================


}