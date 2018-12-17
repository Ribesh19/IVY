package com.example.ribeshmaharjan.ivy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
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

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;



public class DetailActivity extends AppCompatActivity {
    ImageButton mbackbtn;
    Button mrequestfeestructure;
    Button mreview;
    Button mbookappointment;
    Button mmakefavourite;
    Button mseeallreview;
    CarouselView mcarouselView;

    FavouriteFragment favouriteFragment=new FavouriteFragment();
    int[] sampleImages = {R.drawable.preschool_img,R.drawable.school_img1,R.drawable.school_img2};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_detail);
        mbackbtn=findViewById(R.id.back_btn);
        mrequestfeestructure=findViewById(R.id.btn_requestfeestructure_detail);
        mbookappointment=findViewById(R.id.btn_bookanappointment_detail);
        mmakefavourite=findViewById(R.id.btn_favourite);
        mreview=findViewById(R.id.btn_review);
        mseeallreview=findViewById(R.id.seeallreview);
        mcarouselView=findViewById(R.id.imageView2);
        mcarouselView.setPageCount(sampleImages.length);
        mcarouselView.setImageListener(imageListener);


        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,MainLayoutActivity.class);
                startActivity(intent);
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
