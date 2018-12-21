package com.example.ribeshmaharjan.ivy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;


public class MainLayoutActivity extends AppCompatActivity {

    ImageButton mpromptcancel;
    Button mpromptsubmit;



    FavouriteFragment favouriteFragment = new FavouriteFragment();

    ListingHelpFragment listingHelpFragment = new ListingHelpFragment();
    SearchFragment searchFragment = new SearchFragment();
    FloatingActionButton floatingActionButton;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Toast.makeText(ListingHelpActivity.this,"Home",Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();
                    fragmentManager0.beginTransaction()
                            .replace(R.id.frameLayout_replace, listingHelpFragment)
                            .commit();

                    return true;
                case R.id.navigation_search:
                    // Toast.makeText(ListingHelpActivity.this,"Search",Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager fragmentManager2 = getSupportFragmentManager();
                    fragmentManager2.beginTransaction()
                            .replace(R.id.frameLayout_replace, searchFragment)
                            .commit();

                    return true;
                case R.id.navigation_favourite:
                    // Toast.makeText(ListingHelpActivity.this,"Favourite",Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager fragmentManager1 = getSupportFragmentManager();
                    fragmentManager1.beginTransaction()
                            .replace(R.id.frameLayout_replace, favouriteFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getLocationPermission();
        setContentView(R.layout.activity_main_layout);
        android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.frameLayout_replace, listingHelpFragment)
                .commit();
        floatingActionButton = findViewById(R.id.fab_listinghelp);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* final AlertDialog.Builder dialog = new AlertDialog.Builder(MainLayoutActivity.this);
               *//* dialog .setPositiveButton(R.string.submit,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });*//*

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialoguebox, null);
                mpromptsubmit = dialogView.findViewById(R.id.btn_dialog_submit);
                //mpromptcancel = dialogView.findViewById(R.id.btn_dialog_cancel);
                dialog.setView(dialogView);
                dialog.show();*/
               /* mpromptcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
                    }
                });*/


                final Dialog dialog=new Dialog(MainLayoutActivity.this);
                dialog.setContentView(R.layout.dialoguebox);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(layoutParams);
                dialog.show();
                mpromptcancel=dialog.findViewById(R.id.dialog_close);
                mpromptsubmit = dialog.findViewById(R.id.btn_dialog_submit);
                mpromptcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });



            }
        });
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            navigation.setVisibility(View.GONE);
                            floatingActionButton.setVisibility(View.GONE);
                        }else{

                        navigation.setVisibility(View.VISIBLE);
                        floatingActionButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
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
}
