package com.mobisys.android.androidl;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kbeanie.imagechooser.api.ImageChooserActivity;
import com.mobisys.android.androidl.rest.RestCallback;
import com.mobisys.android.androidl.rest.RestClient;
import com.mobisys.android.androidl.data.Resource;
import com.mobisys.android.androidl.widget.AppUtil;
import com.mobisys.android.androidl.widget.MImageLoader;
import com.mobisys.android.androidl.widget.Preferences;
import com.mobisys.android.androidl.widget.ProgressDialog;

import java.io.File;
import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;

/**
 * Created by vikas on 6/23/15.
 */
public class ChooseImagesActivity extends AppCompatActivity {

    private Dialog mPg;
    private int mSelectedImagePos;
    private ImageView mSelectedImageView;
    private static final int IMAGE_CHOOSER = 100;
    private ArrayList<Resource> mResourceList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_choose_image);
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mResourceList = new ArrayList<Resource>();

        initScreen();
    }

    private void initScreen() {
        findViewById(R.id.btn_add_photo_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedImagePos = 0;
                mSelectedImageView = (ImageView) findViewById(R.id.image_1);
                showImageChooserActivity();
            }
        });

        findViewById(R.id.btn_add_photo_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedImagePos = 1;
                mSelectedImageView = (ImageView) findViewById(R.id.image_2);
                showImageChooserActivity();
            }
        });


        findViewById(R.id.btn_add_photo_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedImagePos = 2;
                mSelectedImageView = (ImageView) findViewById(R.id.image_3);
                showImageChooserActivity();
            }
        });


        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if((mResourceList==null||mResourceList.size()==0)){
            AppUtil.showErrorDialog("You must submit atleast one photo.", ChooseImagesActivity.this);
            return;
        }
        uploadImages();
    }

    private void uploadImages() {
        ArrayList<String> imageStringPath=new ArrayList<String>();
        ArrayList<String> mImageType=new ArrayList<String>();

        if(mResourceList!=null && mResourceList.size()>0){
            for(int i=0;i<mResourceList.size();i++){
                if(mResourceList.get(i).getUri()!=null && mResourceList.get(i).isDirty()){ //while edit add uri and url to imagepath
                    String imagepath=AppUtil.getRealPathFromURI(Uri.parse(mResourceList.get(i).getUri()), ChooseImagesActivity.this);
                    if(Preferences.DEBUG) Log.d("Activity", "***image path:" + imagepath);
                    imageStringPath.add(imagepath);

                    String imageType=AppUtil.GetMimeType(ChooseImagesActivity.this, Uri.parse(mResourceList.get(i).getUri()));
                    if(Preferences.DEBUG) Log.d("Activity", "***image type: "+imageType);
                    mImageType.add(imageType);
                }
                else {
                    if(mResourceList.get(i).getUrl()!=null){
                        imageStringPath.add(mResourceList.get(i).getUrl());
                        mImageType.add("null");
                    }
                }
            }
        }

        final Dialog progressDialog = ProgressDialog.show(ChooseImagesActivity.this, "Submitting");
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        TypedFile typedFile = new TypedFile(".jpg", new File(imageStringPath.get(0)));
        multipartTypedOutput.addPart("image", typedFile);


        RestClient.getImageApi(ChooseImagesActivity.this).uploadImage(multipartTypedOutput, new RestCallback<String>() {
            @Override
            public void failure(String restErrors, boolean networkError) {

            }

            @Override
            public void success(String s, Response response) {

            }
        });
    }


    private void showImageChooserActivity() {
        Intent intent = new Intent(ChooseImagesActivity.this, ImageChooserActivity.class);
        startActivityForResult(intent, IMAGE_CHOOSER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_CHOOSER){
            if(resultCode == RESULT_OK) {
                String path = data.getStringExtra(ImageChooserActivity.INTENT_IMAGE_BANNER_PATH);
                Uri selectedImage = Uri.fromFile(new File(path));

                onImageSelected(selectedImage);
                if(mSelectedImageView!=null)
                    MImageLoader.displayImage(ChooseImagesActivity.this, selectedImage.toString(), mSelectedImageView, R.drawable.user_stub);
            } else {
                if(data!=null){
                    String reason = data.getStringExtra(ImageChooserActivity.INTENT_ERROR_MESSAGE);
                    AppUtil.showErrorDialog(reason, ChooseImagesActivity.this);
                }
            }
        }
    }

    private void onImageSelected(Uri imageUri){
        //Put Uri into arraylist
        if(mSelectedImagePos<mResourceList.size()){
            mResourceList.get(mSelectedImagePos).setDirty(true);
            mResourceList.get(mSelectedImagePos).setUri(imageUri.toString());
        }
        else{
            Resource r=new Resource();
            r.setDirty(true);
            r.setUri(imageUri.toString());
            mResourceList.add(r);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
