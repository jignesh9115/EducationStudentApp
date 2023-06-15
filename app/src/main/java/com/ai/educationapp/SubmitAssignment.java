package com.ai.educationapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ai.educationapp.Adapters.AttachmentListRVAdapter;
import com.ai.educationapp.POJOs.FileAttachementPOJO;
import com.ai.educationapp.databinding.ActivitySubmitAssignmentBinding;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.util.ArrayList;

public class SubmitAssignment extends AppCompatActivity {

    ActivitySubmitAssignmentBinding binding;

    //====================variable for images start================
    private int PICK_FILE_REQUEST = 2;

    //Uri to store the image uri
    Uri uri = null;

    String attachmentFile_Name = "";
    String upload_url = "";

    String path = "";

    String[] permissionsRequired = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    ArrayList<FileAttachementPOJO> arrayList_FileAttachement;
    ArrayList<String> arrayList_attachment_path;

    //====================variable for images end====================

    AttachmentListRVAdapter attachmentListRVAdapter;

    RecyclerView.LayoutManager layoutManager;

    String assignment_description="",assign_date="",assign_id="",attachment_url="",assign_title="",assign_status="";

    GDateTime gDateTime = new GDateTime();

    SharedPreferences sp;

    int student_id=0;

    String url="",response="";

    AlertDialog ad;
    AlertDialog.Builder builder;

    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySubmitAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp=getSharedPreferences("student_Detail", Context.MODE_PRIVATE);
        student_id=sp.getInt("student_id",0);

        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);

        Bundle bundle=getIntent().getExtras();
        assign_id=bundle.getString("assign_id");
        assign_title=bundle.getString("assign_title");
        assign_status=bundle.getString("assign_status");


        if (assign_status.equalsIgnoreCase("1")) {

            binding.tvSubmitAssignmentStatus.setTextColor(Color.parseColor("#FF3DDC84"));
            binding.tvSubmitAssignmentStatus.setText("Assigned");

        } else if (assign_status.equalsIgnoreCase("0")) {

            binding.tvSubmitAssignmentStatus.setTextColor(Color.RED);
            binding.tvSubmitAssignmentStatus.setText("Missing");

        }

        binding.tvSubmitAssignmentWork.setText(""+assign_title);


        //Toast.makeText(this, ""+assign_id, Toast.LENGTH_SHORT).show();

        arrayList_attachment_path = new ArrayList<>();
        arrayList_FileAttachement = new ArrayList<>();

        binding.imgBackSubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.tvSubmitAssignmentAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AskPermissions(SubmitAssignment.this, permissionsRequired)) {
                    ActivityCompat.requestPermissions(SubmitAssignment.this, permissionsRequired, 1);
                } else {
                    showFileChooser();
                }
            }
        });

        binding.imgSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assignment_description=assignment_description+" "+binding.edtAddComment.getText().toString();

               binding.edtAddComment.setText(" ");

            }
        });

        binding.btnSubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assign_date=gDateTime.getDateymd();


                //Toast.makeText(SubmitAssignment.this, "assign_date=>"+assign_date+"\nassignment_description=>"+assignment_description+"\nstudent_id=>"+student_id+"\nassign_id=>"+assign_id, Toast.LENGTH_SHORT).show();

               new addAssignmentTask().execute();

            }
        });

    }

    //====================add attachment code starts==========================================

    public static boolean AskPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //===================choose image from device code start==============
    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "select multiple files"), PICK_FILE_REQUEST);
    }
    //=====================choose image from device code ends================

    //===================get path code strart=================================

    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    //================get path code ends=================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        if (clipData == null) {

            uri = data.getData();
            upload_url = getFilePath(getApplicationContext(),uri);
            attachmentFile_Name = "" + upload_url.toString().substring(upload_url.toString().lastIndexOf('/') + 1);
            Log.d("attachmentFile_Name =", attachmentFile_Name + "");

            arrayList_FileAttachement.add(new FileAttachementPOJO(attachmentFile_Name, uri));

            attachmentListRVAdapter = new AttachmentListRVAdapter(arrayList_FileAttachement, SubmitAssignment.this);
            binding.rvSubmitAssignmentList.setLayoutManager(layoutManager);
            binding.rvSubmitAssignmentList.setAdapter(attachmentListRVAdapter);

        } else if (clipData != null) {

            Log.d("clipData.getItemCount", clipData.getItemCount() + "");
            for (int i = 0; i < clipData.getItemCount(); i++) {

                ClipData.Item item = clipData.getItemAt(i);
                uri = item.getUri();
                upload_url = getFilePath(getApplicationContext(),uri);

                attachmentFile_Name = "" + upload_url.toString().substring(upload_url.toString().lastIndexOf('/') + 1);
                //attachmentFile_Name = "" + uri.toString();
                Log.d("attachmentFile_Name =", attachmentFile_Name + "");

                arrayList_FileAttachement.add(new FileAttachementPOJO(attachmentFile_Name, uri));

                attachmentListRVAdapter = new AttachmentListRVAdapter(arrayList_FileAttachement, SubmitAssignment.this);
                binding.rvSubmitAssignmentList.setLayoutManager(layoutManager);
                binding.rvSubmitAssignmentList.setAdapter(attachmentListRVAdapter);

            }

        }

    }

    //====================add attachment code starts==========================================


    //====================upload attachment files code starts=============================

    public class uploadAttachmentTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog=new ProgressDialog(getApplicationContext());
            pdialog.setCancelable(false);
            pdialog.setMessage("please wait...");
            pdialog.show();

            arrayList_attachment_path = new ArrayList<>();

            for (int i = 0; i < arrayList_FileAttachement.size(); i++) {
                arrayList_attachment_path.add(getFilePath(getApplicationContext(),arrayList_FileAttachement.get(i).getUri()));
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {

            attachment_url = getString(R.string.Base_url) + "wsa_product_photo_multipart.php";
            Log.d("attachment_url=>", attachment_url);

            for (int i = 0; i < arrayList_FileAttachement.size(); i++) {
                try {

                    Log.d("path=>", arrayList_attachment_path.get(i));
                    Log.d("name=>", arrayList_FileAttachement.get(i).getName());
                    new MultipartUploadRequest(getApplicationContext(), arrayList_FileAttachement.get(i).getName(), attachment_url)
                            .addFileToUpload(arrayList_attachment_path.get(i), "uploadedfile")
                            .addParameter("name", arrayList_FileAttachement.get(i).getName())
                            .setMaxRetries(2)
                            .setMethod("POST")
                            .startUpload();

                    //multiimageurl = getString(R.string.live_ip) + "wsa_product_photo_insert.php?product_id=" + product_id + "&multiphoto_url=" + arrayList_img_from_grllery.get(i).getName();
                    //new multiimageUploadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, multiimageurl.replace(" ", "%20"));

                } catch (Exception e) {
                    //Toast.makeText(AddNewProducts.this, "Image Upload ERROR"+e, Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pdialog.isShowing()){

                pdialog.dismiss();

            }

        }
    }

    //====================upload attachment files code ends===============================

    //===========================add assignment task starts================================

    public class addAssignmentTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url=getString(R.string.Base_url)+getString(R.string.add_assignment_url)+assign_date+"&assignment_description="+assignment_description+"&student_id="+student_id+"&assign_id="+assign_id;
            Log.d("add assignment url=>",url+"");

            HttpHandler httpHandler = new HttpHandler();
            response=httpHandler.makeServiceCall(url);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("addassign response=>",response+"");

            if (response.contains("added successfully.")){

                showDialog();

            }else {

                Toast.makeText(SubmitAssignment.this, "something went wrong...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //===========================add assignment task ends==================================

    public void showDialog(){

        builder=new AlertDialog.Builder(SubmitAssignment.this);
        builder.setCancelable(false);
        builder.setMessage("Assignment Submitted Successfully...");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        ad=builder.create();
        ad.show();

        ad.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }

}