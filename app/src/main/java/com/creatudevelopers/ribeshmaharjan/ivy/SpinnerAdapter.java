package com.creatudevelopers.ribeshmaharjan.ivy;

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

import com.creatudevelopers.ribeshmaharjan.ivy.model.city;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<city> {
    int groupid;
    Activity context;
    List<city> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int groupid, int id, List<city>
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
        textView.setText(list.get(position).getCity());
        textView.setTextColor(Color.rgb(111,53,148));
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        View v= getView(position,convertView,parent);
        TextView textView=v.findViewById(R.id.spinner_txt_item);
        Typeface typeface=ResourcesCompat.getFont(getContext(),R.font.montserrat_bold2);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.GRAY);
        return v;

    }

}
