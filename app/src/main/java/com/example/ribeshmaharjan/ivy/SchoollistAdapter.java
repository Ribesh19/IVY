package com.example.ribeshmaharjan.ivy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;



import com.example.ribeshmaharjan.ivy.model.School;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.maps.model.LatLng;



import java.util.ArrayList;
import java.util.List;


import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;



public class SchoollistAdapter extends RecyclerView.Adapter<SchoollistAdapter.InfoViewHolder> implements LocationListener {


    Context mcontext;

    private List<School> mschools;
    List<String> imagelist = null;
    static  LatLng latLng;
    LocationManager locationManager;


    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView mSchoolImage;
        TextView mSchoolName;
        RatingBar mRatingbar;
        TextView mStatus;
        TextView mDistance;
        ImageButton mPrice;
        Slider slider;

        private InfoViewHolder(View itemView) {
            super(itemView);
            // mSchoolImage=  itemView.findViewById(R.id.profileImageView);
            slider = itemView.findViewById(R.id.profileImageView);
            mSchoolName = itemView.findViewById(R.id.nameTxt);
            mRatingbar = itemView.findViewById(R.id.rating_list);
            mStatus = itemView.findViewById(R.id.txtview_status);
            mDistance = itemView.findViewById(R.id.txtview_distance);
            mPrice = itemView.findViewById(R.id.btn_viewprice);


        }
    }


    private final LayoutInflater mInflater;
    SchoollistAdapter(Context context, List<School> schools) {
        mcontext = context;
        mInflater = LayoutInflater.from(context);
        mschools = schools;

    }


    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.school_info_layout, parent, false);

        return new InfoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final InfoViewHolder holder, final int position) {


        locationManager = (LocationManager) mcontext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
           /* public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
            {

            }*/
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);


        Typeface typeface = ResourcesCompat.getFont(mcontext, R.font.montserrat_regular);
        /*Glide.with(mcontext)

                //.load("https://i.imgur.com/PpFGm1o.png")
                .load(imagelist.get(position))
                .apply(new RequestOptions().override(630 ,586))
                .into(holder.mSchoolImage);*/


        if (mschools.get(position).getImages().isEmpty()) {
            List<Slide> slideList2 = new ArrayList<>();

            slideList2.add((new Slide(0, "https://i.imgur.com/PpFGm1o.png", mcontext.getResources().getDimensionPixelSize(R.dimen.slider_image_corner))));

            holder.slider.addSlides(slideList2);
        } else {
            List<Slide> slideList1 = new ArrayList<>();
            for (int i = 0; i < mschools.get(position).getImages().size(); i++) {
                slideList1.add((new Slide(i, mschools.get(position).getImages().get(i), mcontext.getResources().getDimensionPixelSize(R.dimen.slider_image_corner))));
            }
            holder.slider.addSlides(slideList1);
        }
       /* List<Slide> slideList2 = new ArrayList<>();

        slideList2.add((new Slide(0,"https://i.imgur.com/PpFGm1o.png", mcontext.getResources().getDimensionPixelSize(R.dimen.slider_image_corner))));

        holder.slider.addSlides(slideList2);*/
        LocationRequest mLocationRequest = new LocationRequest();

        if (mschools.get(position).getSchoolaverage() != null) {
            holder.mRatingbar.setRating(mschools.get(position).getSchoolaverage());
        } else {
            holder.mRatingbar.setRating(0);
        }
        holder.mSchoolName.setText(mschools.get(position).getName());
        Double distance = (getDistanceBetween(latLng.latitude, latLng.longitude,
                mschools.get(position).getLatitude(), mschools.get(position).getLongitude()))/1000;
        if(distance<0==false)
        {
            holder.mDistance.setText(String.format("%.2f",distance)+" "+"Km from you");
        }

        //holder.mDistance.setText(getDistanceBetween(37.422,-122.084, mschools.get(position).getLatitude(), mschools.get(position).getLongitude()).toString());
       // holder.mDistance.setText("0.4 Km from you");
        holder.mDistance.setTypeface(typeface);
        holder.mStatus.setText("Open");
        holder.mDistance.setTypeface(typeface);

        // Toast.makeText(mcontext,imagelist.get(position),Toast.LENGTH_LONG).show();
        //holder.mSchoolImage.setImageResource(mImageIds.get(position));
        holder.mPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext.getApplicationContext(), FeeStructureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
       /* holder.mSchoolImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext.getApplicationContext(),DetailActivity.class);
                intent.putExtra("schoolID", mschools.get(position).getId());
                //Toast.makeText(mcontext,Integer.toString(mschools.get(position).getId()),Toast.LENGTH_LONG).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

            }
        });*/
        holder.slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
                Intent intent = new Intent(mcontext.getApplicationContext(), DetailActivity.class);
                intent.putExtra("schoolID", mschools.get(position).getId());
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
    public static Double getDistanceBetween(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == 0 || lat2 == 0)
            return null;
        float[] result = new float[1];
        Location.distanceBetween(lat1, lon1,
                lat2, lon2, result);
        return (double) result[0];
    }




}
