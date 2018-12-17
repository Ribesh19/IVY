package com.example.ribeshmaharjan.ivy;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_favourite, container, false);
        RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview_favourite);
        final FavouriteAdapter recyclerview_adapter1 = new FavouriteAdapter(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView.setAdapter(recyclerview_adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return rootview;
    }

}
