package com.ai.educationapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ai.educationapp.POJOs.NoticeListPOJO;
import com.ai.educationapp.databinding.EntityNoticeListBinding;
import com.ai.educationapp.databinding.EntityResultSubjectDetailsBinding;
import com.ai.educationapp.databinding.NoticeDetialDialogBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NoticeListAdapter extends BaseAdapter {

    ArrayList<NoticeListPOJO> noticeListPOJOArrayList;
    Context context;

    EntityNoticeListBinding binding;

    AlertDialog ad;
    AlertDialog.Builder builder;

    public NoticeListAdapter(ArrayList<NoticeListPOJO> noticeListPOJOArrayList, Context context) {
        this.noticeListPOJOArrayList = noticeListPOJOArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return noticeListPOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeListPOJOArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return noticeListPOJOArrayList.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            binding = EntityNoticeListBinding.inflate(LayoutInflater.from(context), parent, false);
            view = binding.getRoot();
        }

        binding.tvNoticeDate.setText("" + noticeListPOJOArrayList.get(position).getOn_date());
        binding.tvNoticeTitle.setText("" + noticeListPOJOArrayList.get(position).getNotice_type());
        binding.tvNoticeDescription.setText("" + noticeListPOJOArrayList.get(position).getNotice_description());

        binding.tvNoticeShowMore.setVisibility(View.GONE);

        Layout layout = binding.tvNoticeDescription.getLayout();
        if(layout != null) {
            int lines = layout.getLineCount();
            if(lines > 0) {
                int ellipsisCount = layout.getEllipsisCount(lines-1);
                if ( ellipsisCount > 0) {
                    Log.d(TAG, "Text is ellipsized");
                    binding.tvNoticeShowMore.setVisibility(View.VISIBLE);
                }
            }
        }

        binding.cardNoticeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoticeDetialDialogBinding binding1;

                builder = new AlertDialog.Builder(context);

                binding1 = NoticeDetialDialogBinding.inflate(LayoutInflater.from(context), parent, false);
                builder.setView(binding1.getRoot());

                binding1.tvNoticeDateDetailDialog.setText("" + noticeListPOJOArrayList.get(position).getOn_date());
                binding1.tvNoticeTitleDetailDialog.setText("" + noticeListPOJOArrayList.get(position).getNotice_type());
                binding1.tvNoticeDescriptionDetailDialog.setText("" + noticeListPOJOArrayList.get(position).getNotice_description());

                binding1.bntOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ad.dismiss();

                    }
                });

                ad=builder.create();
                ad.show();
            }
        });

        return view;
    }
}
