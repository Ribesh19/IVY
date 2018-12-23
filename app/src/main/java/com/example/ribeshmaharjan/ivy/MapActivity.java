package com.example.ribeshmaharjan.ivy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ImageButton;


import android.widget.Spinner;
import android.widget.Toast;

import com.example.ribeshmaharjan.ivy.model.School;
import com.example.ribeshmaharjan.ivy.model.SchoolResponse;
import com.example.ribeshmaharjan.ivy.model.city;
import com.example.ribeshmaharjan.ivy.model.cityresponse;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    Button mListview;
    Spinner spinner;
    ImageButton msignup;
    SharedPreferences prefs;

    private GoogleMap mMap;

    private static final String TAG = MapActivity.class.getSimpleName();
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    List<city> citieslist;
    //List<School> schoolslist2=null;
    List<School> schoolslist2=ListingHelpFragment.schoolListformap;
    LatLng latLng=SchoollistAdapter.latLng;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(18.922025, 72.834563);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";



    private static String URL1 = "https://api.ivyschool.in/api/getcity";

    ArrayList<String> list=new ArrayList<>();
    final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);



        spinner=findViewById(R.id.spinner_mapview);

        Call<cityresponse> call= apiInterface.getcity();

        call.enqueue(new Callback<cityresponse>() {
            @Override
            public void onResponse(@NonNull Call<cityresponse> call, @NonNull retrofit2.Response<cityresponse> response) {
                assert response.body() != null;
                int statuscode = response.code();
                citieslist = response.body().getResults();
                Log.d(TAG, "Number of cities received: " + citieslist.size());
                if(citieslist!=null) {
                    SpinnerAdapter adapter = new SpinnerAdapter(MapActivity.this, R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                    spinner.setAdapter(adapter);
                }
                else
                {
                    SpinnerAdapter adapter = new SpinnerAdapter(MapActivity.this, R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                    spinner.setAdapter(adapter);
                }

            }
            @Override
            public void onFailure(@NonNull Call<cityresponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // String city = adapterView.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                // String cityname=adapterView.getSelectedItem().toString();
                int city = spinner.getSelectedItemPosition();
                if (citieslist != null) {
                    String cityname = citieslist.get(city).getCity();
                    //Toast.makeText(getContext(), cityname, Toast.LENGTH_LONG).show();
                    Call<SchoolResponse> call1 = apiInterface.getschools(cityname);
                    call1.enqueue(new Callback<SchoolResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SchoolResponse> call1, @NonNull retrofit2.Response<SchoolResponse> response) {
                            //Toast.makeText(MapActivity.this, " Success", Toast.LENGTH_LONG).show();
                            assert response.body() != null;
                            schoolslist2 = response.body().getResults();
                            updateLocationUI();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(Objects.requireNonNull(mLastKnownLocation).getLatitude(),
                                            mLastKnownLocation.getLongitude()), 12));

                            //addmarkers(schoolslist);
                        }

                        @Override
                        public void onFailure(@NonNull Call<SchoolResponse> call1, @NonNull Throwable t) {
                            //Toast.makeText(getContext(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        msignup=findViewById(R.id.signup_login_map);
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                boolean Islogin=prefs.getBoolean("Islogin",false);
                if(Islogin)
                {
                    Intent intent2=new Intent(MapActivity.this,UserDetailActivity.class);
                    startActivity(intent2);
                }
                else
                {

                    Intent intent1=new Intent(MapActivity.this,RegisterActivity.class);
                    startActivity(intent1);
                }

            }
        });
        mListview=findViewById(R.id.btn_listview);
        mListview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MapActivity.this,"List view",Toast.LENGTH_LONG).show();
                /*Intent intent1=new Intent(MapActivity.this,MainLayoutActivity.class);
                startActivity(intent1);*/
                MapActivity.super.onBackPressed();
            }
        });

        /*SpinnerAdapter adapter=new SpinnerAdapter(MapActivity.this,R.layout.spinner_layout,R.id.spinner_txt_item,list);

        spinner.setAdapter(adapter);*/

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);



    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;


        /*try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style file. Error: ", e);
        }*/
        mMap.setPadding(0,200,32,0);


        // Prompt the user for permission.
        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
       // Marker visibilemarkers = new Marker().addmarkers(schoolslist2);
        //addmarkers(schoolslist2);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_selected_geo));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(Objects.requireNonNull(marker.getPosition().latitude),
                                marker.getPosition().longitude), DEFAULT_ZOOM));
                return false;
            }
        });

}


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(Objects.requireNonNull(mLastKnownLocation).getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            latLng=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
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
            getDeviceLocation();//added
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
            getDeviceLocation();
            updateLocationUI();
        }
    }

    /**
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }
        mMap.addMarker(new MarkerOptions()
                .title(getString(R.string.default_info_title))
                .position(mDefaultLocation)
                .snippet(getString(R.string.default_info_snippet)));
        // Prompt the user for permission.
       //getLocationPermission();
    }

    private void updateLocationUI() {
        if (mMap == null) {

            return;
        }
        try {
            if (mLocationPermissionGranted) {
                int i;
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                if(schoolslist2.size()!=0) {
                    mMap.clear();
                    for (i = 0; i < schoolslist2.size(); i++) {
                        Double distance = (getDistanceBetween(latLng.latitude,
                                latLng.longitude,schoolslist2.get(i).getLatitude(),schoolslist2.get(i).getLongitude()))/1000;
                        LatLng latLng = new LatLng(schoolslist2.get(i).getLatitude(), schoolslist2.get(i).getLongitude());
                        // Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                        Marker marker=mMap.addMarker(new MarkerOptions().position(latLng).title(schoolslist2.get(i).getName()).draggable(false)
                        .snippet(String.format("%.2f",distance)+" "+"Km from you"));
                        marker.setTag(schoolslist2.get(i).getId());
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(schoolslist2.get(i).getName()).draggable(false));

                    }



                }
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                //getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
public static Double getDistanceBetween(double lat1, double lon1, double lat2, double lon2) {
    if (lat1 == 0 || lat2 == 0)
        return null;
    float[] result = new float[1];
    Location.distanceBetween(lat1, lon1,
            lat2, lon2, result);
    return (double) result[0];
}

}

