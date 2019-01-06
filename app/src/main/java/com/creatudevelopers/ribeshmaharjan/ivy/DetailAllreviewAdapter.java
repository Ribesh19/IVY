package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.creatudevelopers.ribeshmaharjan.ivy.model.ReviewCollection;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class DetailAllreviewAdapter  extends RecyclerView.Adapter<DetailAllreviewAdapter.InfoViewHolder> {
    Context mcontext;
    List<ReviewCollection> mreviewCollectionList;

    class InfoViewHolder extends RecyclerView.ViewHolder {

        TextView musername;
        TextView mLastactive;
        RatingBar mReviewRatingbar;
        TextView mRatingValue;
        TextView mreview;
        CircleImageView mimageView4;

        private InfoViewHolder(View itemView) {
            super(itemView);
            mimageView4=itemView.findViewById(R.id.imageView4);
            musername=itemView.findViewById(R.id.username);
            mLastactive=itemView.findViewById(R.id.lastactive);
            mReviewRatingbar=itemView.findViewById(R.id.rating_review);
            mRatingValue=itemView.findViewById(R.id.ratingvalue);
            mreview=itemView.findViewById(R.id.Reviewbody);
        }
    }

    private final LayoutInflater mInflater;
    // Cached copy of words

    DetailAllreviewAdapter(Context context,List <ReviewCollection> reviewCollectionList)
    {
        mcontext=context;
        mreviewCollectionList=reviewCollectionList;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public DetailAllreviewAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.reviewadapter_layout, parent, false);
        return new DetailAllreviewAdapter.InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAllreviewAdapter.InfoViewHolder holder, int position) {

            Typeface typeface = ResourcesCompat.getFont(mcontext, R.font.montserrat_regular);

            if(!mreviewCollectionList.get(position).getUsername().isEmpty()) {
                holder.mimageView4.setImageResource(R.drawable.imagefiller);
                holder.musername.setText(mreviewCollectionList.get(position).getUsername());
                holder.musername.setTypeface(typeface);
                holder.mLastactive.setText(mreviewCollectionList.get(position).getCreatedAt());
                holder.mLastactive.setTypeface(typeface);
                holder.mReviewRatingbar.setRating(mreviewCollectionList.get(position).getUseraveragereview());
                holder.mRatingValue.setText(mreviewCollectionList.get(position).getUseraveragereview().toString() + " of 5");
                holder.mRatingValue.setTypeface(typeface);
                holder.mreview.setText(mreviewCollectionList.get(position).getMessage());
                holder.mreview.setTypeface(typeface);
            }
            else
            {
                holder.mimageView4.setVisibility(GONE);
                holder.musername.setVisibility(GONE);

                holder.mLastactive.setVisibility(GONE);

                holder.mReviewRatingbar.setVisibility(GONE);
                holder.mRatingValue.setVisibility(GONE);

                holder.mreview.setVisibility(GONE);

            }

    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        /*if (logs != null)
            return logs.size();
        else return 0;*/
        return mreviewCollectionList.size()/2;
    }




}
