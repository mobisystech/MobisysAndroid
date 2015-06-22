package com.mobisys.android.androidl;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by priyank on 6/18/15.
 */
public class TabWithViewPagerActivity extends AppCompatActivity {
    private int[] mIcons = {R.drawable.ic_facebook, R.drawable.ic_twitter, R.drawable.ic_instagram};
    private String[] mTitles = {"Facebook", "Twitter", "Instagram"};

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.activity_tab);
        initActionbar();
        initMainScreen(true);
    }

    private void initActionbar(){
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initMainScreen(boolean icon){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (icon){
            for (int i=0;i<pagerAdapter.getCount();i++){
                tabLayout.getTabAt(i).setCustomView(pagerAdapter.getTextView(i));
            }
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

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageFragment.newInstance(position);
                case 1:
                default:
                    return PageFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        public TextView getTextView(int position){
            TextView textView = new TextView(TabWithViewPagerActivity.this);
            textView.setText(mTitles[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, mIcons[position], 0, 0);
            return textView;
        }

    }

    public static class PageFragment extends Fragment {
        public static final String ARG_PAGE = "ARG_PAGE";

        private int mPage;

        public static PageFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            PageFragment fragment = new PageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View view = inflater.inflate(R.layout.fragment_page, container, false);
            TextView textView = new TextView(getActivity());
            textView.setText("Fragment #" + mPage);
            return textView;
        }
    }
}
