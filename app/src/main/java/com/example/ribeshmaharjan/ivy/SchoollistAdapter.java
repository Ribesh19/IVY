package com.example.ribeshmaharjan.ivy;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;



public class SchoollistAdapter extends  RecyclerView.Adapter<SchoollistAdapter.InfoViewHolder>{


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
           mSchoolImage=  itemView.findViewById(R.id.profileImageView);
           mSchoolName=itemView.findViewById(R.id.nameTxt);
           //mRatingbar=itemView.findViewById(R.id.rating_list);
           mStatus=itemView.findViewById(R.id.txtview_status);
           mDistance=itemView.findViewById(R.id.txtview_distance);
           mPrice=itemView.findViewById(R.id.btn_viewprice);


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
       // holder.mRatingbar.setRating(2);
        holder.mDistance.setText("0.4 KM");
        holder.mStatus.setText("Open");
        holder.mPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(),FeeStructureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

        holder.mSchoolImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(),DetailActivity.class);
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
        return 3;
    }

}
