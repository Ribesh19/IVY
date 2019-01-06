package com.creatudevelopers.ribeshmaharjan.ivy;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.Typeface;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.creatudevelopers.ribeshmaharjan.ivy.model.School;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SchoolResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.city;
import com.creatudevelopers.ribeshmaharjan.ivy.model.cityresponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;
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

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback /*, GoogleMap.InfoWindowAdapter */{
    Button mListview;
    Spinner spinner;
    ImageButton msignup;
    SharedPreferences prefs;
    TextView mtextView4;
    ProgressBar mprogressBar_map;
    TextView mtextView;

    HashMap<Double,LatLng> hashMap;
    private GoogleMap mMap;

    private static final String TAG = MapActivity.class.getSimpleName();
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    List<city> citieslist;
    List<School> schoolslist2=ListingHelpFragment.schoolListformap;
    LatLng latLng=SchoollistAdapter.latLng;
    Double distance[];
    Double distance_forinfowindow[];

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
        mtextView4=findViewById(R.id.textView4);
        spinner=findViewById(R.id.spinner_mapview);
        mprogressBar_map=findViewById(R.id.progressBar_map);
        mprogressBar_map.setVisibility(View.VISIBLE);
        mtextView=findViewById(R.id.textView);
        final boolean[] firsttime = {true};
        Typeface typeface = ResourcesCompat.getFont(MapActivity.this, R.font.montserrat_bold2);
        Typeface typeface_regular = ResourcesCompat.getFont(MapActivity.this, R.font.montserrat_regular);

        mtextView4.setTypeface(typeface);
        mtextView.setTypeface(typeface);


       // Toast.makeText(MapActivity.this,String.valueOf(ListingHelpFragment.spinnerselected_position),Toast.LENGTH_LONG).show();
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
                if(firsttime[0]) {
                    spinner.setSelection(ListingHelpFragment.spinnerselected_position);
                    firsttime[0] =false;
                }

                mprogressBar_map.setVisibility(View.VISIBLE);

                int city = spinner.getSelectedItemPosition();

                ListingHelpFragment.spinnerselected_position=city;
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
                                    new LatLng(Objects.requireNonNull(hashMap.get(distance[0]).latitude),
                                            hashMap.get(distance[0]).longitude), DEFAULT_ZOOM));
                            mprogressBar_map.setVisibility(View.GONE);

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
        mListview.setTypeface(typeface);
        mListview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MapActivity.this,"List view",Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(MapActivity.this,MainLayoutActivity.class);
                startActivity(intent1);
               /* MapActivity.super.onBackPressed();*/
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
            public boolean onMarkerClick(final Marker marker) {

                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_selected_geo));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(Objects.requireNonNull(marker.getPosition().latitude),
                                marker.getPosition().longitude), DEFAULT_ZOOM));
               // marker.showInfoWindow();
                Typeface typeface = ResourcesCompat.getFont(MapActivity.this, R.font.montserrat_regular);
                LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
                final Dialog dialog=new Dialog(MapActivity.this);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
               // wlp.flags &= ~WindowManager.LayoutParams.DIM_AMOUNT_CHANGED;
                window.setAttributes(wlp);
                View view1 = inflater.inflate(R.layout.custom_infowindow, null);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view1);

                ImageView mimageView_infowindow= dialog.findViewById(R.id.imageView_infowindow);
                TextView mschool_name_infowindow=dialog.findViewById(R.id.school_name_infowindow);
                TextView mtxtview_status_infowindow=dialog.findViewById(R.id.txtview_status_infowindow);
                TextView mtxtview_distance_infowindow=dialog.findViewById(R.id.txtview_distance_infowindow);
                RatingBar mrating_bar_infowindow=dialog.findViewById(R.id.rating_bar_infowindow);
                mschool_name_infowindow.setText(schoolslist2.get((Integer)marker.getTag()).getName());
                Picasso.get().load(schoolslist2.get((Integer)marker.getTag()).getImages().get(0)).into(mimageView_infowindow);
                mtxtview_status_infowindow.setText(schoolslist2.get((Integer)marker.getTag()).getOpeningtime());
                mtxtview_status_infowindow.setTypeface(typeface);
                mtxtview_distance_infowindow.setTypeface(typeface);
                mrating_bar_infowindow.setRating(schoolslist2.get((Integer)marker.getTag()).getSchoolaverage());
                mtxtview_distance_infowindow.setText(String.format("%s Km from you", String.format("%.2f",
                        distance_forinfowindow[(Integer) marker.getTag()])));
                mtxtview_distance_infowindow.setTypeface(typeface);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(layoutParams);
                dialog.show();

                mschool_name_infowindow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapActivity.this, DetailActivity.class);
                        intent.putExtra("schoolID",Integer.valueOf(schoolslist2.get((Integer)marker.getTag()).getId()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                mimageView_infowindow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapActivity.this, DetailActivity.class);
                        intent.putExtra("schoolID",Integer.valueOf(schoolslist2.get((Integer)marker.getTag()).getId()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                return false;
            }
        });

       /*mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
           @Override
           public void onInfoWindowClick(Marker marker) {
              // Toast.makeText(MapActivity.this,marker.getTag().toString(),Toast.LENGTH_LONG).show();
               Intent intent = new Intent(MapActivity.this, DetailActivity.class);
               intent.putExtra("schoolID",Integer.valueOf(marker.getTag().toString()));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
           }
       });
*/
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
                    hashMap=new HashMap<>();
                    distance =new Double[schoolslist2.size()];
                    distance_forinfowindow=new Double[schoolslist2.size()];
                    for (i = 0; i < schoolslist2.size(); i++) {
                         distance[i] = (getDistanceBetween(latLng.latitude,
                                latLng.longitude,schoolslist2.get(i).getLatitude(),schoolslist2.get(i).getLongitude()))/1000;
                         distance_forinfowindow[i]=distance[i];
                         latLng = new LatLng(schoolslist2.get(i).getLatitude(), schoolslist2.get(i).getLongitude());
                        Marker marker=mMap.addMarker(new MarkerOptions().position(latLng)/*.title(schoolslist2.get(i).getName())*/.draggable(false));
                        marker.setTag(i);
                        hashMap.put(distance[i],latLng);
                    }
                   Arrays.sort(distance);
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1=new Intent(MapActivity.this,MainLayoutActivity.class);
        startActivity(intent1);
    }
}

