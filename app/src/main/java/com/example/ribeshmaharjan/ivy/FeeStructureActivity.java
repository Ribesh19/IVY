package com.example.ribeshmaharjan.ivy;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class FeeStructureActivity extends AppCompatActivity {

    TextView schoolname;
    TextView mcheckboxtext;
    CheckBox mtransportationcheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_structure);
        schoolname=findViewById(R.id.school_name_fee);
        mcheckboxtext=findViewById(R.id.transportations_required_textview);
        mtransportationcheckbox=findViewById(R.id.checkbox_trasportation_fee);
        schoolname.setTextColor(Color.argb(100, 111,53,148));
        mcheckboxtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mtransportationcheckbox.isChecked())
               {
                   mtransportationcheckbox.setChecked(false);
               }
               else
               {
                   mtransportationcheckbox.setChecked(true);
               }
            }
        });



    }
}
