package com.example.ribeshmaharjan.ivy;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;



import com.example.ribeshmaharjan.ivy.model.School;
import com.example.ribeshmaharjan.ivy.model.SchoolResponse;
import com.example.ribeshmaharjan.ivy.model.city;
import com.example.ribeshmaharjan.ivy.model.cityresponse;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;


import retrofit2.Call;
import retrofit2.Callback;




/**
 * A simple {@link Fragment} subclass.
 */
public class ListingHelpFragment extends Fragment {
    Context mcontext;
    Spinner spinner;
    Button mbtnmapview;
    ImageButton mbtnsignuplogin;
    ProgressBar progressBar;


    private static final String TAG =" Fragment";
    List<city> citieslist=null;
    List<School> schoolslist=null;

    public ListingHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        final View rootview= inflater.inflate(R.layout.fragment_listing_help, container, false);
        spinner=rootview.findViewById(R.id.spinner_listhelper);
        mbtnsignuplogin=rootview.findViewById(R.id.signup_login);
        progressBar=rootview.findViewById(R.id.progressBar_mainlayout);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext()));
        //JSONArray jsonArray = response.getJSONArray("results");
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
                        Log.d(TAG, "Number of movies received: " + citieslist.size());
                        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                        spinner.setAdapter(adapter);

                    }
                    @Override
                    public void onFailure(@NonNull Call<cityresponse> call, @NonNull Throwable t) {
                        Log.e(TAG, t.toString());

                    }
                });

            }
            else
            {
                SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, R.id.spinner_txt_item, citieslist);
                spinner.setAdapter(adapter);
            }

        if(schoolslist==null)
        {

        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // String city = adapterView.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
             //  String city=adapterView.getItemAtPosition(adapterView.getSelectedItemPosition()).toString();
               int city=spinner.getSelectedItemPosition();
               // Toast.makeText(getContext(),Integer.toString(city),Toast.LENGTH_LONG).show();
               if(city==0)
                {
                    Call<SchoolResponse> call1=apiInterface.getschools("Kathmandu");
                    call1.enqueue(new Callback<SchoolResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SchoolResponse> call1, @NonNull retrofit2.Response<SchoolResponse> response) {
                            Toast.makeText(getContext(),"Kathmandu Success",Toast.LENGTH_LONG).show();
                            assert response.body() != null;
                            progressBar.setVisibility(View.GONE);
                            schoolslist=response.body().getResults();
                            SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),schoolslist);
                            recyclerView.setAdapter(recyclerview_adapter);
                        }

                        @Override
                        public void onFailure(@NonNull Call<SchoolResponse> call1, @NonNull Throwable t) {
                            //Toast.makeText(getContext(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                }
                else if (city==1)
                {
                    Call<SchoolResponse> call1=apiInterface.getschools("Kirtipur");
                    call1.enqueue(new Callback<SchoolResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SchoolResponse> call1, @NonNull retrofit2.Response<SchoolResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            assert response.body() != null;
                            recyclerView.removeAllViews();
                            schoolslist=response.body().getResults();
                            SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),schoolslist);
                            recyclerView.setAdapter(recyclerview_adapter);
                        }

                        @Override
                        public void onFailure(@NonNull Call<SchoolResponse> call1, @NonNull Throwable t) {
                            Toast.makeText(getContext(),"Kathmandu Error",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else
                {
                    Call<SchoolResponse> call2=apiInterface.getschools("Lalitpur");
                    call2.enqueue(new Callback<SchoolResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SchoolResponse> call2, @NonNull retrofit2.Response<SchoolResponse> response2) {
                            progressBar.setVisibility(View.GONE);
                            assert response2.body() != null;
                            int statuscode =response2.code();
                            Toast.makeText(getActivity(),"SUCCESS",Toast.LENGTH_LONG).show();
                            schoolslist=response2.body().getResults();
                            SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),schoolslist);
                            recyclerView.setAdapter(recyclerview_adapter);
                        }

                        @Override
                        public void onFailure(@NonNull Call<SchoolResponse> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        /*ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("Delhi"));
        list.add(new ItemData("Hauz Khas"));
        list.add(new ItemData("Lajpat Nagar"));
        list.add(new ItemData("Greater Kailash"));
        list.add(new ItemData("Rangpuri"));
        list.add(new ItemData("Noida"));
        SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);*/
        /*SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);*/
        mbtnmapview=rootview.findViewById(R.id.btn_mapview);
        mbtnmapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MapActivity.class);
                startActivity(intent);
            }
        });

        /*final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),ImageAssets.getAll());
        recyclerView.setAdapter(recyclerview_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));*/
        mbtnsignuplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }

}
