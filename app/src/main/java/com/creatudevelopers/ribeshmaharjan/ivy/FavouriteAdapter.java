package com.creatudevelopers.ribeshmaharjan.ivy;

import android.annotation.SuppressLint;
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

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.creatudevelopers.ribeshmaharjan.ivy.model.Detail;
import com.creatudevelopers.ribeshmaharjan.ivy.model.DetailResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FavouriteResults;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.InfoViewHolder> {

    Context mcontext;
    List<FavouriteResults> mfavouriteResultsList;
    LatLng latLng=SchoollistAdapter.latLng;




    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;

        private InfoViewHolder(View itemView) {
            super(itemView);
            mSchoolImage=  itemView.findViewById(R.id.imageView_favourite1);
            mSchoolName=itemView.findViewById(R.id.school_name_favourtie);
            mRatingbar=itemView.findViewById(R.id.rating_bar_favourite);
            mStatus=itemView.findViewById(R.id.txtview_status_favourite);
            mDistance=itemView.findViewById(R.id.txtview_distance_favourite);


        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    FavouriteAdapter(Context context, List<FavouriteResults> favouriteResults)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);
        mfavouriteResultsList=favouriteResults;

    }

    @Override
    public FavouriteAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.favouriteschool_recyclerview_layout ,parent, false);
        return new FavouriteAdapter.InfoViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final FavouriteAdapter.InfoViewHolder holder, int position) {
        final Typeface typeface = ResourcesCompat.getFont(mcontext, R.font.montserrat_regular);
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<DetailResponse> call_detailing=apiInterface.getschooldetail(Integer.valueOf(mfavouriteResultsList.get(position).getSchoolId()));
        call_detailing.enqueue(new Callback<DetailResponse>() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DetailResponse> call, @NonNull Response<DetailResponse> response) {
                //assert response.body() != null;
                final Detail detail = response.body().getResults();
                holder.mSchoolName.setText(detail.getName());
                holder.mSchoolName.setTypeface(typeface);
                holder.mStatus.setText(detail.getOpeningtime() + " - "+ detail.getClosingtime());
                holder.mStatus.setTypeface(typeface);
                Double distance = (getDistanceBetween(latLng.latitude, latLng.longitude,
                        detail.getLatitude(),detail.getLongitude()))/1000;
                if(distance >= 0)
                {
                    holder.mDistance.setText(String.format("%.2f",distance)+" "+"Km from you");
                    holder.mDistance.setTypeface(typeface);
                }
               /* List<Slide> slideList2 = new ArrayList<>();
                slideList2.add((new Slide(0, detail.getImages().get(0), mcontext.getResources().getDimensionPixelSize(R.dimen.slider_image_corner))));
                holder.mSchoolImage.addSlides(slideList2);*/
                Picasso.get().load(detail.getImages().get(0)).into(holder.mSchoolImage);
                if(detail.getSchoolaverage()!=null) {
                    holder.mRatingbar.setRating(detail.getSchoolaverage());
                }
                else
                {
                    holder.mRatingbar.setRating(0);
                }
                holder.mSchoolName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                        intent2.putExtra("schoolID", detail.getId());

                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent2);
                    }
                });
                holder.mSchoolImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                        intent2.putExtra("schoolID", detail.getId());

                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent2);

                    }
                });
                holder.mDistance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                        intent2.putExtra("schoolID", detail.getId());

                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent2);

                    }
                });
                holder.mStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                        intent2.putExtra("schoolID", detail.getId());

                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent2);

                    }
                });
                holder.mRatingbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                        intent2.putExtra("schoolID", detail.getId());

                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent2);

                    }
                });

            }

            @Override
            public void onFailure(@NonNull Call<DetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(mcontext,t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        /*holder.mSchoolImage.setImageResource(mImageIds.get(position));*/
       // holder.mSchoolImage.setBackgroundResource();
        //holder.mSchoolName.setText(mfavouriteResultsList.get(position).);
        // holder.mRatingbar.setRating(2);
        //holder.mDistance.setText("0.4 KM");
       // holder.mStatus.setText("Open");





    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return mfavouriteResultsList.size();
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