package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ribeshmaharjan.ivy.model.FeeStructure;
import com.example.ribeshmaharjan.ivy.model.FeeStructureResponse;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeStructureActivity extends AppCompatActivity {

    TextView schoolname;
    Spinner spinner;
    ImageButton mbackbtn;
    Button mfeestructure_button;

    TextInputLayout mname_fee,memail_fee,mphonenumber_fee,maddress_fee;
    String ageofchild;

    FeeStructure feeStructure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Integer schoolid= getIntent().getIntExtra("schoolID",1);
        setContentView(R.layout.activity_fee_structure);
        schoolname=findViewById(R.id.school_name_fee);
        mbackbtn=findViewById(R.id.back_btn_fee);
        mname_fee=findViewById(R.id.name_fee);
        memail_fee=findViewById(R.id.email_fee);
        mphonenumber_fee=findViewById(R.id.phonenumber_fee);
        maddress_fee=findViewById(R.id.address_fee);
        mfeestructure_button=findViewById(R.id.feestructure_button);




        schoolname.setTextColor(Color.argb(100, 111,53,148));

        spinner=findViewById(R.id.age_fee);
        ArrayList<AgeGroup> list=new ArrayList<>();
        list.add(new AgeGroup(" Age of Child"));
        list.add(new AgeGroup("1.5 - 2.5 Years Old"));
        list.add(new AgeGroup("2.5 - 3.5 Years Old"));
        list.add(new AgeGroup("3.5 - 4.5 Years Old"));
        final AgeGroupSpinnerAdapter adapter=new AgeGroupSpinnerAdapter(FeeStructureActivity.this,R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(FeeStructureActivity.this,DetailActivity.class);
                startActivity(intent);*/
                FeeStructureActivity.super.onBackPressed();
            }
        });
        mfeestructure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                Integer id  = prefs.getInt("name", 1);
               switch (spinner.getSelectedItemPosition())
               {
                   case 0:
                       ageofchild="Age of Child";
                       break;
                   case 1:
                       ageofchild="1.5 - 2.5 Years Old";
                       break;
                   case 2:
                       ageofchild="2.5 - 3.5 Years Old";
                       break;
                   case 3:
                       ageofchild="3.5 - 4.5 Years Old";
                       break;
                   default:
                       ageofchild="Age of Child";

               }
               // Toast.makeText(FeeStructureActivity.this,ageofchild + schoolid,Toast.LENGTH_LONG).show();

                 String emailID = memail_fee.getEditText().getText().toString();
                 String name= mname_fee.getEditText().getText().toString();
                String phonnnumber= mphonenumber_fee.getEditText().getText().toString();
               String address=  maddress_fee.getEditText().getText().toString();

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<FeeStructureResponse> loginResponseCall=apiInterface.postfeerequest(schoolid,id,name,emailID,phonnnumber,address,ageofchild);
                loginResponseCall.enqueue(new Callback<FeeStructureResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<FeeStructureResponse> call, @NonNull Response<FeeStructureResponse> response) {
                            feeStructure=response.body().getRequest();
                            Toast.makeText(FeeStructureActivity.this,response.body().getResults(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeeStructureResponse> call, @NonNull Throwable t) {

                    }
                });

            }
        });



    }
}
