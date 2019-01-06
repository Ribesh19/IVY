package com.creatudevelopers.ribeshmaharjan.ivy;



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
import android.widget.TextView;


import com.creatudevelopers.ribeshmaharjan.ivy.model.FavouriteResponse;
import com.creatudevelopers.ribeshmaharjan.ivy.model.FavouriteResults;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiClient;
import com.creatudevelopers.ribeshmaharjan.ivy.rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;



/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

   static List<FavouriteResults> favouriteResults;
    ProgressBar progressBar_favourite;
    TextView mfavourtie_message;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_favourite, container, false);
        progressBar_favourite=rootview.findViewById(R.id.progressBar_favourite);
        mfavourtie_message=rootview.findViewById(R.id.favourtie_message);
        progressBar_favourite.setVisibility(View.VISIBLE);
        mfavourtie_message.setVisibility(View.GONE);
        final RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview_favourite);
        SharedPreferences prefs = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        final Integer id = prefs.getInt("id", 0);
        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
           // Call<FavouriteResponse> favouriteResponseCall = apiInterface.getfavourite(Integer.getInteger(id));
        if(getActivity()!=null && isAdded()) {
                Call<FavouriteResponse> favouriteResponseCall = apiInterface.getfavourite(id);
                favouriteResponseCall.enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<FavouriteResponse> call, @NonNull Response<FavouriteResponse> response) {
                        progressBar_favourite.setVisibility(GONE);
                        favouriteResults = response.body().getResults();
                            HomeActivity.favourite_school_list = new int[FavouriteFragment.favouriteResults.size()];
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                HomeActivity.favourite_school_list[i] = Integer.valueOf(response.body().getResults().get(i).getSchoolId());

                            }

                        if(!favouriteResults.isEmpty()) {
                                if(getContext()!=null) {
                                    final FavouriteAdapter recyclerview_adapter1 = new FavouriteAdapter(Objects.requireNonNull(getContext()), favouriteResults);
                                    recyclerView.setAdapter(recyclerview_adapter1);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                }
                        }
                        else
                        {
                            mfavourtie_message.setVisibility(View.VISIBLE);
                        }

                    }
                    @Override
                    public void onFailure(@NonNull Call<FavouriteResponse> call, @NonNull Throwable t) {

                    }
                });

        }
        else
        {
            progressBar_favourite.setVisibility(GONE);
            final FavouriteAdapter recyclerview_adapter1 = new FavouriteAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), favouriteResults);
            recyclerView.setAdapter(recyclerview_adapter1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        }

        return rootview;
    }

}
