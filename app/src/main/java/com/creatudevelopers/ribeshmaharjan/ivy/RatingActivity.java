package com.creatudevelopers.ribeshmaharjan.ivy;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.transition.Fade;
import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.creatudevelopers.ribeshmaharjan.ivy.model.RatingResponse;

import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingActivity extends AppCompatActivity {

    RatingBar mratingBarInput;
    RatingBar mQualified_staff_rating_input;
    RatingBar minfrastructure_rating_input;
    RatingBar mcurriculum_rating_input;
    TextInputLayout madditional_info_rating;

    TextView textView_schoolname;
    ImageView mbackbtn;
    Button mbtn_submit_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_rating);
        mbackbtn=findViewById(R.id.back_btn_rating);
        final Integer schoolid= getIntent().getIntExtra("schoolID",1);

        mbtn_submit_review=findViewById(R.id.btn_submit_review);
        textView_schoolname=findViewById(R.id.school_name_rating);
        textView_schoolname.setTextColor(Color.argb(100, 111,53,148));
        mratingBarInput=findViewById(R.id.rating_security_input);
        mQualified_staff_rating_input=findViewById(R.id.Qualified_staff_rating_input);
        minfrastructure_rating_input=findViewById(R.id.infrastructure_rating_input);
        mcurriculum_rating_input=findViewById(R.id.curriculum_rating_input);
        madditional_info_rating=findViewById(R.id.additional_info_rating);


        mratingBarInput.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mratingBarInput.setRating(rating);
                //Toast.makeText(RatingActivity.this,String.valueOf(rating),Toast.LENGTH_LONG).show();
            }
        });
        mQualified_staff_rating_input.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mQualified_staff_rating_input.setRating(rating);
            }
        });
        minfrastructure_rating_input.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                minfrastructure_rating_input.setRating(rating);
            }
        });
        mcurriculum_rating_input.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mcurriculum_rating_input.setRating(rating);
            }
        });


        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingActivity.super.onBackPressed();

            }
        });
        mbtn_submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtn_submit_review.setEnabled(false);
                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                Integer id  = prefs.getInt("id", 0);

                boolean IsLogin= prefs.getBoolean("Islogin",false);
                String addinfo= madditional_info_rating.getEditText().getText().toString();
               // Toast.makeText(RatingActivity.this,name + email, Toast.LENGTH_LONG).show();
                if(addinfo.isEmpty())
                {
                    madditional_info_rating.setError(getResources().getString(R.string.error_string));
                }
               /* if(IsLogin)
                {*/
                    if(!addinfo.isEmpty()) {
                        mbtn_submit_review.setText("SUBMITING ...");
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        final Call<RatingResponse> ratingResponseCall = apiInterface.postreview(schoolid, id
                                , mratingBarInput.getRating(), mQualified_staff_rating_input.getRating(), minfrastructure_rating_input.getRating(),
                                mcurriculum_rating_input.getRating(), addinfo);
                        ratingResponseCall.enqueue(new Callback<RatingResponse>() {
                            @Override
                            public void onResponse( Call<RatingResponse> call, Response<RatingResponse> response) {
                                assert response.body() != null;
                               /* LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_saved_layout, (ViewGroup) findViewById(R.id.toast_layout_card_save));
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();*/
                                Intent intent = new Intent(RatingActivity.this, ThankyouActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure (Call<RatingResponse> call,  Throwable t) {
                               // Toast.makeText(RatingActivity.this, t.getCause().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                    {

                    }

            }
        });

        KeyboardVisibilityEvent.setEventListener(
                RatingActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            madditional_info_rating.setError(null);
                            mbtn_submit_review.setEnabled(true);
                            mbtn_submit_review.setText(R.string.submit_review);
                        }
                    }
                });
    }
}
