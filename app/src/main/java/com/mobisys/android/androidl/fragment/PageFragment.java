package com.mobisys.android.androidl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobisys.android.androidl.R;
import com.mobisys.android.androidl.adapter.CustomRecyclerAdapter;

/**
 * Created by priyank on 6/20/15.
 */
public class PageFragment extends Fragment {
    public static final String POSITION = "position";
    private static final int DATASET_COUNT = 60;
    private int mPosition;
    private View mRootView;
    private String[] mDataset;

    public static PageFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_page, null);
        initDatasetCount();
        initScreen();
        return mRootView;
    }

    private void initScreen(){
        RecyclerView recyclerView = (RecyclerView)mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(mDataset);
        recyclerView.setAdapter(adapter);
    }

    private void initDatasetCount(){
        mDataset = new String[DATASET_COUNT];
        int k = mPosition*60;
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is element #" + (i+k);
        }
    }
}
