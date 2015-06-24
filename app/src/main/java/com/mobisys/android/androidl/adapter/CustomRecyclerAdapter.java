package com.mobisys.android.androidl.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobisys.android.androidl.R;

/**
 * Created by priyank on 6/20/15.
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private OnItemClickListener mListener;

    public static interface OnItemClickListener {
        public void onItemClick(int position, View row);
    }

    public CustomRecyclerAdapter(String[] dataset){
        this.mDataset = dataset;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler, viewGroup, false);

        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final OnItemClickListener mListener;

        public ViewHolder(final View itemView, OnItemClickListener itemListener) {
            super(itemView);
            this.mListener = itemListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null) mListener.onItemClick(getAdapterPosition(), itemView);
                }
            });
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
