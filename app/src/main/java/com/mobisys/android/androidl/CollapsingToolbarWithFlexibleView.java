package com.mobisys.android.androidl;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mobisys.android.androidl.adapter.CustomRecyclerAdapter;

public class CollapsingToolbarWithFlexibleView extends AppCompatActivity {
    private static final int DATASET_COUNT = 60;
    private String[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_with_flexible_view);

        initDatasetCount();
        initToolbar();
        initScreen();
    }

    private void initScreen(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(mDataset);
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar(){
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar)).setTitle("Collapsing Toolbar with Tab, ViewPager & FlexibleView");
        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar)).setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar)).setExpandedTitleColor(getResources().getColor(android.R.color.white));
        //toolBar.setTitle("Collapsing Toolbar with Tab, ViewPager & FlexibleView");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Collapsing Toolbar with Tab, ViewPager & FlexibleView");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_collapsing_toolbar_with_flexible_view, menu);
        return true;
    }

    private void initDatasetCount(){
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is element #" + i;
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
