package com.example.ribeshmaharjan.ivy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
    Button mmakefavourite;
    Button mseeallreview;
    CarouselView mcarouselView;
    ProgressBar mprogressbar;

    TextView mschoolname;
    TextView maddress;
    TextView maboutuscontent;
    TextView magegroup;

    Detail detail=null;

    FavouriteFragment favouriteFragment=new FavouriteFragment();
    int[] sampleImages = {R.drawable.preschool_img,R.drawable.school_img1,R.drawable.school_img2};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       Integer schoolid= getIntent().getIntExtra("schoolID",1);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_detail);
        Toast.makeText(DetailActivity.this,Integer.toString(schoolid),Toast.LENGTH_LONG).show();

        mschoolname=findViewById(R.id.school_name);
        maddress=findViewById(R.id.txt_address);
        maboutuscontent=findViewById(R.id.aboutus_content);
        magegroup=findViewById(R.id.txtview_agegroup);
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
                String address= detail.getAddress()+", "+detail.getCity()+", Bagmati - "+detail.getState()+", "+detail.getPostCode()+", "+detail.getCountry();
                maddress.setText(address);
                maboutuscontent.setText(detail.getDescription());
                magegroup.setText(detail.getAgeGroup());

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
        mmakefavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*Intent intent5=new Intent();
               intent5.setClass(DetailActivity.this,MainLayoutActivity.class);
                startActivity(intent5);*/
              }
        });

        mseeallreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(DetailActivity.this,AllReviewAcitivity.class);
                startActivity(intent6);
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
