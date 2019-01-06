package com.creatudevelopers.ribeshmaharjan.ivy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FeeStructureResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;


public class MainLayoutActivity extends AppCompatActivity {

    ImageButton mpromptcancel;
    Button mpromptsubmit;
    int count;
    static BottomNavigationView navigation;
    boolean doubleBackToExitPressedOnce = false;


    FavouriteFragment favouriteFragment = new FavouriteFragment();

    ListingHelpFragment listingHelpFragment = new ListingHelpFragment();
    SearchFragment searchFragment = new SearchFragment();
    FloatingActionButton floatingActionButton;
    android.support.v4.app.FragmentManager fragmentManager0 = getSupportFragmentManager();




    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewFragment(listingHelpFragment,"HOME_FRAGMENT");
                    //Toast.makeText(ListingHelpActivity.this,"Home",Toast.LENGTH_LONG).show();

                   /* fragmentManager0.beginTransaction()
                            .replace(R.id.frameLayout_replace, listingHelpFragment)
                            .commit();
*/
                    return true;
                case R.id.navigation_search:
                    // Toast.makeText(ListingHelpActivity.this,"Search",Toast.LENGTH_LONG).show();
                   // android.support.v4.app.FragmentManager fragmentManager2 = getSupportFragmentManager();
                    viewFragment(searchFragment,"OTHER_FRAGMENT");
                    /*fragmentManager0.beginTransaction()
                            .replace(R.id.frameLayout_replace, searchFragment)
                            .addToBackStack("a")
                            .commit();*/
                    return true;
                case R.id.navigation_favourite:
                    // Toast.makeText(ListingHelpActivity.this,"Favourite",Toast.LENGTH_LONG).show();
                    //android.support.v4.app.FragmentManager fragmentManager1 = getSupportFragmentManager();
                    viewFragment(favouriteFragment,"OTHER_FRAGMENT");
                    /*fragmentManager0.beginTransaction()
                            .replace(R.id.frameLayout_replace, favouriteFragment)
                            .addToBackStack("a")
                            .commit();*/
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_layout);
        viewFragment(listingHelpFragment,"HOME_FRAGMENT");
        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        floatingActionButton = findViewById(R.id.fab_listinghelp);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog=new Dialog(MainLayoutActivity.this);
                dialog.setContentView(R.layout.dialoguebox);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(layoutParams);
                dialog.show();
                 final EditText madditional_info_help=dialog.findViewById(R.id.additional_info_help);
                 final EditText memail_help=dialog.findViewById(R.id.email_help);
                final EditText mphonenumber_help=dialog.findViewById(R.id.phonenumber_help);
                SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
                String email = prefs.getString("email","");
                final String phonenumber_from_prefs=prefs.getString("phone","");
                    memail_help.setText(email);
                    mphonenumber_help.setText(phonenumber_from_prefs);
                mpromptcancel=dialog.findViewById(R.id.dialog_close);
                mpromptsubmit = dialog.findViewById(R.id.btn_dialog_submit);
                mpromptcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                mpromptsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email=memail_help.getText().toString();
                        String phone=mphonenumber_help.getText().toString();
                        String feeeback=madditional_info_help.getText().toString();
                        SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                        editor.putString("phone",phone);
                        if(email.isEmpty() )
                        {
                            memail_help.setError("Field must not be empty.");
                        }
                        if (phone.isEmpty())
                        {
                            mphonenumber_help.setError("Field must not be empty.");
                        }
                        //Toast.makeText(MainLayoutActivity.this, email, Toast.LENGTH_SHORT).show();
                        if(!email.isEmpty() && !phone.isEmpty() && !feeeback.isEmpty()) {
                            Call<FeeStructureResponse> feedbackcall = apiInterface.postfeedback(email, phone, feeeback);
                            feedbackcall.enqueue(new Callback<FeeStructureResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<FeeStructureResponse> call, @NonNull Response<FeeStructureResponse> response) {
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_saved_layout, (ViewGroup) findViewById(R.id.toast_layout_card_save));
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    //toast.setText(response.body().getResults());
                                    toast.setView(layout);
                                    toast.show();

                                }

                                @Override
                                public void onFailure(@NonNull Call<FeeStructureResponse> call, @NonNull Throwable t) {

                                }
                            });
                            dialog.cancel();
                        }
                    }
                });



            }
        });
        navigation = findViewById(R.id.navigation);
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

        private void viewFragment(Fragment mfragment,String mname)
        {
            fragmentManager0.beginTransaction()
                    .replace(R.id.frameLayout_replace, mfragment).commit();
            count = fragmentManager0.getBackStackEntryCount();
            if(mname.equals("OTHER_FRAGMENT"))
            {
                fragmentManager0.beginTransaction().addToBackStack(mname).commit();
            }
            fragmentManager0.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if( fragmentManager0.getBackStackEntryCount() <= count){
                        // pop all the fragment and remove the listener
                        fragmentManager0.popBackStack("OTHER_FRAGMENT", POP_BACK_STACK_INCLUSIVE);
                        fragmentManager0.removeOnBackStackChangedListener(this);
                        // set the home button selected
                        navigation.getMenu().getItem(0).setChecked(true);
                        fragmentManager0.beginTransaction()
                                .replace(R.id.frameLayout_replace, listingHelpFragment)
                                .commit();
                    }
                }
            });
        }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           moveTaskToBack(true);
           finish();
            }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
