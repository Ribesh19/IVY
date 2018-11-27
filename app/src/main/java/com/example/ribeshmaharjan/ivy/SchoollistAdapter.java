package com.example.ribeshmaharjan.ivy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SchoollistAdapter extends  RecyclerView.Adapter<SchoollistAdapter.InfoViewHolder>{


    Context mcontext;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
       RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;
        TextView mPrice;




        private InfoViewHolder(View itemView) {
            super(itemView);
           mSchoolImage=  itemView.findViewById(R.id.profileImageView);
           mSchoolName=itemView.findViewById(R.id.nameTxt);
           mRatingbar=itemView.findViewById(R.id.rating_list);
           mStatus=itemView.findViewById(R.id.txtview_status);
           mDistance=itemView.findViewById(R.id.txtview_distance);
           mPrice=itemView.findViewById(R.id.textviewprice);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    SchoollistAdapter(Context context)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.school_info_layout, parent, false);
        return new InfoViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {

        /*holder.mSchoolImage.setImageResource(mImageIds.get(position));*/
        holder.mSchoolImage.setBackgroundResource(R.drawable.preschool_img);
        holder.mSchoolName.setText(R.string.ivy_komal_pre_school);
        holder.mRatingbar.setRating(4);
        holder.mDistance.setText("0.4 KM");
        holder.mPrice.setText("$ 200");
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
