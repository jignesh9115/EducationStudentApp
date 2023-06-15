package com.ai.educationapp.ui.notice;

import android.app.ProgressDialog;
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

import com.ai.educationapp.Adapters.NoticeListAdapter;
import com.ai.educationapp.HttpHandler;
import com.ai.educationapp.POJOs.NoticeListPOJO;
import com.ai.educationapp.R;
import com.ai.educationapp.databinding.FragmentNoticeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private NoticeViewModel noticeViewModel;

    FragmentNoticeBinding binding;

    String url = "", response = "";

    ArrayList<NoticeListPOJO> noticeListPOJOArrayList;
    NoticeListPOJO noticeListPOJO;

    NoticeListAdapter noticeListAdapter;

    ProgressDialog pdialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noticeViewModel =
                ViewModelProviders.of(this).get(NoticeViewModel.class);
        binding = FragmentNoticeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.imgNoticeNotFound.setVisibility(View.GONE);

        noticeListPOJOArrayList = new ArrayList<>();
        new getNoticeDataTask().execute();

        return root;
    }

    //================get notice data task starts======================

    public class getNoticeDataTask extends AsyncTask<Void, Void, Void> {
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

            url = getString(R.string.Base_url) + getString(R.string.get_notice_list_url);
            Log.d("get Notice List url=>", url + "");

            HttpHandler httpHandler = new HttpHandler();
            response = httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("response=>", response + "");

            getNotiveData();

            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
        }
    }

    private void getNotiveData() {

        try {
            JSONObject jsonObject = new JSONObject(response + "");
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) {

                binding.imgNoticeNotFound.setVisibility(View.GONE);
                binding.lvNotice.setVisibility(View.VISIBLE);


                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);
                    noticeListPOJO = new NoticeListPOJO(object.getString("notice_id"),
                            object.getString("notice_description"),
                            object.getString("notice_type"),
                            object.getString("notice_status"),
                            object.getString("on_date"),
                            object.getString("notice_date"));

                    noticeListPOJOArrayList.add(noticeListPOJO);
                }

                noticeListAdapter = new NoticeListAdapter(noticeListPOJOArrayList, getActivity());
                binding.lvNotice.setAdapter(noticeListAdapter);

            } else {

                binding.imgNoticeNotFound.setVisibility(View.GONE);
                binding.lvNotice.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //================get notice data task ends========================

}