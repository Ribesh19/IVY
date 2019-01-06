package com.creatudevelopers.ribeshmaharjan.ivy;

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
import android.widget.TextView;
import android.widget.Toast;
import com.creatudevelopers.ribeshmaharjan.ivy.model.LoginResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.LoginResults;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mlogin;
    TextInputLayout mlogin_email;
    TextInputLayout mlogin_password;
    TextView mforgetpassword;

    LoginResults loginResults;
    SharedPreferences.Editor editor;



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
        mforgetpassword=rootview.findViewById(R.id.forgetpassword);

        mforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ForgetPassword.class);
                startActivity(intent);
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlogin.setEnabled(false);

                final String emailID = mlogin_email.getEditText().getText().toString();
                final String password =mlogin_password.getEditText().getText().toString();
                if(emailID.isEmpty()) {
                    mlogin_email.setError(getResources().getString(R.string.error_string));
                }
                if(password.isEmpty())
                {
                    mlogin_password.setError(getResources().getString(R.string.error_string));
                }
                if(!isEmailValid(emailID))
                {
                    mlogin_email.setError(getResources().getString(R.string.invalidemail));
                }
                if(!emailID.isEmpty() && !password.isEmpty() && isEmailValid(emailID)) {
                    RegisterActivity.mprogressBar_login.setVisibility(View.VISIBLE);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<LoginResponse> loginResponseCall=apiInterface.getlogindetail(emailID,password);
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                            assert response.body() != null;
                            //Toast.makeText(getContext(),"Login Response",Toast.LENGTH_LONG).show();
                            int status = response.code();
                            if(status==200)
                            {
                                RegisterActivity.mprogressBar_login.setVisibility(View.GONE);

                               // Toast.makeText(getContext(),"Login Valid",Toast.LENGTH_LONG).show();
                                loginResults=response.body().getResults();
                                editor = getActivity().getSharedPreferences("mypref", MODE_PRIVATE).edit();
                                editor.putInt("id",loginResults.getId());
                                editor.putString("name",loginResults.getName());
                                editor.putString("email",loginResults.getEmail());
                                editor.putString("image",loginResults.getAvatar());
                                editor.putBoolean("Islogin", true);
                                editor.putBoolean("FormLogin",true);
                                editor.apply();
                               // Objects.requireNonNull(LoginFragment.super.getActivity()).onBackPressed();
                                Intent intent = new Intent(getActivity(),MainLayoutActivity.class);
                                startActivity(intent);
                            }
                            if(status==400)
                            {
                                Toast.makeText(getContext(),"Login Invalid",Toast.LENGTH_LONG).show();
                                mlogin_email.setError(getResources().getString(R.string.up_wrong));
                                mlogin_password.setError("");
                                mlogin_email.getEditText().setText(null);
                                mlogin_password.getEditText().setText(null);
                                mlogin.setEnabled(true);
                                RegisterActivity.mprogressBar_login.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                           // Toast.makeText(getContext(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();


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
                            RegisterActivity.mprogressBar_login.setVisibility(View.GONE);
                            mlogin_email.setError(null);
                            mlogin_password.setError(null);
                            mlogin.setEnabled(true);
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
