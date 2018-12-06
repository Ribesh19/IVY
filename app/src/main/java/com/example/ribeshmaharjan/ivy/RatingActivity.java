package com.example.ribeshmaharjan.ivy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    RatingBar mratingBarInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_rating);

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
