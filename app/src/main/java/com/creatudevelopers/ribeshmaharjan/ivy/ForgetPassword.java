package com.creatudevelopers.ribeshmaharjan.ivy;


import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.creatudevelopers.ribeshmaharjan.ivy.model.ForgetPasswordResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPassword extends AppCompatActivity {

    TextInputLayout mforgetpassword_email;
    Button mforgetpassword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mforgetpassword_email=findViewById(R.id.forgetpassword_email);
        mforgetpassword_btn=findViewById(R.id.forgetpassword_btn);

        mforgetpassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mforgetpassword_btn.setEnabled(false);
                mforgetpassword_btn.setText("Submitting...");
                String email=mforgetpassword_email.getEditText().getText().toString();
                if(email.isEmpty()) {

                    mforgetpassword_email.setError(getResources().getString(R.string.error_string));
                }
                    final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ForgetPasswordResponse> forgetpasswordresponse=apiInterface.forgetpassword(email);
                   forgetpasswordresponse.enqueue(new Callback<ForgetPasswordResponse>() {
                       @Override
                       public void onResponse(@NonNull Call<ForgetPasswordResponse> call, @NonNull Response<ForgetPasswordResponse> response) {
                           mforgetpassword_btn.setText(R.string.send_password_reset_link);
                           LayoutInflater inflater = getLayoutInflater();
                           View layout = inflater.inflate(R.layout.toast_saved_layout, (ViewGroup) findViewById(R.id.toast_layout_card_save));
                           Toast toast = new Toast(getApplicationContext());
                           toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                           toast.setDuration(Toast.LENGTH_SHORT);
                           toast.setView(layout);
                           toast.show();
                           finish();

                       }

                       @Override
                       public void onFailure(@NonNull Call<ForgetPasswordResponse> call, @NonNull Throwable t) {


                       }
                   });



            }
        });
        KeyboardVisibilityEvent.setEventListener(
                ForgetPassword.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            mforgetpassword_btn.setEnabled(true);
                            mforgetpassword_email.setError(null);
                            mforgetpassword_btn.setText(R.string.send_password_reset_link);

                        }
                    }
                });


    }

}
