package com.mintosoft.rubelgroup;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {
  
    private final Activity context;
    private final String[] maintitle;  
    private final String[] subtitle;
  
    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle) {
        super(context, R.layout.details, maintitle);
        // TODO Auto-generated constructor stub  
  
        this.context=context;  
        this.maintitle=maintitle;  
        this.subtitle=subtitle;
  
    }  
  
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.details, null,true);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(Color.parseColor("#e0f2f1"));
        } else {
            rowView.setBackgroundColor(Color.parseColor("#b2dfdb"));
        }
        TextView titleText = (TextView) rowView.findViewById(R.id.name);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.sub);
  
        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);  
  
        return rowView;  
  
    };  
}  