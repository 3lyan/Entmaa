package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.m3lyan.entmaa.R;

import java.util.ArrayList;

/**
 * Created by mahmoud on 3/23/2018.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context context1;
    private ArrayList<String> data;
    public Resources res;
    LayoutInflater inflater;


    public CustomSpinnerAdapter(Context context, ArrayList<String> objects) {
        super(context,android.R.layout.simple_spinner_dropdown_item,objects);

        context1 = context;
        data = objects;

        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return super.getCount()-1;
    }
}

