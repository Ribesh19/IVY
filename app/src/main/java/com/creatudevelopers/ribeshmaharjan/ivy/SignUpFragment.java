package com.creatudevelopers.ribeshmaharjan.ivy;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.creatudevelopers.ribeshmaharjan.ivy.model.SignUpResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SignUpResult;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    Button msignup;
    TextInputLayout msignup_name,msignup_email,msignup_password,msignup_confirmpassword;
    String name,email,password,confirmpassword;
    SharedPreferences.Editor editor;




    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_sign_up, container, false);
        msignup=rootview.findViewById(R.id.btn_signup_register);
        msignup_name=rootview.findViewById(R.id.signup_name);
        msignup_email=rootview.findViewById(R.id.signup_email);
        msignup_password=rootview.findViewById(R.id.signup_password);
        msignup_confirmpassword=rootview.findViewById(R.id.signup_confirmpassword);
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent3=new Intent(getActivity(),MainLayoutActivity.class);
                startActivity(intent3);*/
                msignup.setEnabled(false);
                name = msignup_name.getEditText().getText().toString();
                email = msignup_email.getEditText().getText().toString();
                password = msignup_password.getEditText().getText().toString();
                confirmpassword = msignup_confirmpassword.getEditText().getText().toString();
                if (name.isEmpty()) {
                    msignup_name.setError(getResources().getString(R.string.error_string));
                }
                if (email.isEmpty()) {
                    msignup_email.setError(getResources().getString(R.string.error_string));
                }
                if (password.isEmpty()) {
                    msignup_password.setError(getResources().getString(R.string.error_string));
                }
                if (confirmpassword.isEmpty()) {
                    msignup_confirmpassword.setError(getResources().getString(R.string.error_string));
                }
                if(!password.equals(confirmpassword))
                {
                    msignup_password.setError(getResources().getString(R.string.Password_dontmatch));
                    msignup_confirmpassword.setError("");
                }
                if(!isEmailValid(email))
                {
                    msignup_email.setError(getResources().getString(R.string.invalidemail));
                }
                if (password.equals(confirmpassword)&& isEmailValid(email)) {


                    if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()) {
                        RegisterActivity.mprogressBar_login.setVisibility(View.VISIBLE);
                        Call<SignUpResponse> signUpResponseCall =apiInterface.signup(name,email,password);
                        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                                int statuscode=response.code();

                                if(statuscode==200)
                                {
                                    SignUpResult signUpResult=response.body().getResults();
                                    editor = getActivity().getSharedPreferences("mypref", MODE_PRIVATE).edit();
                                    editor.putInt("id",signUpResult.getId());
                                    editor.putString("name",signUpResult.getName());
                                    editor.putString("email",signUpResult.getEmail());
                                    editor.putString("image","");
                                    editor.putBoolean("Islogin", true);
                                    editor.putBoolean("FormLogin",true);
                                    editor.apply();

                                    Intent intent=new Intent(getContext(),MainLayoutActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_pleaselogin_layout, (ViewGroup) rootview.findViewById(R.id.toast_layout_card_login));
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    RegisterActivity.mprogressBar_login.setVisibility(View.GONE);
                                    msignup.setEnabled(true);


                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {

                            }
                        });
                    }
                }
            }
        });
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            msignup_name.setError(null);
                            msignup_email.setError(null);
                            msignup_password.setError(null);
                            msignup_confirmpassword.setError(null);
                            msignup.setEnabled(true);

                        }
                    }
                });
        return rootview;
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.\\+-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
