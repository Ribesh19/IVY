package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        Intent intent=new Intent(HomeActivity.this,FeeStructureActivity.class);
        startActivity(intent);
    }
}
