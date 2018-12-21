package com.example.ribeshmaharjan.ivy;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.ribeshmaharjan.ivy.model.LoginRequest;
import com.example.ribeshmaharjan.ivy.model.LoginResponse;
import com.example.ribeshmaharjan.ivy.model.LoginResults;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;


import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mlogin;
    TextInputLayout mlogin_email;
    TextInputLayout mlogin_password;

    LoginResults loginResults;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_login, container, false);

        mlogin=rootview.findViewById(R.id.btn_login_register);
        mlogin_email=rootview.findViewById(R.id.login_email);
        mlogin_password=rootview.findViewById(R.id.login_password);


        /*ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> loginResponseCall=apiInterface.getlogindetail();*/
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailID = mlogin_email.getEditText().getText().toString();
                final String password =mlogin_password.getEditText().getText().toString();
                if(emailID.isEmpty()) {
                    mlogin_email.setError(getResources().getString(R.string.error_string));
                }
                if(password.isEmpty())
                {
                    mlogin_password.setError(getResources().getString(R.string.error_string));
                }
                //Toast.makeText(getContext(),emailID,Toast.LENGTH_LONG).show();
                if(!emailID.isEmpty() && !password.isEmpty()) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<LoginResponse> loginResponseCall=apiInterface.getlogindetail(emailID,password);
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            assert response.body() != null;
                            Toast.makeText(getContext(),"Login Response",Toast.LENGTH_LONG).show();
                            int status = response.code();
                            if(status==200)
                            {
                                Toast.makeText(getContext(),"Login Valid",Toast.LENGTH_LONG).show();
                                loginResults=response.body().getResults();
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("mypref", MODE_PRIVATE).edit();
                                editor.putInt("id",loginResults.getId());
                                editor.putString("name",loginResults.getName());
                                editor.putString("email",loginResults.getEmail());
                                editor.apply();
                                Objects.requireNonNull(LoginFragment.super.getActivity()).onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Login Invalid",Toast.LENGTH_LONG).show();
                                mlogin_email.setError(getResources().getString(R.string.up_wrong));
                                mlogin_password.setError("");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                            Toast.makeText(getContext(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();


                        }
                    });

                }

            }
        });
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            mlogin_email.setError(null);
                            mlogin_password.setError(null);
                        }else{
                            mlogin_email.setError(null);
                            mlogin_password.setError(null);
                        }
                    }
                });
        return rootview;
    }

}
