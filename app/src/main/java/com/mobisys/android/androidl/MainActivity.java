package com.mobisys.android.androidl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by priyank on 6/18/15.
 */
public class MainActivity extends AppCompatActivity {

    private String[] menu = {"Drawer Navigation", "Floating Action Button (FAB)", "TabLayout", "TabLayout with ViewPager", "Collapsing Toolbar", "Collapsing Toolbar with Tab", "Collapsing Toolbar with Tab & ViewPager", "Collapsing Toolbar with Tab, ViewPager & FlexibleView", "Others","GridView with Animation","ListView with Animation","Http Request using Retrofit", "Image Chooser"};

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                    startActivity(intent);
                } else if (position == 1){
                    Intent intent = new Intent(MainActivity.this, FABActivity.class);
                    startActivity(intent);
                } else if (position == 2){
                    Intent intent = new Intent(MainActivity.this, TabActivity.class);
                    startActivity(intent);
                } else if (position == 3){
                    Intent intent = new Intent(MainActivity.this, TabWithViewPagerActivity.class);
                    startActivity(intent);
                } else if (position == 4){
                    Intent intent = new Intent(MainActivity.this, CollapsingToolbarActivity.class);
                    startActivity(intent);
                } else if (position == 5){
                    Intent intent = new Intent(MainActivity.this, CollapsingToolbarWithTabActivity.class);
                    startActivity(intent);
                } else if (position == 6){
                    Intent intent = new Intent(MainActivity.this, CollapsingToolbarWithTabAndViewPager.class);
                    startActivity(intent);
                } else if (position == 7){
                    Intent intent = new Intent(MainActivity.this, CollapsingToolbarWithFlexibleView.class);
                    startActivity(intent);
                } else if (position == 8){
                    Intent intent = new Intent(MainActivity.this, OtherComponentsActivity.class);
                    startActivity(intent);
                } else if (position == 9){
                    Intent intent = new Intent(MainActivity.this, GridAnimationActivity.class);
                    startActivity(intent);
                } else if (position == 10){
                    Intent intent = new Intent(MainActivity.this, ListAnimationActivity.class);
                    startActivity(intent);
                } else if (position == 11){
                    Intent intent = new Intent(MainActivity.this, HttpRequestSampleActivity.class);
                    startActivity(intent);
                }
                else if (position == 12){
                    Intent intent = new Intent(MainActivity.this, ChooseImagesActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
