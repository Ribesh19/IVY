package com.example.ribeshmaharjan.ivy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainLayoutActivity extends AppCompatActivity {


    FavouriteFragment favouriteFragment=new FavouriteFragment();
    ListingHelpFragment listingHelpFragment=new ListingHelpFragment();
    FloatingActionButton floatingActionButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Toast.makeText(ListingHelpActivity.this,"Home",Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager fragmentManager0 =getSupportFragmentManager();
                    fragmentManager0.beginTransaction()
                            .replace(R.id.frameLayout_replace,listingHelpFragment)
                            .commit();

                    return true;
                case R.id.navigation_search:
                    // Toast.makeText(ListingHelpActivity.this,"Search",Toast.LENGTH_LONG).show();

                    return true;
                case R.id.navigation_favourite:
                    // Toast.makeText(ListingHelpActivity.this,"Favourite",Toast.LENGTH_LONG).show();
                    android.support.v4.app.FragmentManager fragmentManager1 =getSupportFragmentManager();
                    fragmentManager1.beginTransaction()
                            .replace(R.id.frameLayout_replace,favouriteFragment)
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
        android.support.v4.app.FragmentManager fragmentManager0 =getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.frameLayout_replace,listingHelpFragment)
                .commit();
        floatingActionButton=findViewById(R.id.fab_listinghelp);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainLayoutActivity.this,HelpActivity.class);
                startActivity(intent);
                }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
