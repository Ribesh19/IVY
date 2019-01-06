package com.creatudevelopers.ribeshmaharjan.ivy;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creatudevelopers.ribeshmaharjan.ivy.model.Featured;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FeaturedResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.School;
import com.creatudevelopers.ribeshmaharjan.ivy.model.SchoolResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView msearchView_school_name;
    SearchView msearchView_location;
    List<Featured> featuredList=null;
    List<School> schoolslist=null;
    TextView mrecentsearch;




    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        final View rootview= inflater.inflate(R.layout.fragment_search, container, false);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recentsearch_recyclerview);
        final RecyclerView recyclerView1 =rootview.findViewById(R.id.recommendation_recyclerview);
        mrecentsearch=rootview.findViewById(R.id.recentsearch);
        mrecentsearch.setVisibility(View.GONE);
        msearchView_location=rootview.findViewById(R.id.search_location_card);
        msearchView_school_name=rootview.findViewById(R.id.search_school_card);

        msearchView_location.setIconified(false);
        msearchView_school_name.setIconified(false);//was false
        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);

        if(getActivity()!=null && isAdded()) {
            Call<FeaturedResponse> featuredResponseCall = apiInterface.getfeatured();
            featuredResponseCall.enqueue(new Callback<FeaturedResponse>() {
                @Override
                public void onResponse(@NonNull Call<FeaturedResponse> call, @NonNull Response<FeaturedResponse> response) {
                    assert response.body() != null;
                    featuredList = response.body().getResults();
                    if(getContext()!=null) {
                        final RecommendationAdapter recyclerview_adapter1 = new RecommendationAdapter(getContext(), featuredList);

                        recyclerView1.setAdapter(recyclerview_adapter1);
                        recyclerView1.setNestedScrollingEnabled(false);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    }


                }

                @Override
                public void onFailure(@NonNull Call<FeaturedResponse> call, @NonNull Throwable t) {

                }
            });
        }

         msearchView_school_name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                // Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                 Call<SchoolResponse> search_schoolresposne=apiInterface.getsearchresult(s);
                 search_schoolresposne.enqueue(new Callback<SchoolResponse>() {
                     @Override
                     public void onResponse(@NonNull Call<SchoolResponse> call, @NonNull Response<SchoolResponse> response) {
                         mrecentsearch.setVisibility(View.VISIBLE);
                         recyclerView.setVisibility(View.VISIBLE);
                         mrecentsearch.setText(R.string.search_result);
                         assert response.body() != null;
                         int statuscode=response.code();
                         if(statuscode==200) {
                             schoolslist = response.body().getResults();
                             RecentSearchAdapter recyclerview_adapter = new RecentSearchAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), schoolslist);
                             recyclerView.setAdapter(recyclerview_adapter);
                             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                         }
                         else if (statuscode==400)
                         {
                            // recyclerView.setVisibility(View.GONE);
                             mrecentsearch.setText("Search Result Not Found");
                             recyclerView.setVisibility(View.INVISIBLE);
                         }
                     }

                     @Override
                     public void onFailure(@NonNull Call<SchoolResponse> call, @NonNull Throwable t) {

                     }
                 });

                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {
                 return false;
             }
         });
         msearchView_location.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                 Call<SchoolResponse> search_schoolresposne=apiInterface.getsearchresult(s);
                 search_schoolresposne.enqueue(new Callback<SchoolResponse>() {
                     @Override
                     public void onResponse(@NonNull Call<SchoolResponse> call, @NonNull Response<SchoolResponse> response) {
                         assert response.body() != null;
                         mrecentsearch.setVisibility(View.VISIBLE);
                         recyclerView.setVisibility(View.VISIBLE);
                         mrecentsearch.setText(R.string.search_result);
                         int statuscode=response.code();
                         if(statuscode==200) {
                             schoolslist = response.body().getResults();

                             RecentSearchAdapter recyclerview_adapter = new RecentSearchAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), schoolslist);
                             recyclerView.setAdapter(recyclerview_adapter);
                             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                         }
                         else if (statuscode==400)
                         {
                             //recyclerView.setVisibility(View.GONE);
                             mrecentsearch.setText("Search Result Not Found");
                            // recyclerView.removeAllViews();
                             recyclerView.setVisibility(View.INVISIBLE);
                         }
                     }

                     @Override
                     public void onFailure(@NonNull Call<SchoolResponse> call, @NonNull Throwable t) {

                     }
                 });
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {
                 return false;
             }
         });



        return rootview;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}


