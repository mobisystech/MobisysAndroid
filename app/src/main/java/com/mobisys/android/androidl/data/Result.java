package com.mobisys.android.androidl.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

public class Result implements Parcelable {

	@JsonProperty("id")
	private long id;
	@JsonProperty("poster_path")
	private String poster_path;
	@JsonProperty("title")
	private String title;
	@JsonProperty("release_date")
	private String release_date;

	public Result() {}

	public Result(Parcel in) {
		readFromParcel(in);
	}

	public Result(long id, String poster_path, String title, String release_date) {
		this.id = id;
		this.poster_path = poster_path;
		this.title = title;
		this.release_date = release_date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	private void readFromParcel(Parcel in) {
		id = in.readLong();
		poster_path = in.readString();
		title = in.readString();
		release_date = in.readString();
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(poster_path);
		dest.writeString(title);
		dest.writeString(release_date);
	}

	public static final Creator<Result> CREATOR = new Creator<Result>() {
		
		@Override
		public Result createFromParcel(Parcel source) {
			return new Result(source);
		}
		
		@Override
		public Result[] newArray(int size) {
			return new Result[size];
		}
		
	};

	@Override
	public int describeContents() {
		return 0;
	}
}
