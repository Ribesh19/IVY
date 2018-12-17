package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

public class FeeStructureActivity extends AppCompatActivity {

    TextView schoolname;
    Spinner spinner;
    ImageButton mbackbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_structure);
        schoolname=findViewById(R.id.school_name_fee);
        mbackbtn=findViewById(R.id.back_btn_fee);
        schoolname.setTextColor(Color.argb(100, 111,53,148));

        spinner=findViewById(R.id.age_fee);
        ArrayList<AgeGroup> list=new ArrayList<>();
        list.add(new AgeGroup("2 - 3 months"));
        list.add(new AgeGroup("3 - 4 months"));
        list.add(new AgeGroup("4 - 6 months"));
        list.add(new AgeGroup("6 - 8 months"));
        list.add(new AgeGroup("8 - 10 months"));
        list.add(new AgeGroup("10 - 12 months"));
        AgeGroupSpinnerAdapter adapter=new AgeGroupSpinnerAdapter(FeeStructureActivity.this,R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FeeStructureActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });



    }
}
