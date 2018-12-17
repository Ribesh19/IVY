package com.example.ribeshmaharjan.ivy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.InfoViewHolder> {

    Context mcontext;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        //RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;
        ImageButton mPrice;




        private InfoViewHolder(View itemView) {
            super(itemView);
            mSchoolImage=  itemView.findViewById(R.id.imageView_favourite);
            mSchoolName=itemView.findViewById(R.id.school_name_favourtie);
            //mRatingbar=itemView.findViewById(R.id.rating_list);
            mStatus=itemView.findViewById(R.id.txtview_status_favourite);
            mDistance=itemView.findViewById(R.id.txtview_distance_favourite);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    FavouriteAdapter(Context context)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public FavouriteAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.favouriteschool_recyclerview_layout ,parent, false);
        return new FavouriteAdapter.InfoViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(FavouriteAdapter.InfoViewHolder holder, int position) {



        /*holder.mSchoolImage.setImageResource(mImageIds.get(position));*/
        holder.mSchoolImage.setBackgroundResource(R.drawable.mother_newborn);
        holder.mSchoolName.setText(R.string.komal_day_care_service_and_pre_nursery);
        // holder.mRatingbar.setRating(2);
        holder.mDistance.setText("0.4 KM");
        holder.mStatus.setText("Open");




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
