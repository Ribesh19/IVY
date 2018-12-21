package com.example.ribeshmaharjan.ivy;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ribeshmaharjan.ivy.model.FavouriteResponse;
import com.example.ribeshmaharjan.ivy.model.FavouriteResults;
import com.example.ribeshmaharjan.ivy.rest.ApiClient;
import com.example.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    List<FavouriteResults> favouriteResults;
    ProgressBar progressBar_favourite;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_favourite, container, false);
        progressBar_favourite=rootview.findViewById(R.id.progressBar_favourite);
        progressBar_favourite.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview_favourite);

        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        Integer id  = prefs.getInt("name", 1);
        Call<FavouriteResponse> favouriteResponseCall = apiInterface.getfavourite(id);
        favouriteResponseCall.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavouriteResponse> call, @NonNull Response<FavouriteResponse> response) {
                progressBar_favourite.setVisibility(View.GONE);
                //assert response.body() != null;
                favouriteResults=response.body().getResults();
                final FavouriteAdapter recyclerview_adapter1 = new FavouriteAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),favouriteResults);
                recyclerView.setAdapter(recyclerview_adapter1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

            }

            @Override
            public void onFailure(@NonNull Call<FavouriteResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.getCause().getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        return rootview;
    }

}
