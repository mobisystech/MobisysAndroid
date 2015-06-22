package com.mobisys.android.androidl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by priyank on 6/18/15.
 */
public class TabActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.activity_tab);
        initActionbar();
        initMainScreen(false);
    }

    private void initActionbar(){
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initMainScreen(boolean icon){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        findViewById(R.id.pager).setVisibility(View.GONE);

        tabLayout.removeAllTabs();
        if (icon){
            tabLayout.addTab(tabLayout.newTab().setText("Facebook").setIcon(R.drawable.ic_facebook));
            tabLayout.addTab(tabLayout.newTab().setText("Twitter").setIcon(R.drawable.ic_twitter));
            tabLayout.addTab(tabLayout.newTab().setText("Instagram").setIcon(R.drawable.ic_instagram));
        } else {
            tabLayout.addTab(tabLayout.newTab().setText("Facebook"));
            tabLayout.addTab(tabLayout.newTab().setText("Twitter"));
            tabLayout.addTab(tabLayout.newTab().setText("Instagram"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            setResult(RESULT_OK);
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_with_icon){
            initMainScreen(true);
        } else if (item.getItemId() == R.id.action_without_icon){
            initMainScreen(false);
        }

        return super.onOptionsItemSelected(item);
    }
}
