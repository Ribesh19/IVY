package com.example.ribeshmaharjan.ivy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView msearchView_school_name;
    SearchView msearchView_location;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView =rootview.findViewById(R.id.recentsearch_recyclerview);
        msearchView_location=rootview.findViewById(R.id.search_location);
        msearchView_school_name=rootview.findViewById(R.id.search_school);
       msearchView_location.setIconified(false);
        msearchView_school_name.setIconified(false);
        final RecentSearchAdapter recyclerview_adapter = new RecentSearchAdapter(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView.setAdapter(recyclerview_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        RecyclerView recyclerView1 =rootview.findViewById(R.id.recommendation_recyclerview);
        final RecommendationAdapter recyclerview_adapter1 = new RecommendationAdapter(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView1.setAdapter(recyclerview_adapter1);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return rootview;
    }


}


