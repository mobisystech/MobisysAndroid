package com.mobisys.android.androidl.Rest;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import android.content.Context;

import com.mobisys.android.androidl.widget.Preferences;
import com.mobisys.android.androidl.Rest.api.MovieApi;
import com.squareup.okhttp.OkHttpClient;

public class RestClient {

    private static final String HEADER_AUTH_TOKEN = "X-AUTH-TOKEN";
	private static MovieApi mMovieApi;

    private static String PROD = "https://api.themoviedb.org/3";
	private static String STAGE = "https://api.themoviedb.org/3";

	private static boolean isUsingV4API = true;

	private RestClient() {
	}

	public static MovieApi getMovieApi(Context context) {
		if(mMovieApi ==null){
			setupRestClient(context);
		}
		
		return mMovieApi;
	}

	private static void setupRestClient(final Context context) {
		OkHttpClient okClient = new OkHttpClient();
		
		RestAdapter.Builder builder = new RestAdapter.Builder()
			.setEndpoint(productionOrStageUrl())
			.setConverter(new JacksonConverter())
			.setClient(new OkClient(okClient))
			.setLogLevel(RestAdapter.LogLevel.FULL);

		RestAdapter restAdapter = builder.build();
		mMovieApi = restAdapter.create(MovieApi.class);
	}
	
	private static String productionOrStageUrl(){
		if(Preferences.IS_STAGING) return STAGE;
		else return PROD;
	}

	public static boolean isUsingV4API(){
		return isUsingV4API;
	}
}
