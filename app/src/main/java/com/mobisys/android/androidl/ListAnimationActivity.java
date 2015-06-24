package com.mobisys.android.androidl;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mobisys.android.androidl.adapter.CustomRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAnimationActivity extends AppCompatActivity {
    private String[] mDataset = {"Facebook", "Google+", "Instagram", "Pininterest", "Linkedin", "Whatsapp"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_animation);

        initToolbar();
        initScreen();
    }

    private void initToolbar(){
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Collapsing Toolbar");
    }

    private void initScreen(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(mDataset);
        adapter.setOnItemClickListener(new CustomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View row) {
                showDetailActivity(position, row);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void showDetailActivity(int position,View row){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("name", mDataset[position]);

        List<Pair> participants = new ArrayList<>(2);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView textView = (TextView)row.findViewById(R.id.textView);
        Pair<View, String> pair = new Pair<View, String>(textView, getString(R.string.text_view_transition));
        participants.add(pair);
        pair = new Pair<View, String>(toolbar, getString(R.string.toolbar_transition));
        participants.add(pair);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, participants.toArray(new Pair[participants.size()]));
        final Bundle transitionBundle = activityOptions.toBundle();
        ActivityCompat.startActivity(this, intent, transitionBundle);//startActivity(intent, transitionBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
