package com.example.ribeshmaharjan.ivy;

import android.content.Intent;

import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;


public class RegisterActivity extends AppCompatActivity {
    ImageButton mbtnback;
    Button mfacbook;
    Button mgoogle;

    private static final String EMAIL = "email";
    final SignUpFragment signUpFragment=new SignUpFragment();
    final LoginFragment loginFragment=new LoginFragment();
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_register);
        final android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.container_frame, loginFragment)
                .commit();
        mbtnback = findViewById(R.id.btn_back_register_activity);
        mfacbook = findViewById(R.id.facebook);
        mgoogle=findViewById(R.id.google);

        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"Button pressed",Toast.LENGTH_LONG).show();
               /* Intent intent=new Intent(RegisterActivity.this,MainLayoutActivity.class);
                startActivity(intent);*/
                RegisterActivity.super.onBackPressed();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                        RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mfacbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList(EMAIL));

            }
        });


        final TabLayout tabLayout = findViewById(R.id.tabs);
        // tabLayout.setupWithViewPager(mViewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    // Toast.makeText(RegisterActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    fragmentManager0.beginTransaction()
                            .replace(R.id.container_frame, loginFragment)
                            .commit();

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    fragmentManager0.beginTransaction()
                            .replace(R.id.container_frame, signUpFragment)
                            .commit();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}
