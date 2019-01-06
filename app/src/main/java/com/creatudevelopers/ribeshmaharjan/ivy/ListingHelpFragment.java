package com.creatudevelopers.ribeshmaharjan.ivy;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import java.util.List;

import java.util.Objects;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.creatudevelopers.ribeshmaharjan.ivy.model.School;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SchoolResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.city;
import com.creatudevelopers.ribeshmaharjan.ivy.model.cityresponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;


import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListingHelpFragment extends Fragment {
    Context mcontext;
    Spinner spinner;
    Button mbtnmapview;
    ImageButton mbtnsignuplogin;
    ProgressBar progressBar;

    SharedPreferences prefs;
    static int spinnerselected_position;



    private static final String TAG =" Fragment";
    static List<city> citieslist=null;
     List<School> schoolslist=null;
     static List<School> schoolListformap=null;


    public ListingHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_listing_help, container, false);
        spinner=rootview.findViewById(R.id.spinner_listhelper);

        final boolean[] firsttime = {true};
        mbtnsignuplogin=rootview.findViewById(R.id.signup_login);
        progressBar=rootview.findViewById(R.id.progressBar_mainlayout);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);


        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);

            if (citieslist==null) {
                Call<cityresponse> call = apiInterface.getcity();
                call.enqueue(new Callback<cityresponse>() {
                    @Override
                    public void onResponse(@NonNull Call<cityresponse> call, @NonNull retrofit2.Response<cityresponse> response) {
                        assert response.body() != null;
                        int statuscode = response.code();

                        citieslist = response.body().getResults();
                        Log.d(TAG, "Number of cities received: " + citieslist.size());
                        if(getActivity()!=null) {
                            SpinnerAdapter adapter = new SpinnerAdapter(getContext(), R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                            spinner.setAdapter(adapter);
                        }

                    }
                    @Override
                    public void onFailure(@NonNull Call<cityresponse> call, @NonNull Throwable t) {
                        Log.e(TAG, t.toString());

                    }
                });

            }
            else
            {
                SpinnerAdapter adapter = new SpinnerAdapter(getContext(), R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                spinner.setAdapter(adapter);
            }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // String city = adapterView.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
               // String cityname=adapterView.getSelectedItem().toString();
               if(firsttime[0]) {
                   spinner.setSelection(ListingHelpFragment.spinnerselected_position);
                   firsttime[0] =false;
               }
               int city = spinner.getSelectedItemPosition();
               spinnerselected_position=spinner.getSelectedItemPosition();
               if (citieslist != null) {
                   final String cityname = citieslist.get(city).getCity();
                   //Toast.makeText(getContext(), cityname, Toast.LENGTH_LONG).show();
                   if(getActivity()!=null && isAdded()) {
                       Call<SchoolResponse> call1 = apiInterface.getschools(cityname);
                       call1.enqueue(new Callback<SchoolResponse>() {
                           @Override
                           public void onResponse(@NonNull Call<SchoolResponse> call1, @NonNull retrofit2.Response<SchoolResponse> response) {
                               // Toast.makeText(getContext(), "Kathmandu Success", Toast.LENGTH_LONG).show();
                               assert response.body() != null;
                               progressBar.setVisibility(View.GONE);
                         /*  if(spinner.getSelectedItemPosition()==0)
                           {
                              // Toast.makeText(mcontext, "Kathmandu Success", Toast.LENGTH_LONG).show();
                               schoolListformap=response.body().getResults();
                           }*/
                               schoolListformap = response.body().getResults();
                               schoolslist = response.body().getResults();
                               if(getContext()!=null) {
                                   SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(getActivity(), schoolslist);
                                   recyclerView.setAdapter(recyclerview_adapter);
                               }
                           }

                           @Override
                           public void onFailure(@NonNull Call<SchoolResponse> call1, @NonNull Throwable t) {
                               //Toast.makeText(getContext(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                           }
                       });
                   }
               }
           }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        mbtnmapview=rootview.findViewById(R.id.btn_mapview);
        mbtnmapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MapActivity.class);
                startActivity(intent);
            }
        });

        mbtnsignuplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
                boolean Islogin=prefs.getBoolean("Islogin",false);
                if(Islogin)
                {
                    Intent intent2=new Intent(getActivity(),UserDetailActivity.class);
                    startActivity(intent2);
                }
                else
                {

                    Intent intent1=new Intent(getActivity(),RegisterActivity.class);
                    startActivity(intent1);
                }


            }
        });



        return rootview;
    }

}
