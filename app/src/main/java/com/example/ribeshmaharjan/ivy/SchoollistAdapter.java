package com.example.ribeshmaharjan.ivy;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ribeshmaharjan.ivy.model.School;
import com.example.ribeshmaharjan.ivy.model.city;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
public class SchoollistAdapter extends  RecyclerView.Adapter<SchoollistAdapter.InfoViewHolder>{


    Context mcontext;
    private List<Integer> mImageIds;
    private List<School> mschools;
    List<String> imagelist =null;
    /* ArrayList<JSONObject> mjsonObjects=new ArrayList<>();*/
    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;
        ImageButton mPrice;

        private InfoViewHolder(View itemView) {
            super(itemView);
            mSchoolImage=  itemView.findViewById(R.id.profileImageView);
           mSchoolName=itemView.findViewById(R.id.nameTxt);
           mRatingbar=itemView.findViewById(R.id.rating_list);
           mStatus=itemView.findViewById(R.id.txtview_status);
           mDistance=itemView.findViewById(R.id.txtview_distance);
           mPrice=itemView.findViewById(R.id.btn_viewprice);


        }
    }


    private final LayoutInflater mInflater;
    // Cached copy of words

/*    SchoollistAdapter(Context context,List<Integer> imageIds)
    {
        mcontext=context;
        mInflater = LayoutInflater.from(context);
        mImageIds = imageIds;


    }*/
   SchoollistAdapter(Context context,List<School> schools)
   {
       mcontext=context;
       mInflater = LayoutInflater.from(context);
       mschools=schools;

       }


    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.school_info_layout, parent, false);

        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final InfoViewHolder holder, final int position) {
        int size= mschools.get(position).getImages().size();
        //Toast.makeText(mcontext,Integer.toString(size),Toast.LENGTH_LONG).show();
       // imagelist.addAll(mschools.get(position).getImages());
       // imagelist.get(position);
        imagelist=mschools.get(position).getImages();
        Typeface typeface=ResourcesCompat.getFont(mcontext,R.font.montserrat_regular);
        Glide.with(mcontext)

                .load("https://i.imgur.com/PpFGm1o.png")
                //.load(imagelist.get(position))
                .apply(new RequestOptions().fitCenter())
                .into(holder.mSchoolImage);

        holder.mRatingbar.setRating(mschools.get(position).getSchoolaverage());
        holder.mSchoolName.setText(mschools.get(position).getName());
        holder.mDistance.setText("0.4 km from you");
        holder.mDistance.setTypeface(typeface);
        holder.mStatus.setText("Open");
        holder.mDistance.setTypeface(typeface);

        Toast.makeText(mcontext,imagelist.get(position),Toast.LENGTH_LONG).show();
        //holder.mSchoolImage.setImageResource(mImageIds.get(position));
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
                intent.putExtra("schoolID", mschools.get(position).getId());
                //Toast.makeText(mcontext,Integer.toString(mschools.get(position).getId()),Toast.LENGTH_LONG).show();
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
