package com.example.fuelex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

//Resource : https://stackoverflow.com/questions/7828892/multiple-arrays-in-a-listview/7829661#7829661

public class FuelListCusAdapter extends BaseAdapter {
    private String[] arr1, arr3;
    private String[] arr2;
    private LayoutInflater inflater;

    public FuelListCusAdapter(Context context, String[] a1, String[] a2, String[] a3){
        arr1 = a1;
        arr2 = a2;
        arr3 = a3;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arr1.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView==null) {
            currentView = inflater.inflate(R.layout.fuel_list, parent, false);
        }

        TextView tView = (TextView)currentView.findViewById(R.id.id_FuelNameInput);
        tView.setText(arr1[position]);

        tView = (TextView)currentView.findViewById(R.id.id_QtyInput);
        tView.setText(arr2[position]);

        tView = (TextView)currentView.findViewById(R.id.id_ArrTimeInput);
        tView.setText(arr3[position]);

        return currentView;
    }


}
