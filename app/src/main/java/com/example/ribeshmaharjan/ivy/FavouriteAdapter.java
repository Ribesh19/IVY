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
import android.widget.Toast;

import com.example.ribeshmaharjan.ivy.model.Detail;
import com.example.ribeshmaharjan.ivy.model.DetailResponse;
import com.example.ribeshmaharjan.ivy.model.FavouriteResults;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.InfoViewHolder> {

    Context mcontext;
    List<FavouriteResults> mfavouriteResultsList;

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
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<DetailResponse> call_detailing=apiInterface.getschooldetail(Integer.valueOf(mfavouriteResultsList.get(position).getSchoolId()));
        call_detailing.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailResponse> call, @NonNull Response<DetailResponse> response) {
                assert response.body() != null;
                Detail detail = response.body().getResults();
                holder.mSchoolName.setText(detail.getName());

            }

            @Override
            public void onFailure(@NonNull Call<DetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(mcontext,t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        /*holder.mSchoolImage.setImageResource(mImageIds.get(position));*/
        holder.mSchoolImage.setBackgroundResource(R.drawable.mother_newborn);
        //holder.mSchoolName.setText(mfavouriteResultsList.get(position).);
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
        return mfavouriteResultsList.size();
    }



}
