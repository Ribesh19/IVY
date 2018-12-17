package com.example.ribeshmaharjan.ivy;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.InfoViewHolder> {
    Context mcontext;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView mrecentsearch;




        private InfoViewHolder(View itemView) {
            super(itemView);
            mrecentsearch=itemView.findViewById(R.id.search_result_list);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    RecentSearchAdapter(Context context)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecentSearchAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recentsearch_layout, parent, false);
        return new RecentSearchAdapter.InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentSearchAdapter.InfoViewHolder holder, int position) {

        String [] schools={"Kid Zone","Safe Heaven","Orange Day"};
        holder.mrecentsearch.setText(schools[position]);
        holder.mrecentsearch.setTextColor(Color.argb(100, 111,53,148));


    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return 3;
    }
}
