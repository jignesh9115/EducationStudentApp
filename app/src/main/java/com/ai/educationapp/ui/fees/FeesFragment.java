package com.ai.educationapp.ui.fees;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.educationapp.Adapters.FeeDetailRVAdapter;
import com.ai.educationapp.Adapters.FeeHistoryRVAdapter;
import com.ai.educationapp.Adapters.FeeInstallmentRVAdapter;
import com.ai.educationapp.GDateTime;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.FeeDetailPOJO;
import com.ai.educationapp.POJOs.StudentFeeDetailPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.DialogFeeDetailBinding;
import com.ai.educationapp.databinding.FragmentFeesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeesFragment extends Fragment {

    private FeesViewModel feesViewModel;
    private Menu menu;

    FragmentFeesBinding binding;

    String sfdurl = "", sfdresponse = "", fdurl = "", fdresponse = "";
    ArrayList<StudentFeeDetailPOJO> arrayList_fee_install, arrayList_fee_history;
    StudentFeeDetailPOJO studentFeeDetailPOJO;

    FeeInstallmentRVAdapter feeInstallmentRVAdapter;

    FeeHistoryRVAdapter feeHistoryRVAdapter;

    RecyclerView.LayoutManager layoutManager_fee_install, layoutManager_fee_history,layoutManager_fee_detail;

    DialogFeeDetailBinding binding1;

    ArrayList<FeeDetailPOJO> arrayList_fee_detail;
    FeeDetailPOJO feeDetailPOJO;

    FeeDetailRVAdapter feeDetailRVAdapter;

    SharedPreferences sp;

    int student_id = 0, standard_id = 0, fee_installment_tot = 0, fee_installment = 0, fee_history_tot = 0, fee_history = 0;

    GDateTime gDateTime = new GDateTime();

    AlertDialog ad;
    AlertDialog.Builder builder;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feesViewModel =
                ViewModelProviders.of(this).get(FeesViewModel.class);
        binding = FragmentFeesBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        layoutManager_fee_install = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        layoutManager_fee_history = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        sp = getActivity().getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        student_id = sp.getInt("student_id", 0);
        standard_id = sp.getInt("standard_id", 0);

        arrayList_fee_install = new ArrayList<>();
        arrayList_fee_history = new ArrayList<>();

        new getStudentFeeDetailTask().execute();

        return root;
    }

    //====================get student fee detail data task starts====================

    public class getStudentFeeDetailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog=new ProgressDialog(getActivity());
            pdialog.setCancelable(false);
            pdialog.setMessage("Loading...");
            pdialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            sfdurl = getString(R.string.Base_url) + getString(R.string.get_student_fee_detail_url) + student_id;
            Log.d("Student FeeDetail url=>", sfdurl + "");

            HttpHandler httpHandler = new HttpHandler();
            sfdresponse = httpHandler.makeServiceCall(sfdurl);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("sfdresponse=>", sfdresponse + "");

            getStudentFeeDetail();

            if (pdialog.isShowing()){

                pdialog.dismiss();

            }

        }
    }

    public void getStudentFeeDetail() {

        try {

            JSONObject jsonObject = new JSONObject(sfdresponse + "");

            JSONObject dataobject = jsonObject.getJSONObject("data");

            JSONArray feeinstallarray = dataobject.getJSONArray("fee_install");

            for (int i = 0; i < feeinstallarray.length(); i++) {

                JSONObject feeinstallbject = feeinstallarray.getJSONObject(i);
                studentFeeDetailPOJO = new StudentFeeDetailPOJO(feeinstallbject.getString("id"),
                        feeinstallbject.getString("student_id"),
                        feeinstallbject.getString("title"),
                        feeinstallbject.getString("fee_type_id"),
                        feeinstallbject.getString("due_date"),
                        feeinstallbject.getString("paid_date"),
                        feeinstallbject.getString("comments"),
                        feeinstallbject.getString("base_amount"),
                        feeinstallbject.getString("receipt_url"));

                fee_installment = Integer.parseInt(feeinstallbject.getString("base_amount"));
                fee_installment_tot = fee_installment_tot + fee_installment;

                arrayList_fee_install.add(studentFeeDetailPOJO);
            }

            feeInstallmentRVAdapter = new FeeInstallmentRVAdapter(arrayList_fee_install, getActivity());
            binding.rvFeeInstallments.setLayoutManager(layoutManager_fee_install);
            binding.rvFeeInstallments.setAdapter(feeInstallmentRVAdapter);


            JSONArray feehistoryarray = dataobject.getJSONArray("fee_history");

            for (int i = 0; i < feehistoryarray.length(); i++) {

                JSONObject feehistorygobject = feehistoryarray.getJSONObject(i);
                studentFeeDetailPOJO = new StudentFeeDetailPOJO(feehistorygobject.getString("id"),
                        feehistorygobject.getString("student_id"),
                        feehistorygobject.getString("title"),
                        feehistorygobject.getString("fee_type_id"),
                        feehistorygobject.getString("due_date"),
                        feehistorygobject.getString("paid_date"),
                        feehistorygobject.getString("comments"),
                        feehistorygobject.getString("base_amount"),
                        feehistorygobject.getString("receipt_url"));

                fee_history = Integer.parseInt(feehistorygobject.getString("base_amount"));
                fee_history_tot = fee_history_tot + fee_history;

                arrayList_fee_history.add(studentFeeDetailPOJO);
            }

            feeHistoryRVAdapter = new FeeHistoryRVAdapter(arrayList_fee_history, getActivity());
            binding.rvFeePaymentHistoty.setLayoutManager(layoutManager_fee_history);
            binding.rvFeePaymentHistoty.setAdapter(feeHistoryRVAdapter);

            binding.tvNextInstallmentsAmount.setText("₹ " + dataobject.getString("next_install_amount"));
            binding.tvNextInstallmentsDueDate.setText("Due on " + gDateTime.ymdTodmy(dataobject.getString("next_install_date")));

            binding.tvTotalFeeInstallment.setText("Total Installment : ₹ " + fee_installment_tot);
            binding.tvTotalFeeHistory.setText("Grand Total : ₹ " + fee_history_tot);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //====================get student fee detail data task ends======================


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_fees_details) {

            showDialog();

        }
        return super.onOptionsItemSelected(item);
    }


    public void showDialog(){



        builder=new AlertDialog.Builder(getActivity());

        binding1=DialogFeeDetailBinding.inflate(LayoutInflater.from(getContext()));
        builder.setView(binding1.getRoot());

        layoutManager_fee_detail = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        arrayList_fee_detail=new ArrayList<>();
        new getFeeDetailTask().execute();

        binding1.btnOkFeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad.dismiss();

            }
        });

        ad=builder.create();
        ad.show();

    }

    //=====================get student fee detail task starts=================

    public class getFeeDetailTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            fdurl=getString(R.string.Base_url)+getString(R.string.get_fee_detail_url)+standard_id;
            Log.d("Fees Detail url=>",fdurl+"");

            HttpHandler httpHandler = new HttpHandler();
            fdresponse=httpHandler.makeServiceCall(fdurl);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("fdresponse=>",fdresponse+"");

            getFeeDetail();
        }
    }

    public void getFeeDetail(){

        try
        {
            JSONObject jsonObject=new JSONObject(fdresponse+"");

            JSONArray dataarray=jsonObject.getJSONArray("data");

            for (int i=0;i<dataarray.length();i++){

                JSONObject dataobject=dataarray.getJSONObject(i);

                JSONArray fees_detailsarray=dataobject.getJSONArray("fees_details");

                for (int j=0;j<fees_detailsarray.length();j++){

                    JSONObject fees_detailsobject=fees_detailsarray.getJSONObject(j);

                    feeDetailPOJO=new FeeDetailPOJO(fees_detailsobject.getString("id"),
                            fees_detailsobject.getString("title"),
                            fees_detailsobject.getString("fee_type_id"),
                            fees_detailsobject.getString("base_amount"));

                    arrayList_fee_detail.add(feeDetailPOJO);
                }

                binding1.tvFeeDetailTitle.setText(""+dataobject.getString("title"));
                binding1.tvFeeDetailTot.setText("Total Fees : ₹ "+dataobject.getString("base_amount"));


            }
            feeDetailRVAdapter=new FeeDetailRVAdapter(arrayList_fee_detail,getActivity());
            binding1.rvFeeDetail.setLayoutManager(layoutManager_fee_detail);
            binding1.rvFeeDetail.setAdapter(feeDetailRVAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //=====================get student fee detail task ends===================

}