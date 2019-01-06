package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Intent;

import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.creatudevelopers.ribeshmaharjan.ivy.model.LoginResults;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SignUpResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SignUpResult;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


import java.net.URL;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    ImageButton mbtnback;
    Button mfacbook;
    Button mgoogle;
    static GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN;
    SharedPreferences.Editor editor;
    static boolean isLoggedIn= false;
   GoogleSignInAccount account;
   static AccessToken accessToken;
    static URL profile_pic;

    static ProgressBar mprogressBar_login;
    static LoginManager loginManager;
    LoginResults loginResults;

   private static final String EMAIL = "email";
    final SignUpFragment signUpFragment=new SignUpFragment();
    final LoginFragment loginFragment=new LoginFragment();
    private CallbackManager callbackManager;
    final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn= accessToken != null && !accessToken.isExpired();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        setContentView(R.layout.activity_register);
        final android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.container_frame, loginFragment)
                .commit();
        mbtnback = findViewById(R.id.btn_back_register_activity);
        mfacbook = findViewById(R.id.facebook);
        mgoogle=findViewById(R.id.google);
        mprogressBar_login=findViewById(R.id.progressBar_login);
        mprogressBar_login.setVisibility(View.GONE);

        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"Button pressed",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                startActivity(intent);
               // RegisterActivity.super.onBackPressed();
            }
        });
        mgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_LONG).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted( JSONObject object,
                                    GraphResponse response) {
                                // Application code

                                editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                                try {
                                    int id=object.getInt("id");
                                    /*profile_pic  = new URL(
                                            "http://graph.facebook.com/" + object.getString("id")+ "/picture?type=large");
                                    Log.i("profile_pic",
                                            profile_pic + "");*/
                                    editor.putInt("id",id);
                                    editor.putString("name",object.getString("name"));
                                    editor.putString("email",object.getString("email"));
                                    editor.putBoolean("signinwithfb",true);
                                    editor.putBoolean("Islogin", true);
                                    editor.putString("image",object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    editor.apply();
                                    //Log.i("profile_pic",object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                } catch (JSONException /*| MalformedURLException*/ e) {
                                    e.printStackTrace();
                                }

                                Log.v("LoginActivity", response.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,picture");
                 //parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
                Intent intent = new Intent(RegisterActivity.this,MainLayoutActivity.class);
                startActivity(intent);


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();


            }
        });

        mfacbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                boolean Islogin=prefs.getBoolean("Islogin",false);
                if(!Islogin)
                {
                    loginManager.getInstance().logInWithReadPermissions(RegisterActivity.this,Arrays.asList(EMAIL));

                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Already Logged in with facebook",Toast.LENGTH_LONG).show();
                }
              //LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile, email, user_birthday, user_friends"));
               // RegisterActivity.super.onBackPressed();
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


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    @Override
    protected void onStart() {

        super.onStart();
        /*accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn= accessToken != null && !accessToken.isExpired();*/
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
           // Toast.makeText(RegisterActivity.this,"You Haven't Signed in yet",Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent(RegisterActivity.this,UserDetailActivity.class);
            startActivity(intent); */
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            RegisterActivity.mprogressBar_login.setVisibility(View.VISIBLE);
            if(account!=null) {
                editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putInt("id",account.hashCode());
                editor.putString("name", account.getDisplayName());
                editor.putString("email", account.getEmail());
                if(account.getPhotoUrl()!=null) {
                    editor.putString("image", account.getPhotoUrl().toString());
                }
                editor.putBoolean("Islogin", true);
                editor.putBoolean("SignIngoogle",true);
                editor.apply();

                Call<SignUpResponse> signUpResponseCall =apiInterface.signup(account.getDisplayName(),account.getEmail(),"123");
                signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                        int statuscode=response.code();

                        if(statuscode==200)
                        {
                            SignUpResult signUpResult=response.body().getResults();
                            editor =getSharedPreferences("mypref", MODE_PRIVATE).edit();
                            editor.putInt("id",signUpResult.getId());
                            Intent intent = new Intent(RegisterActivity.this, MainLayoutActivity.class);
                            startActivity(intent);
                            finish();


                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {

                    }
                });






            }

            // Signed in successfully, show authenticated UI.

           // updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(RegisterActivity.EMAIL, "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
        }
    }



}
