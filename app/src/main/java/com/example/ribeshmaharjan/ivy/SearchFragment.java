package com.example.ribeshmaharjan.ivy;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.ribeshmaharjan.ivy.model.Featured;
import com.example.ribeshmaharjan.ivy.model.FeaturedResponse;
import com.example.ribeshmaharjan.ivy.model.School;
import com.example.ribeshmaharjan.ivy.model.SchoolResponse;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView msearchView_school_name;
    SearchView msearchView_location;
    List<Featured> featuredList=null;
    List<School> schoolslist=null;




    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_search, container, false);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recentsearch_recyclerview);
        final RecyclerView recyclerView1 =rootview.findViewById(R.id.recommendation_recyclerview);
        msearchView_location=rootview.findViewById(R.id.search_location);
        msearchView_school_name=rootview.findViewById(R.id.search_school);
       msearchView_location.setIconified(false);
        msearchView_school_name.setIconified(false);
        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);


        Call<FeaturedResponse> featuredResponseCall = apiInterface.getfeatured();
        featuredResponseCall.enqueue(new Callback<FeaturedResponse>() {
            @Override
            public void onResponse(@NonNull Call<FeaturedResponse> call, @NonNull Response<FeaturedResponse> response) {
                assert response.body() != null;
                featuredList=response.body().getResults();

                final RecommendationAdapter recyclerview_adapter1 = new RecommendationAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),featuredList);
                recyclerView1.setAdapter(recyclerview_adapter1);
                recyclerView1.setNestedScrollingEnabled(false);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(@NonNull Call<FeaturedResponse> call, @NonNull Throwable t) {

            }
        });

         msearchView_school_name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                // Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                 Call<SchoolResponse> search_schoolresposne=apiInterface.getsearchresult(s);
                 search_schoolresposne.enqueue(new Callback<SchoolResponse>() {
                     @Override
                     public void onResponse(@NonNull Call<SchoolResponse> call, @NonNull Response<SchoolResponse> response) {
                         assert response.body() != null;
                         int statuscode=response.code();
                         if(statuscode==200) {
                             schoolslist = response.body().getResults();

                             RecentSearchAdapter recyclerview_adapter = new RecentSearchAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), schoolslist);
                             recyclerView.setAdapter(recyclerview_adapter);
                             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
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
                         int statuscode=response.code();
                         if(statuscode==200) {
                             schoolslist = response.body().getResults();

                             RecentSearchAdapter recyclerview_adapter = new RecentSearchAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), schoolslist);
                             recyclerView.setAdapter(recyclerview_adapter);
                             recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
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


}


