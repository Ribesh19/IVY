package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DetailActivity extends AppCompatActivity {
    ImageButton mbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mbackbtn=findViewById(R.id.back_btn);
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,ListingHelpActivity.class);
                startActivity(intent);
            }
        });


    }
}
