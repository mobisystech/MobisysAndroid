package com.mobisys.android.androidl;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobisys.android.androidl.data.Movie;
import com.mobisys.android.androidl.data.MovieWrapper;
import com.mobisys.android.androidl.rest.Request;
import com.mobisys.android.androidl.rest.RestCallback;
import com.mobisys.android.androidl.rest.RestClient;
import com.mobisys.android.androidl.widget.MImageLoader;
import com.mobisys.android.androidl.widget.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit.client.Response;

/**
 * Created by vikas on 6/22/15.
 */
public class HttpRequestSampleActivity extends AppCompatActivity {

    private Dialog mPg;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_json_parsing);
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initScreen();
    }

    private void initScreen() {
        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMovieList();
            }
        });
    }

    private void fetchMovieList() {
        mPg= ProgressDialog.show(HttpRequestSampleActivity.this, getString(R.string.loading));
        RestClient.getMovieApi(HttpRequestSampleActivity.this).getMovieList(Request.API_KEY, new RestCallback<MovieWrapper>() {
            @Override
            public void success(MovieWrapper movieWrapper, Response response) {
                if (mPg != null && mPg.isShowing()) mPg.dismiss();
                initList(movieWrapper);
            }

            @Override
            public void failure(String restErrors, boolean networkError) {
                if (mPg != null && mPg.isShowing()) mPg.dismiss();
            }
        });
    }

    private void initList(MovieWrapper movieObj) {
        ListView list=(ListView)findViewById(R.id.list);
        MovieListAdapter adapter=new MovieListAdapter(HttpRequestSampleActivity.this,0,movieObj.getMovies());
        list.setAdapter(adapter);
    }

    private class MovieListAdapter extends ArrayAdapter<Movie> {
        private final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

        private LayoutInflater mInflater;
        private class ViewHolder {
            public TextView title, release_date;
            public ImageView poster;

            public ViewHolder(View row){
                title = (TextView)row.findViewById(R.id.title);
                release_date = (TextView)row.findViewById(R.id.release_date);
                poster = (ImageView)row.findViewById(R.id.poster);
            }
        }

        public MovieListAdapter(Context context, int textViewResourceId,ArrayList<Movie> objects) {
            super(context, textViewResourceId, objects);
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if(row == null){
                row = mInflater.inflate(R.layout.row_movie, null);
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            else{
                holder = (ViewHolder)row.getTag();
            }

            MImageLoader.displayImage(HttpRequestSampleActivity.this, Request.IMAGE_PATH+"/"+getItem(position).getPoster_path(), holder.poster,R.drawable.user_stub);
            holder.title.setText(getItem(position).getTitle()+"");
            String date = SDF.format(getItem(position).getRelease_date());
            holder.release_date.setText(date);

            return row;
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
