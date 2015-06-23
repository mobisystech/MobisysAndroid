package com.mobisys.android.androidl.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

public class Movie implements Parcelable {

	@JsonProperty("page")
	private int page;
	@JsonProperty("results")
	private ArrayList<Result> results;
	@JsonProperty("total_pages")
	private int total_pages;

	public Movie() {}

	public Movie(Parcel in) {
		readFromParcel(in);
	}

	public Movie(int page, ArrayList<Result> results, int total_pages) {
		this.page = page;
		this.results = results;
		this.total_pages = total_pages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	private void readFromParcel(Parcel in) {
		page = in.readInt();
		results=new ArrayList<Result>();
		in.readList(results, Result.class.getClassLoader());
		total_pages = in.readInt();
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(page);
		dest.writeList(results);
		dest.writeLong(total_pages);
	}

	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}
		
		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
		
	};

	@Override
	public int describeContents() {
		return 0;
	}
}
