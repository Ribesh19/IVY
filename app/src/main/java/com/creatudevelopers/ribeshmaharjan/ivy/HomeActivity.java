package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FavouriteResponse;

import com.creatudevelopers.ribeshmaharjan.ivy.model.cityresponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;
import com.google.android.gms.maps.model.LatLng;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    Button mbtnsignup;
    SharedPreferences prefs;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    static int favourite_school_list[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getLocationPermission();
        setContentView(R.layout.home_activity_layout);
        mbtnsignup = findViewById(R.id.btn_signUP_homepage);
        mbtnsignup.setEnabled(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        boolean Islogin = prefs.getBoolean("Islogin", false);
        final Integer id = prefs.getInt("id", 0);
        if (Islogin) {
            //Toast.makeText(HomeActivity.this,"logging",Toast.LENGTH_LONG).show();
            mbtnsignup.setVisibility(View.GONE);
            Call<FavouriteResponse> favouriteResponseCall = apiInterface.getfavourite(id);
            favouriteResponseCall.enqueue(new Callback<FavouriteResponse>() {
                @Override
                public void onResponse(@NonNull Call<FavouriteResponse> call, @NonNull Response<FavouriteResponse> response) {
                    FavouriteFragment.favouriteResults = response.body().getResults();
                    favourite_school_list = new int[FavouriteFragment.favouriteResults.size()];
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        favourite_school_list[i] = Integer.valueOf(response.body().getResults().get(i).getSchoolId());

                    }


                }

                @Override
                public void onFailure(@NonNull Call<FavouriteResponse> call, @NonNull Throwable t) {

                }
            });
            Call<cityresponse> call = apiInterface.getcity();
            call.enqueue(new Callback<cityresponse>() {
                @Override
                public void onResponse(@NonNull Call<cityresponse> call, @NonNull retrofit2.Response<cityresponse> response) {
                    assert response.body() != null;
                    int statuscode = response.code();
                    ListingHelpFragment.citieslist = response.body().getResults();
                    Intent intent = new Intent(HomeActivity.this, MainLayoutActivity.class);
                    startActivity(intent);

                    //Log.d(TAG, "Number of cities received: " + citieslist.size());


                }

                @Override
                public void onFailure(@NonNull Call<cityresponse> call, @NonNull Throwable t) {
                    //Log.e(TAG, t.toString());


                }
            });


        }

            mbtnsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mbtnsignup.setEnabled(false);
                    Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            });



    }


    @Override
    public void onLocationChanged(Location location) {
        SchoollistAdapter.latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }



    @Override
    public void onBackPressed() {
       moveTaskToBack(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        boolean Islogin = prefs.getBoolean("Islogin", false);
        if(Islogin)
        {
            Intent intent = new Intent(HomeActivity.this, MainLayoutActivity.class);
            startActivity(intent);
        }


    }
}
