package com.mobisys.android.androidl.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Resource implements Parcelable{

	private String url;
	private String uri;
	private boolean isDirty;
	
	public Resource(){}

	public Resource(Parcel in) {
		readFromParcel(in);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	private void readFromParcel(Parcel in) {
		url=in.readString();
		uri = in.readString();
		isDirty = in.readInt()==1;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		dest.writeString(uri);
		dest.writeInt(isDirty?1:0);
	}

	public static final Creator<Resource> CREATOR = new Creator<Resource>() {
		
		public Resource createFromParcel(Parcel source) {
			return new Resource(source);
		}
		
		public Resource[] newArray(int size) {
			return new Resource[size];
		}
	};
}
