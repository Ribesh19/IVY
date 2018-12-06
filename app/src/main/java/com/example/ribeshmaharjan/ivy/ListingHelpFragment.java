package com.example.ribeshmaharjan.ivy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



/**
 * A simple {@link Fragment} subclass.
 */
public class ListingHelpFragment extends Fragment {
    Context mcontext;
    Spinner spinner;
    Button mbtnmapview;
    ImageButton mbtnsignuplogin;

    public ListingHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_listing_help, container, false);
        spinner=rootview.findViewById(R.id.spinner_listhelper);
        mbtnsignuplogin=rootview.findViewById(R.id.signup_login);

        //String [] location={"Delhi","Hauz Khas", "Lajpat Nagar","Greater Kailash","Rangpuri", "Noida"};
        //ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,location);
        //spinner.setAdapter(adapter);
        ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("Delhi"));
        list.add(new ItemData("Hauz Khas"));
        list.add(new ItemData("Lajpat Nagar"));
        list.add(new ItemData("Greater Kailash"));
        list.add(new ItemData("Rangpuri"));
        list.add(new ItemData("Noida"));
        SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.spinner_layout,R.id.spinner_txt_item,list);
        spinner.setAdapter(adapter);

        mbtnmapview=rootview.findViewById(R.id.btn_mapview);
        mbtnmapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MapActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView =rootview.findViewById(R.id.recyclerview);
        final SchoollistAdapter recyclerview_adapter = new SchoollistAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),ImageAssets.getAll());
        recyclerView.setAdapter(recyclerview_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
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
