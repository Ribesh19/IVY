package com.creatudevelopers.ribeshmaharjan.ivy;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.creatudevelopers.ribeshmaharjan.ivy.model.FeeStructure;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FeeStructureResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeStructureActivity extends AppCompatActivity {

    TextView schoolname;
    Spinner spinner;
    ImageButton mbackbtn;
    Button mfeestructure_button;

    TextInputLayout mname_fee,memail_fee,mphonenumber_fee,maddress_fee,madditional_info_fee;
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
        madditional_info_fee=findViewById(R.id.additional_info_fee);
        schoolname.setTextColor(Color.argb(100, 111,53,148));

        spinner=findViewById(R.id.age_fee);
        ArrayList<AgeGroup> list=new ArrayList<>();
        list.add(new AgeGroup(" Age of Child"));
        list.add(new AgeGroup("1.5 - 2.5 Years Old"));
        list.add(new AgeGroup("2.5 - 3.5 Years Old"));
        list.add(new AgeGroup("3.5 - 4.5 Years Old"));
        final AgeGroupSpinnerAdapter adapter=new AgeGroupSpinnerAdapter(FeeStructureActivity.this,R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email","");
        final String phonenumber_from_prefs=prefs.getString("phone","");
        memail_fee.getEditText().setText(email);
        mname_fee.getEditText().setText(name);
        if(!phonenumber_from_prefs.isEmpty())
        {
            mphonenumber_fee.getEditText().setText(phonenumber_from_prefs);
        }

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
                mfeestructure_button.setEnabled(false);
                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                final Integer id = prefs.getInt("id", 0);
                final boolean Islogin = prefs.getBoolean("Islogin", false);
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        ageofchild = "Age of Child";
                        break;
                    case 1:
                        ageofchild = "1.5 - 2.5 Years Old";
                        break;
                    case 2:
                        ageofchild = "2.5 - 3.5 Years Old";
                        break;
                    case 3:
                        ageofchild = "3.5 - 4.5 Years Old";
                        break;
                    default:
                        ageofchild = "Age of Child";

                }
                // Toast.makeText(FeeStructureActivity.this,ageofchild + schoolid,Toast.LENGTH_LONG).show();
                String emailID = memail_fee.getEditText().getText().toString();
                String name = mname_fee.getEditText().getText().toString();
                String phonnnumber = mphonenumber_fee.getEditText().getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putString("phone",phonnnumber);
                editor.apply();
                String address = maddress_fee.getEditText().getText().toString();
                String additional_info=madditional_info_fee.getEditText().getText().toString();

                if(emailID.isEmpty())
                {
                    memail_fee.setError(getResources().getString(R.string.error_string));
                    mfeestructure_button.setText(R.string.request_fee_structure);
                }
                if (name.isEmpty())
                {
                    mname_fee.setError(getResources().getString(R.string.error_string));
                    mfeestructure_button.setText(R.string.request_fee_structure);
                }
                if(phonnnumber.isEmpty())
                {
                    mphonenumber_fee.setError(getResources().getString(R.string.error_string));
                    mfeestructure_button.setText(R.string.request_fee_structure);
                }
                if(address.isEmpty())
                {
                    maddress_fee.setError(getResources().getString(R.string.error_string));
                    mfeestructure_button.setText(R.string.request_fee_structure);
                }
                if(!isEmailValid(emailID))
                {
                    memail_fee.setError(getResources().getString(R.string.invalidemail));
                    mfeestructure_button.setText(R.string.request_fee_structure);
                }
                if(additional_info.isEmpty())
                {
                    additional_info=null;
                }
                /*if (Islogin) {*/
                if(!emailID.isEmpty() && !name.isEmpty() && isEmailValid(emailID)) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    if(id!=0) {
                        mfeestructure_button.setText("SUBMITING...");
                        Call<FeeStructureResponse> loginResponseCall = apiInterface.postfeerequest(schoolid, id, name, emailID, phonnnumber, address, ageofchild,additional_info);
                        loginResponseCall.enqueue(new Callback<FeeStructureResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<FeeStructureResponse> call, @NonNull Response<FeeStructureResponse> response) {
                                //feeStructure = response.body().getRequest();
                                int statuscode = response.code();

                                // Toast.makeText(FeeStructureActivity.this, response.body().getResults(), Toast.LENGTH_LONG).show();

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_saved_layout, (ViewGroup) findViewById(R.id.toast_layout_card_save));
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                //Toast.makeText(RatingActivity.this,response.body().getResults(),Toast.LENGTH_LONG).show();
                                FeeStructureActivity.super.onBackPressed();
                            }

                            @Override
                            public void onFailure(@NonNull Call<FeeStructureResponse> call, @NonNull Throwable t) {
                                //Toast.makeText(FeeStructureActivity.this, t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                                mfeestructure_button.setEnabled(true);
                                //call.cancel();

                            }
                        });
                    }
                }

            }
        });
        KeyboardVisibilityEvent.setEventListener(
                FeeStructureActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            memail_fee.setError(null);
                            mname_fee.setError(null);
                            mphonenumber_fee.setError(null);
                            maddress_fee.setError(null);
                            mfeestructure_button.setEnabled(true);
                            mfeestructure_button.setText(R.string.request_fee_structure);
                        }
                    }
                });
        }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
