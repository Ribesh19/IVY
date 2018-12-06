package com.example.ribeshmaharjan.ivy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView =rootview.findViewById(R.id.recentsearch_recyclerview);
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
