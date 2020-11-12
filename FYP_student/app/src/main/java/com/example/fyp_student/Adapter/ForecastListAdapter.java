package com.example.fyp_student.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.R;

import java.util.ArrayList;

public class ForecastListAdapter extends ArrayAdapter<Enrollment> {

    private static final String TAG = "EnrollListAdapter";

    private Context mContext;
    int mResource;

    public ForecastListAdapter(Context context, int resource, ArrayList<Enrollment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sbc = getItem(position).getSubjectCode();
        String sbn = getItem(position).getSubjectName();
        String sbr = getItem(position).getResultgrade();
        Enrollment enroll = new Enrollment(sbc, sbn);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView sbresult = (TextView) convertView.findViewById(R.id.forecastresult);
        TextView sbcode = (TextView) convertView.findViewById(R.id.forecastsubcode);
        TextView sbname = (TextView) convertView.findViewById(R.id.forecastsubname);
        if (sbr == "B") {
            sbresult.setTextColor(Color.parseColor("#f56464"));
        }
        else if (sbr == "A")
        {
            sbresult.setTextColor(Color.parseColor("#7df55f"));
        }



        sbcode.setText(sbc);
        sbname.setText(sbn);
        sbresult.setText(sbr);


        return convertView;
    }
}