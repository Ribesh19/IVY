package com.example.ribeshmaharjan.ivy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ribeshmaharjan.ivy.model.Detail;
import com.example.ribeshmaharjan.ivy.model.DetailResponse;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    ImageButton mbackbtn;
    Button mrequestfeestructure;
    Button mreview;
    Button mbookappointment;
    ToggleButton mmakefavourite;
    Button mseeallreview;
    CarouselView mcarouselView;
    ProgressBar mprogressbar;

    TextView mschoolname;
    TextView maddress;
    TextView maboutuscontent;
    TextView magegroup;
    RatingBar mratingbar2;
    RatingBar ratingBar_security;
    RatingBar ratingBar_staff;
    RatingBar ratingBar_infrastructure;
    RatingBar ratingBar_curriculum;
    TextView musername_review_detail_review;
    TextView mratingvalue_review_detail_review;
    TextView mReviewbody_review_detail;
    RatingBar mrating_review_review_detail;

    Detail detail=null;

    FavouriteFragment favouriteFragment=new FavouriteFragment();
    int[] sampleImages = {R.drawable.preschool_img,R.drawable.school_img1,R.drawable.school_img2};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       final Integer schoolid= getIntent().getIntExtra("schoolID",1);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_detail);
        Toast.makeText(DetailActivity.this,Integer.toString(schoolid),Toast.LENGTH_LONG).show();

        mschoolname=findViewById(R.id.school_name);
        maddress=findViewById(R.id.txt_address);
        maboutuscontent=findViewById(R.id.aboutus_content);
        magegroup=findViewById(R.id.txtview_agegroup);
        mratingbar2=findViewById(R.id.rating2);
        ratingBar_security=findViewById(R.id.rating_security);
        ratingBar_curriculum=findViewById(R.id.rating_curriculum);
        ratingBar_infrastructure=findViewById(R.id.rating_infrastructure);
        ratingBar_staff=findViewById(R.id.rating_qualified_staff);
        musername_review_detail_review=findViewById(R.id.username_review_detail);
        mratingvalue_review_detail_review=findViewById(R.id.ratingvalue_review_detail);
        mReviewbody_review_detail=findViewById(R.id.Reviewbody_review_detail);
        mrating_review_review_detail=findViewById(R.id.rating_review_review_detail);

        mprogressbar=findViewById(R.id.progressBar_detail);
        mbackbtn=findViewById(R.id.back_btn);
        mrequestfeestructure=findViewById(R.id.btn_requestfeestructure_detail);
        mbookappointment=findViewById(R.id.btn_bookanappointment_detail);
        mmakefavourite=findViewById(R.id.btn_favourite);
        mreview=findViewById(R.id.btn_review);
        mseeallreview=findViewById(R.id.seeallreview);
        mcarouselView=findViewById(R.id.imageView2);
        mcarouselView.setPageCount(sampleImages.length);
        mcarouselView.setImageListener(imageListener);
        mprogressbar.setVisibility(View.VISIBLE);

       /* mmakefavourite.setChecked(false);
        mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle));*/

        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<DetailResponse> call_detailing=apiInterface.getschooldetail(schoolid);
        call_detailing.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailResponse> call, @NonNull Response<DetailResponse> response) {
                mprogressbar.setVisibility(View.GONE);
               Toast.makeText(DetailActivity.this, "Detail Activity Success",Toast.LENGTH_LONG).show();
                int statuscode = response.code();
                assert response.body() != null;
                detail=response.body().getResults();
                //Toast.makeText(DetailActivity.this,detail.getName() ,Toast.LENGTH_LONG).show();
                mschoolname.setText(detail.getName());
                String address= detail.getAddress()+", "+detail.getCity()+", State - "+detail.getState()+", "+detail.getPostCode()+", "+detail.getCountry();
                maddress.setText(address);
                int fav_id = detail.getFavorite().get(0).getIsFavorite();
                if(fav_id==0){

                    mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle));
                }
                else
                {
                    mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle_red));
                }
                maboutuscontent.setText(detail.getDescription());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    maboutuscontent.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                magegroup.setText(detail.getAgeGroup());
                mratingbar2.setRating(detail.getSchoolaverage());
                ratingBar_security.setRating(detail.getSecurityaverage());
                ratingBar_staff.setRating(detail.getStaffaverage());
                ratingBar_curriculum.setRating(detail.getCurriculumaverage());
                ratingBar_infrastructure.setRating(detail.getInfrastructureaverage());
                    musername_review_detail_review.setText(detail.getLatestReview().getUsername());
                    mReviewbody_review_detail.setText(detail.getLatestReview().getMessage());
                    mrating_review_review_detail.setRating(detail.getLatestReview().getUseraveragereview());
                    mratingvalue_review_detail_review.setText(detail.getLatestReview().getUseraveragereview().toString() + " of 5");

                }

            @Override
            public void onFailure(@NonNull Call<DetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(DetailActivity.this,MainLayoutActivity.class);
                startActivity(intent);*/
               DetailActivity.super.onBackPressed();
            }
        });
        mrequestfeestructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(DetailActivity.this,FeeStructureActivity.class);
                startActivity(intent2);
            }
        });
        mreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(DetailActivity.this,RatingActivity.class);
               startActivity(intent3,ActivityOptions.makeSceneTransitionAnimation(DetailActivity.this).toBundle());
                //startActivity(intent3);
            }
        });
        mbookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(DetailActivity.this,AppointmentActivity.class);
                startActivity(intent4);
            }
        });
        mseeallreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(DetailActivity.this,AllReviewAcitivity.class);
                intent6.putExtra("schoolID", schoolid);
                startActivity(intent6);
            }
        });
        mmakefavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle_red));
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_favourite_layout,
                            (ViewGroup) findViewById(R.id.toast_layout_card));
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else
                {
                    mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle));
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_remove_favourite_layout,
                            (ViewGroup) findViewById(R.id.toast_layout_card_remove));
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
        }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


}
