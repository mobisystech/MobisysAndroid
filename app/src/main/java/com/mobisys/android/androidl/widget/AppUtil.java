package com.mobisys.android.androidl.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.mobisys.android.androidl.R;

public class AppUtil {

	public static void showErrorDialog(String msg, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.error);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage(msg);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public static String getRealPathFromURI(Uri contentURI, Context mContext) {
		Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) {
			return contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		}
	}


	public static String GetMimeType(Context context, Uri uriImage){
		String strMimeType = null;
		Cursor cursor = context.getContentResolver().query(uriImage,
				new String[] { MediaStore.MediaColumns.MIME_TYPE },
				null, null, null);

		if (cursor != null && cursor.moveToNext()){
			strMimeType = cursor.getString(0);
		}

		return strMimeType;
	}
}
