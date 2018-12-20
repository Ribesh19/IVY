package com.example.ribeshmaharjan.ivy;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ribeshmaharjan.ivy.model.Review;
import com.example.ribeshmaharjan.ivy.model.ReviewCollection;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.InfoViewHolder> {
    Context mcontext;
    List<ReviewCollection> mreviewCollectionList=null;

    class InfoViewHolder extends RecyclerView.ViewHolder {

        TextView musername;
        TextView mLastactive;
        RatingBar mReviewRatingbar;
        TextView mRatingValue;
        TextView mreview;

        private InfoViewHolder(View itemView) {
            super(itemView);
            musername=itemView.findViewById(R.id.username);
            mLastactive=itemView.findViewById(R.id.lastactive);
            mReviewRatingbar=itemView.findViewById(R.id.rating_review);
            mRatingValue=itemView.findViewById(R.id.ratingvalue);
            mreview=itemView.findViewById(R.id.Reviewbody);
        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    ReviewAdapter(Context context,List <ReviewCollection> reviewCollectionList)
    {
        mcontext=context;
        mreviewCollectionList=reviewCollectionList;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ReviewAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.reviewadapter_layout, parent, false);
        return new ReviewAdapter.InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.InfoViewHolder holder, int position) {
       /* String [] usernames={"Shyam Lohat","Sarala Massey","Kool Zero","Shyam Lohat"};
        String [] ratingdate={"1 day ago","August 7, 2018","August 7, 2018","August 8, 2018"};
        String [] ratingvalue={"3 of 5","4 of 5","5 of 5","3 of 5"};
        Integer [] rating={3,4,5,3};
        holder.musername.setText(usernames[position]);
        holder.mLastactive.setText(ratingdate[position]);
        holder.mReviewRatingbar.setRating(rating[position]);
        holder.mRatingValue.setText(ratingvalue[position]);*/
       holder.musername.setText(mreviewCollectionList.get(position).getUsername());
       holder.mLastactive.setText(mreviewCollectionList.get(position).getDate());
       holder.mReviewRatingbar.setRating(mreviewCollectionList.get(position).getUseraveragereview());
       holder.mRatingValue.setText(mreviewCollectionList.get(position).getUseraveragereview().toString() + " of 5");
       holder.mreview.setText(mreviewCollectionList.get(position).getMessage());
        }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return mreviewCollectionList.size();
    }


}
