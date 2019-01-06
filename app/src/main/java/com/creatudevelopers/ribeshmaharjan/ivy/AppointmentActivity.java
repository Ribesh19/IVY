package com.creatudevelopers.ribeshmaharjan.ivy;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity {

    ImageView mbackbtn;
    Button mbtn_bookanappointment;
    EditText mdateappointment;

    TextInputLayout mname_appointment,memail_appointment,mphonenumber_appointment,medit_text_Edittxt_date_appointment,
            madditional_info_appointment;

    Calendar myCalendar = Calendar.getInstance();
    Date currentime2=Calendar.getInstance().getTime();

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
        madditional_info_appointment=findViewById(R.id.additional_info_appointment);


        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email","");
        final String phonenumber_from_prefs=prefs.getString("phone","");
        memail_appointment.getEditText().setText(email);
        mname_appointment.getEditText().setText(name);
        if(!phonenumber_from_prefs.isEmpty())
        {
            mphonenumber_appointment.getEditText().setText(phonenumber_from_prefs);
        }


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
                mbtn_bookanappointment.setEnabled(false);

                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                Integer id = prefs.getInt("id", 0);
                String emailID = memail_appointment.getEditText().getText().toString();
                String name = mname_appointment.getEditText().getText().toString();
                String phonnnumber = mphonenumber_appointment.getEditText().getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putString("phone",phonnnumber);
                editor.apply();
                String date = mdateappointment.getText().toString();
                String additional_info=madditional_info_appointment.getEditText().getText().toString();
                if (emailID.isEmpty()) {
                    memail_appointment.setError(getResources().getString(R.string.error_string));
                }
                if (name.isEmpty()) {
                    mname_appointment.setError(getResources().getString(R.string.error_string));
                }
                if (phonnnumber.isEmpty()) {
                    mphonenumber_appointment.setError(getResources().getString(R.string.error_string));
                }
                if (date.isEmpty()) {
                    mdateappointment.setError(getResources().getString(R.string.error_string));
                }
                if(!isEmailValid(emailID))
                {
                    memail_appointment.setError(getResources().getString(R.string.invalidemail));
                }
                if(myCalendar.getTime().before(currentime2))
                {
                    mdateappointment.setError(getResources().getString(R.string.Futuredate));
                }
                if(additional_info.isEmpty())
                {
                    additional_info=null;
                }

                if(id!=0) {
                    if (!emailID.isEmpty() && !name.isEmpty() && !phonnnumber.isEmpty()
                            && !date.isEmpty() && isEmailValid(emailID) && !myCalendar.getTime().before(currentime2)) {
                        mbtn_bookanappointment.setText("SUBMITING ...");
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Void> loginResponseCall = apiInterface.postappointment(schoolid, id, name, emailID,
                                phonnnumber, null, null, date, additional_info);
                        loginResponseCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                //Toast.makeText(AppointmentActivity.this,"Appointment Booked",Toast.LENGTH_LONG).show();
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_saved_layout, (ViewGroup) findViewById(R.id.toast_layout_card_save));
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                //Toast.makeText(RatingActivity.this,response.body().getResults(),Toast.LENGTH_LONG).show();
                                AppointmentActivity.super.onBackPressed();
                                mbtn_bookanappointment.setText(R.string.book_an_appointment);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AppointmentActivity.this, " Error", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            }
        });

        KeyboardVisibilityEvent.setEventListener(
            AppointmentActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            memail_appointment.setError(null);
                            mname_appointment.setError(null);
                            mphonenumber_appointment.setError(null);
                            mdateappointment.setError(null);
                            mbtn_bookanappointment.setEnabled(true);
                            mbtn_bookanappointment.setText(R.string.book_an_appointment);
                        }
                    }
                });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            mdateappointment.setText(sdf.format(myCalendar.getTime()));
    }

    public static boolean isEmailValid(String email) {
       // String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression="^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }




}
