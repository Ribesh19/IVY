package com.creatudevelopers.ribeshmaharjan.ivy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.creatudevelopers.ribeshmaharjan.ivy.model.Review;
import com.creatudevelopers.ribeshmaharjan.ivy.model.ReviewResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllReviewAcitivity extends AppCompatActivity {

    ImageView mbackbtn;
    Review    review=null;
    RatingBar mrating_security_allreview;
    RatingBar mrating_qualified_staff_allreview;
    RatingBar mrating_infrastructure_allreview;
    RatingBar mrating_curriculum_allreview;

    ProgressBar mprogressBar_reviews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_review_acitivity);
        final Integer schoolid= getIntent().getIntExtra("schoolID",1);
        mbackbtn=findViewById(R.id.back_btn_review);
        mrating_security_allreview=findViewById(R.id.rating_security_allreview);
        mrating_qualified_staff_allreview=findViewById(R.id.rating_qualified_staff_allreview);
        mrating_infrastructure_allreview=findViewById(R.id.rating_infrastructure_allreview);
        mrating_curriculum_allreview=findViewById(R.id.rating_curriculum_allreview);
        mprogressBar_reviews=findViewById(R.id.progressBar_reviews);
        mprogressBar_reviews.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView =findViewById(R.id.allreview_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<ReviewResponse> call_allreviews=apiInterface.getallreviews(schoolid);
        call_allreviews.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                assert response.body() != null;
                review=response.body().getResults();
                mprogressBar_reviews.setVisibility(View.GONE);
                if (review.getSecurityaverage()!=null) {
                    mrating_security_allreview.setRating(review.getSecurityaverage());
                    mrating_qualified_staff_allreview.setRating(review.getStaffaverage());
                    mrating_infrastructure_allreview.setRating(review.getInfrastructureaverage());
                    mrating_curriculum_allreview.setRating(review.getCurriculumaverage());
                }
                else
                {
                    mrating_security_allreview.setRating(0);
                    mrating_qualified_staff_allreview.setRating(0);
                    mrating_infrastructure_allreview.setRating(0);
                    mrating_curriculum_allreview.setRating(0);
                }
                final ReviewAdapter recyclerview_adapter1 = new ReviewAdapter(Objects.requireNonNull(getApplicationContext()),review.getReviews());
                recyclerView.setAdapter(recyclerview_adapter1);
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                Toast.makeText(AllReviewAcitivity.this,t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                Log.e("Error",t.getCause().getMessage());


            }
        });


        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllReviewAcitivity.super.onBackPressed();
            }
        });
    }
}
