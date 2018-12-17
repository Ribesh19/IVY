package com.example.ribeshmaharjan.ivy;

import android.content.Intent;

import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;





public class RegisterActivity extends AppCompatActivity {
    ImageButton mbtnback;
    Button mfacbook;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    final SignUpFragment signUpFragment=new SignUpFragment();
    final LoginFragment loginFragment=new LoginFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final android.support.v4.app.FragmentManager fragmentManager0 =getSupportFragmentManager();
        fragmentManager0.beginTransaction()
                .replace(R.id.container_frame,loginFragment)
                .commit();
        mbtnback=findViewById(R.id.btn_back_register_activity);
        mfacbook=findViewById(R.id.facebook);

        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"Button pressed",Toast.LENGTH_LONG).show();
                /*Intent intent=new Intent(RegisterActivity.this,MainLayoutActivity.class);
                startActivity(intent);*/
                RegisterActivity.super.onBackPressed();
            }
        });
        mfacbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(RegisterActivity.this,"Button  Facebook pressed",Toast.LENGTH_LONG).show();
            }
        });





        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.container);
        //setupViewPager(mViewPager);

        final TabLayout tabLayout = findViewById(R.id.tabs);
       // tabLayout.setupWithViewPager(mViewPager);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                   // Toast.makeText(RegisterActivity.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                    fragmentManager0.beginTransaction()
                            .replace(R.id.container_frame,loginFragment)
                            .commit();

                }else if (tabLayout.getSelectedTabPosition()==1)
                {
                            fragmentManager0.beginTransaction()
                            .replace(R.id .container_frame,signUpFragment)
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




        //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }
   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignUpFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/




}
