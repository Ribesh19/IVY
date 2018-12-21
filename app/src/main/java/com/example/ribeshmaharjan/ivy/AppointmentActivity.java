package com.example.ribeshmaharjan.ivy;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity {

    ImageView mbackbtn;
    Button mbtn_bookanappointment;
    EditText mdateappointment;


    TextInputLayout mname_appointment,memail_appointment,mphonenumber_appointment,medit_text_Edittxt_date_appointment;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Integer schoolid= getIntent().getIntExtra("schoolID",1);
        setContentView(R.layout.activity_appointment);
        mdateappointment=findViewById(R.id.Edittxt_date_appointment);
        mbackbtn=findViewById(R.id.back_btn_appointment);

        mname_appointment=findViewById(R.id.name_appointment);
        memail_appointment=findViewById(R.id.email_appointment);
        mphonenumber_appointment=findViewById(R.id.phonenumber_appointment);
        medit_text_Edittxt_date_appointment=findViewById(R.id.edit_text_Edittxt_date_appointment);
        mbtn_bookanappointment=findViewById(R.id.btn_bookanappointment);



        final DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };

        mdateappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AppointmentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentActivity.super.onBackPressed();
            }
        });
        mbtn_bookanappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                Integer id  = prefs.getInt("name", 1);
                String emailID = memail_appointment.getEditText().getText().toString();
                String name= mname_appointment.getEditText().getText().toString();
                String phonnnumber= mphonenumber_appointment.getEditText().getText().toString();
                String date=  mdateappointment.getText().toString();
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Void> loginResponseCall=apiInterface.postappointment(schoolid,id,name,emailID,phonnnumber,null,null,date);
                loginResponseCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AppointmentActivity.this,"Appointment Booked",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AppointmentActivity.this," Error",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mdateappointment.setText(sdf.format(myCalendar.getTime()));


    }


}
