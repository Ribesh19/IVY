package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Context;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.creatudevelopers.ribeshmaharjan.ivy.model.Featured;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.InfoViewHolder> {

    Context mcontext;
    List<Featured> mfeaturedList;
    LatLng latLng=SchoollistAdapter.latLng;

    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        RatingBar mrating_bar_favourite;
        TextView mStatus;
        TextView mDistance;
        ImageButton mPrice;







        private InfoViewHolder(View itemView) {
            super(itemView);

            mSchoolImage=  itemView.findViewById(R.id.imageView_favourite1);
            mSchoolName=itemView.findViewById(R.id.school_name_favourtie);
            mrating_bar_favourite=itemView.findViewById(R.id.rating_bar_favourite);
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
    public void onBindViewHolder(@NonNull RecommendationAdapter.InfoViewHolder holder, final int position) {

        Typeface typeface = ResourcesCompat.getFont(mcontext, R.font.montserrat_regular);

        Double distance = (getDistanceBetween(latLng.latitude, latLng.longitude,
                mfeaturedList.get(position).getLatitude(), mfeaturedList.get(position).getLongitude()))/1000;
        Picasso.get().load(mfeaturedList.get(position).getImages()).into(holder.mSchoolImage);
       holder.mSchoolName.setText(mfeaturedList.get(position).getName());
       holder.mSchoolName.setTypeface(typeface);
      // holder.mStatus.setText(mfeaturedList.get(position).get);
       holder.mStatus.setTypeface(typeface);
       holder.mDistance.setText(String.format("%.2f",distance)+" "+"Km from you");
       holder.mDistance.setTypeface(typeface);
      // holder.mrating_bar_favourite.setRating(mfeaturedList.get(position).);

        holder.mSchoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,DetailActivity.class);
                intent.putExtra("schoolID", mfeaturedList.get(position).getId());
                intent.putExtra("fromfavourtie","");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
        holder.mSchoolImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,DetailActivity.class);
                intent.putExtra("schoolID", mfeaturedList.get(position).getId());
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
        return mfeaturedList.size();
    }
    public static Double getDistanceBetween(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == 0 || lat2 == 0)
            return null;
        float[] result = new float[1];
        Location.distanceBetween(lat1, lon1,
                lat2, lon2, result);
        return (double) result[0];
    }

}
