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

import com.mobisys.android.androidl.Rest.Request;
import com.mobisys.android.androidl.Rest.RestCallback;
import com.mobisys.android.androidl.Rest.RestClient;
import com.mobisys.android.androidl.data.Movie;
import com.mobisys.android.androidl.data.Result;
import com.mobisys.android.androidl.widget.MImageLoader;
import com.mobisys.android.androidl.widget.ProgressDialog;

import java.util.ArrayList;

import retrofit.client.Response;

/**
 * Created by vikas on 6/22/15.
 */
public class JsonParsingActivity extends AppCompatActivity {

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
        mPg= ProgressDialog.show(JsonParsingActivity.this, getString(R.string.loading));
        RestClient.getMovieApi(JsonParsingActivity.this).getMovieList(Request.API_KEY, new RestCallback<Movie>() {
            @Override
            public void success(Movie movieObj, Response response) {
                if (mPg != null && mPg.isShowing()) mPg.dismiss();
                initList(movieObj);
            }

            @Override
            public void failure(String restErrors, boolean networkError) {
                if (mPg != null && mPg.isShowing()) mPg.dismiss();
            }
        });
    }

    private void initList(Movie movieObj) {
        ListView list=(ListView)findViewById(R.id.list);
        MovieListAdapter adapter=new MovieListAdapter(JsonParsingActivity.this,0,movieObj.getResults());
        list.setAdapter(adapter);
    }

    private class MovieListAdapter extends ArrayAdapter<Result> {

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

        public MovieListAdapter(Context context, int textViewResourceId,ArrayList<Result> objects) {
            super(context, textViewResourceId, objects);
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if(row == null){
                row = mInflater.inflate(R.layout.row_movie_list_item, null);
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            else{
                holder = (ViewHolder)row.getTag();
            }

            if(getItem(position).getPoster_path()!=null && getItem(position).getPoster_path().length()>0)
                MImageLoader.displayImage(JsonParsingActivity.this, Request.IMAGE_PATH+"/"+getItem(position).getPoster_path(), holder.poster,R.drawable.user_stub);
            else
                MImageLoader.displayImage(JsonParsingActivity.this, null, holder.poster,R.drawable.user_stub);

            holder.title.setText(getItem(position).getTitle()+"");
            holder.release_date.setText(getItem(position).getRelease_date()+"");

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
