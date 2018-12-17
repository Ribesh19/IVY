package com.example.ribeshmaharjan.ivy;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewTreeObserver;
import android.widget.Button;

import android.widget.Toast;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;


public class MainLayoutActivity extends AppCompatActivity {

    Button mpromptcancel;
    Button mpromptsubmit;


    FavouriteFragment favouriteFragment = new FavouriteFragment();
    HelpFragment helpFragment = new HelpFragment();
    ListingHelpFragment listingHelpFragment = new ListingHelpFragment();
    SearchFragment searchFragment = new SearchFragment();
    FloatingActionButton floatingActionButton;


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
        setContentView(R.layout.activity_main_layout);
        android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.frameLayout_replace, listingHelpFragment)
                .commit();
        floatingActionButton = findViewById(R.id.fab_listinghelp);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent=new Intent(MainLayoutActivity.this,HelpActivity.class);
                startActivity(intent);*/
               /* android.support.v4.app.FragmentManager fragmentManager0 =getSupportFragmentManager();
                fragmentManager0.beginTransaction()
                        .replace(R.id.frameLayout_replace,helpFragment)
                        .commit();*/
                /*Dialog fabdialog=new Dialog(MainLayoutActivity.this);
                fabdialog.setContentView(R.layout.fablayout);
                WindowManager.LayoutParams layoutParams1 = new WindowManager.LayoutParams();
                layoutParams1.copyFrom(fabdialog.getWindow().getAttributes());
                *//*layoutParams1.height = WindowManager.LayoutParams.MATCH_PARENT;*//*
                layoutParams1.gravity=Gravity.END;
                layoutParams1.gravity=Gravity.BOTTOM;
                //layoutParams1.flags=~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                fabdialog.getWindow().setAttributes(layoutParams1);
                fabdialog.show();*/


                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainLayoutActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialoguebox, null);
                mpromptsubmit = dialogView.findViewById(R.id.btn_dialog_submit);
                mpromptcancel = dialogView.findViewById(R.id.btn_dialog_cancel);
                dialog.setView(dialogView);
                dialog.show();


                mpromptcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mpromptsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainLayoutActivity.this, "SUBMIT", Toast.LENGTH_LONG).show();
                    }
                });

                    /*dialog.setContentView(R.layout.dialoguebox);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    //layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.getWindow().setAttributes(layoutParams);*/
                //ialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                //FloatingActionButton floatingActionButton = new FloatingActionButton(MainLayoutActivity.this);
                /*FloatingActionButton floatingActionButton=findViewById(R.id.cross_fab);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)floatingActionButton.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                floatingActionButton.setLayoutParams(params);*/
                //floatingActionButton.setBackgroundResource(R.drawable.ic_icon_cross);
                //floatingActionButton.setBackgroundColor(Color.rgb(131,131,134));


            }
        });
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            navigation.setVisibility(View.INVISIBLE);
                        }else{

                        navigation.setVisibility(View.VISIBLE);
                        }
                    }
                });
        }
}
