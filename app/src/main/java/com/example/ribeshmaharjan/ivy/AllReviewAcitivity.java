package com.example.ribeshmaharjan.ivy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class AllReviewAcitivity extends AppCompatActivity {

    ImageView mbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_review_acitivity);
        mbackbtn=findViewById(R.id.back_btn_review);
        RecyclerView recyclerView =findViewById(R.id.allreview_recyclerview);
        final ReviewAdapter recyclerview_adapter1 = new ReviewAdapter(Objects.requireNonNull(getApplicationContext()));
        recyclerView.setAdapter(recyclerview_adapter1);
       /* recyclerView.setNestedScrollingEnabled(false);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllReviewAcitivity.super.onBackPressed();
            }
        });
    }
}
