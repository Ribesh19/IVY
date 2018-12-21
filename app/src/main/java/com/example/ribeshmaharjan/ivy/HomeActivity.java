package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    //Button mbtnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.home_activity_layout);
       //mbtnsignup=findViewById(R.id.btn_signUP_homepage);
       /*mbtnsignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(HomeActivity.this,RegisterActivity.class);
               startActivity(intent);
           }
       });*/

        Intent intent=new Intent(HomeActivity.this,MainLayoutActivity.class);
        startActivity(intent);
    }
}
