package com.example.ribeshmaharjan.ivy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.transition.Fade;


import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;


public class RatingActivity extends AppCompatActivity {

    RatingBar mratingBarInput;
    TextView textView_schoolname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_rating);

        textView_schoolname=findViewById(R.id.school_name_rating);
        textView_schoolname.setTextColor(Color.argb(100, 111,53,148));
        mratingBarInput=findViewById(R.id.rating_security_input);
        mratingBarInput.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mratingBarInput.setRating(rating);
                //Toast.makeText(RatingActivity.this,String.valueOf(rating),Toast.LENGTH_LONG).show();
            }
        });
    }
}
