package com.example.ribeshmaharjan.ivy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AgeGroupSpinnerAdapter extends ArrayAdapter<AgeGroup> {

    int groupid;
    Activity context;
    ArrayList<AgeGroup> list;
    LayoutInflater inflater;

    public AgeGroupSpinnerAdapter(Activity context, int groupid, int id, ArrayList<AgeGroup>
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent ){
        @SuppressLint("ViewHolder") View itemView=inflater.inflate(groupid,parent,false);
        // View itemView=super.getView(position,convertView,parent);
        Typeface typeface=ResourcesCompat.getFont(getContext(),R.font.montserrat_regular);
        TextView textView=itemView.findViewById(R.id.spinner_txt_item);
        textView.setTypeface(typeface);
        textView.setText(list.get(position).getAgegroup());
        textView.setTextColor(Color.rgb(0,180,240));
        return itemView;
    }

    public View getDropDownView(int position, View convertView, @NonNull ViewGroup
            parent){
        View v= getView(position,convertView,parent);
        TextView textView=v.findViewById(R.id.spinner_txt_item);
        Typeface typeface=ResourcesCompat.getFont(getContext(),R.font.montserrat_regular);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.GRAY);
        return v;

    }
}
