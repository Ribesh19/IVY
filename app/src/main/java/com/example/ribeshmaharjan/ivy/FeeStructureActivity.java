package com.example.ribeshmaharjan.ivy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FeeStructureActivity extends AppCompatActivity {

    TextView schoolname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_structure);
        schoolname=findViewById(R.id.school_name_fee);
        schoolname.setTextColor(Color.argb(100, 111,53,148));
    }
}
