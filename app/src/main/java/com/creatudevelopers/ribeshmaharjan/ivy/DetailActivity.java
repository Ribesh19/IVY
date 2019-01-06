package com.creatudevelopers.ribeshmaharjan.ivy;

import android.app.ActivityOptions;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;

import android.transition.Fade;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.creatudevelopers.ribeshmaharjan.ivy.model.AddFavouriteResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.Detail;
import com.creatudevelopers.ribeshmaharjan.ivy.model.DetailResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    ImageButton mbackbtn;
    Button mrequestfeestructure;
    Button mreview;
    Button mbookappointment;
    ToggleButton mmakefavourite;
    Button mseeallreview;
    ProgressBar mprogressbar;
    Slider slider;

    TextView mschoolname;
    TextView maddress;
    TextView maboutuscontent;
    TextView magegroup;
    RatingBar mratingbar2;
    RatingBar ratingBar_security;
    RatingBar ratingBar_staff;
    RatingBar ratingBar_infrastructure;
    RatingBar ratingBar_curriculum;
    TextView mtransportation_context;

    TextView mfacilities_content;
    Button mbtn_call_detail;
    Button mbtn_location_detail;
    TextView mtxtview_status_2;
    int fav_id;
    String fromfavourtie;



    Detail detail=null;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       final Integer schoolid= getIntent().getIntExtra("schoolID",1);
        fromfavourtie=getIntent().getStringExtra("fromfavourtie");
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_detail);
        //Toast.makeText(DetailActivity.this,Integer.toString(schoolid),Toast.LENGTH_LONG).show();

        mschoolname=findViewById(R.id.school_name);
        maddress=findViewById(R.id.txt_address);
        maboutuscontent=findViewById(R.id.aboutus_content);
        magegroup=findViewById(R.id.txtview_agegroup);
        mratingbar2=findViewById(R.id.rating2);
        ratingBar_security=findViewById(R.id.rating_security);
        ratingBar_curriculum=findViewById(R.id.rating_curriculum);
        ratingBar_infrastructure=findViewById(R.id.rating_infrastructure);
        ratingBar_staff=findViewById(R.id.rating_qualified_staff);

        slider=findViewById(R.id.imageView2);
        mfacilities_content=findViewById(R.id.facilities_content);
        mbtn_call_detail=findViewById(R.id.btn_call_detail);
        mbtn_location_detail=findViewById(R.id.btn_location_detail);
        mtxtview_status_2=findViewById(R.id.txtview_status_2);

        mprogressbar=findViewById(R.id.progressBar_detail);
        mbackbtn=findViewById(R.id.back_btn);
        mrequestfeestructure=findViewById(R.id.btn_requestfeestructure_detail);
        mbookappointment=findViewById(R.id.btn_bookanappointment_detail);
        mmakefavourite=findViewById(R.id.btn_favourite);
        mmakefavourite.setTextOff(null);
        mmakefavourite.setTextOn(null);
        mmakefavourite.setText(null);
        mmakefavourite.setEnabled(true);
        mreview=findViewById(R.id.btn_review);
        mseeallreview=findViewById(R.id.seeallreview);
        mprogressbar.setVisibility(View.VISIBLE);
        mtransportation_context=findViewById(R.id.transportation_context);

        final RecyclerView recyclerView =findViewById(R.id.allreview_recyclerview_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        final Integer id =prefs.getInt("id",0);
        //final boolean Islogin= prefs.getBoolean("Islogin",false);

        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<DetailResponse> call_detailing=apiInterface.getschooldetail(schoolid);
        call_detailing.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailResponse> call, @NonNull Response<DetailResponse> response) {
                mprogressbar.setVisibility(View.GONE);

                int statuscode = response.code();
                assert response.body() != null;
                detail = response.body().getResults();
                List<Slide> slideList1 = new ArrayList<>();
                for (int i = 0; i < detail.getImages().size(); i++) {
                    slideList1.add((new Slide(i, detail.getImages().get(i), getResources().getDimensionPixelSize(R.dimen.slider_image_corner))));
                }
                slider.addSlides(slideList1);

                mschoolname.setText(detail.getName());
                String address = detail.getAddress() + ", " + detail.getCity() + ", State - " + detail.getState() + ", " + detail.getPostCode() + ", " + detail.getCountry();
                maddress.setText(address);
                   // int fav_id = detail.getIsFavorite();

                    for(int i=0;i<HomeActivity.favourite_school_list.length;i++)
                    {
                        if(HomeActivity.favourite_school_list[i]==schoolid)
                        {
                            fav_id = 1;
                        }

                    }
                    if (fav_id == 0) {
                        mmakefavourite.setChecked(false);
                        mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle));
                    } else {
                        mmakefavourite.setChecked(true);
                        mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_red));
                    }

                maboutuscontent.setText(stripHtml(detail.getDescription()));
                    mtransportation_context.setText(stripHtml(detail.getTransport()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mtransportation_context.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    maboutuscontent.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                if(detail.getFacilities()!=null) {
                    mfacilities_content.setText(stripHtml(detail.getFacilities()));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mfacilities_content.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                mtxtview_status_2.setText(String.format("%s - %s", detail.getOpeningtime(), detail.getClosingtime()));
                magegroup.setText(detail.getAgeGroup());
                if(detail.getSchoolaverage()!=null) {
                    mratingbar2.setRating(detail.getSchoolaverage());
                    ratingBar_security.setRating(detail.getSecurityaverage());
                    ratingBar_staff.setRating(detail.getStaffaverage());
                    ratingBar_curriculum.setRating(detail.getCurriculumaverage());
                    ratingBar_infrastructure.setRating(detail.getInfrastructureaverage());
                }
                else
                {
                    mratingbar2.setRating(0);
                    ratingBar_security.setRating(0);
                    ratingBar_staff.setRating(0);
                    ratingBar_curriculum.setRating(0);
                    ratingBar_infrastructure.setRating(0);
                }
                if (!detail.getLatestReview().isEmpty() || detail.getLatestReview()!=null) {
                    final DetailAllreviewAdapter recyclerview_adapter1 = new DetailAllreviewAdapter(Objects.requireNonNull(getApplicationContext()),detail.getLatestReview());
                    recyclerView.setAdapter(recyclerview_adapter1);

                }


            }

            @Override
            public void onFailure(@NonNull Call<DetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, t.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                   DetailActivity.super.onBackPressed();
            }
        });
        mrequestfeestructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(DetailActivity.this,FeeStructureActivity.class);
                intent2.putExtra("schoolID",schoolid);
                startActivity(intent2);
            }
        });
        mreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(DetailActivity.this,RatingActivity.class);
                intent3.putExtra("schoolID",schoolid);
               startActivity(intent3,ActivityOptions.makeSceneTransitionAnimation(DetailActivity.this).toBundle());
                //startActivity(intent3);
            }
        });
        mbookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(DetailActivity.this,AppointmentActivity.class);
                intent4.putExtra("schoolID",schoolid);
                startActivity(intent4);

            }
        });
        mseeallreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(DetailActivity.this,AllReviewAcitivity.class);
                intent6.putExtra("schoolID", schoolid);
                startActivity(intent6);
            }
        });
        mbtn_call_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });
        mbtn_location_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*Uri gmmIntentUri = Uri.parse("google.navigation geo:"+ detail.getLatitude()+","+detail.getLongitude()+"&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");*/
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+detail.getLatitude()+","+detail.getLongitude()));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        mmakefavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mmakefavourite.setEnabled(false);
                    if(isChecked)
                    {
                        if(id!=0) {
                            Call<AddFavouriteResponse> addFavouriteResponseCall = apiInterface.makefavourite(schoolid, id);
                            addFavouriteResponseCall.enqueue(new Callback<AddFavouriteResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<AddFavouriteResponse> call, @NonNull Response<AddFavouriteResponse> response) {
                                    mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_red));
                                    /*LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_favourite_layout,
                                            (ViewGroup) findViewById(R.id.toast_layout_card));
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();*/
                                }
                                @Override
                                public void onFailure(@NonNull Call<AddFavouriteResponse> call, @NonNull Throwable t) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(DetailActivity.this,"id 0",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        mmakefavourite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_circle));
                        Call<AddFavouriteResponse> addFavouriteResponseCall = apiInterface.makefavourite(schoolid, id);

                    }
                }
        });


        }
    @SuppressWarnings("deprecation")
    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }


    @Override
    public void onBackPressed() {
            DetailActivity.super.onBackPressed();

    }
}
