package com.example.ribeshmaharjan.ivy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListingHelpActivity extends AppCompatActivity {

   Spinner spinner;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_search:

                    return true;
                case R.id.navigation_favourite:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_help);spinner=findViewById(R.id.spinner_listhelper);
        //String [] location={"Delhi","Hauz Khas", "Lajpat Nagar","Greater Kailash","Rangpuri", "Noida"};
        //ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,location);
        //spinner.setAdapter(adapter);
        ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("Delhi"));
        list.add(new ItemData("Hauz Khas"));
        list.add(new ItemData("Lajpat Nagar"));
        list.add(new ItemData("Greater Kailash"));
        list.add(new ItemData("Rangpuri"));
        list.add(new ItemData("Noida"));
        SpinnerAdapter adapter=new SpinnerAdapter(this,R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
