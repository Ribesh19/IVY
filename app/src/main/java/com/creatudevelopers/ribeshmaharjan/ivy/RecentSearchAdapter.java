package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.creatudevelopers.ribeshmaharjan.ivy.model.School;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.InfoViewHolder> {
    Context mcontext;
    private List<School> mschools;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView mrecentsearch;




        private InfoViewHolder(View itemView) {
            super(itemView);
            mrecentsearch=itemView.findViewById(R.id.search_result_list);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    RecentSearchAdapter(Context context,List<School> schools)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);
        mschools=schools;

    }

    @Override
    public RecentSearchAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recentsearch_layout, parent, false);
        return new RecentSearchAdapter.InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentSearchAdapter.InfoViewHolder holder, final int position) {

       /* String [] schools={"Kid Zone","Safe Heaven","Orange Day"};
        holder.mrecentsearch.setText(schools[position]);*/
       holder.mrecentsearch.setText(mschools.get(position).getName());
        holder.mrecentsearch.setTextColor(Color.argb(190, 111,53,148));

        holder.mrecentsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                intent.putExtra("schoolID", mschools.get(position).getId());
                intent.putExtra("fromfavourtie","");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });


    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return mschools.size();
    }
}
