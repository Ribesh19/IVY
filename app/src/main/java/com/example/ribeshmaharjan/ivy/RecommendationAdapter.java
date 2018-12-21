package com.example.ribeshmaharjan.ivy;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ribeshmaharjan.ivy.model.Featured;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.InfoViewHolder> {

    Context mcontext;
    List<Featured> mfeaturedList;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        //RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;
        ImageButton mPrice;






        private InfoViewHolder(View itemView) {
            super(itemView);

            mSchoolImage=  itemView.findViewById(R.id.imageView_favourite1);
            mSchoolName=itemView.findViewById(R.id.school_name_favourtie);
            //mRatingbar=itemView.findViewById(R.id.rating_list);
            mStatus=itemView.findViewById(R.id.txtview_status_favourite);
            mDistance=itemView.findViewById(R.id.txtview_distance_favourite);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    RecommendationAdapter(Context context,List<Featured> featuredList)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);
        mfeaturedList=featuredList;

    }

    @NonNull
    @Override
    public RecommendationAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.favouriteschool_recyclerview_layout ,parent, false);
        return new RecommendationAdapter.InfoViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.InfoViewHolder holder, int position) {



        /*holder.mSchoolImage.setImageResource(mImageIds.get(position));*/
       /* holder.mSchoolImage.setBackgroundResource(R.drawable.mother_newborn);
        holder.mSchoolName.setText(R.string.komal_day_care_service_and_pre_nursery);
        // holder.mRatingbar.setRating(2);
        holder.mDistance.setText("0.4 KM");
        holder.mStatus.setText("Open");*/

       holder.mSchoolName.setText(mfeaturedList.get(position).getName());




    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return mfeaturedList.size();
    }

}
