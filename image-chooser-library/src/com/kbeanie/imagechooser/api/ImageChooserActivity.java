package com.kbeanie.imagechooser.api;

import com.kbeanie.imagechooser.R;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class ImageChooserActivity extends Activity implements ImageChooserListener{

	public static final String INTENT_IMAGE_THUMBNAIL_PATH = "intent_image_thumbnail_path";
	public static final String INTENT_IMAGE_BANNER_PATH = "intent_image_banner_path";
	public static final String INTENT_IMAGE_PATH = "intent_image_path";
	public static final String INTENT_ERROR_MESSAGE = "intent_error_message";
	
	private ImageChooserManager mChooserManager;
	private int mChooserType;
	private String mFilePath;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		showSelectDialog();
	}
	
	private void showSelectDialog() {
		final CharSequence[] methods = {getString(R.string.lab_choose_from_gallery), getString(R.string.lab_take_picture), getString(R.string.lab_cancel)};
		 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.lab_choose_option));
        builder.setItems(methods, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                case 0://Gallery
                	chooseGalleryImage();
                	break;
                case 1://Camera
                	captureImage();
                	break;
                case 2://cancel
                	dialog.dismiss();
                	finish();
                	break;
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
	}
	
	private void captureImage() {
		mChooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
		mChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_CAPTURE_PICTURE, getString(R.string.app_name).replaceAll(" ", ""), true);
		mChooserManager.setImageChooserListener(this);
		try {
			mFilePath = mChooserManager.choose();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chooseGalleryImage() {
		mChooserType = ChooserType.REQUEST_PICK_PICTURE;
		mChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_PICK_PICTURE, getString(R.string.app_name).replaceAll(" ", ""), true);
		mChooserManager.setImageChooserListener(this);
		try {
			mFilePath = mChooserManager.choose();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onImageChosen(ChosenImage image) {
		Intent data = new Intent();
		data.putExtra(INTENT_IMAGE_PATH, image.getFilePathOriginal());
		data.putExtra(INTENT_IMAGE_BANNER_PATH, image.getFileThumbnail());
		data.putExtra(INTENT_IMAGE_THUMBNAIL_PATH, image.getFileThumbnailSmall());
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public void onError(String reason) {
		Intent data = new Intent();
		data.putExtra(INTENT_ERROR_MESSAGE, reason);
		setResult(RESULT_CANCELED,data);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
			if (mChooserManager == null) {
				reinitializeImageChooser();
			}
			mChooserManager.submit(requestCode, data);
		} else {
			finish();
		}
	}
	
	// Should be called if for some reason the ImageChooserManager is null (Due
	// to destroying of activity for low memory situations)
	private void reinitializeImageChooser() {
		mChooserManager = new ImageChooserManager(this, mChooserType,
				"Babysidekickkk", true);
		mChooserManager.setImageChooserListener(this);
		mChooserManager.reinitialize(mFilePath);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("chooser_type", mChooserType);
		outState.putString("media_path", mFilePath);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("chooser_type")) {
				mChooserType = savedInstanceState.getInt("chooser_type");
			}

			if (savedInstanceState.containsKey("media_path")) {
				mFilePath = savedInstanceState.getString("media_path");
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
}
