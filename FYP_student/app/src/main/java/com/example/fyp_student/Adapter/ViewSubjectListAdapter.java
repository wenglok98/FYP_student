package com.example.fyp_student.Adapter;

import android.content.Context;
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

public class ViewSubjectListAdapter extends ArrayAdapter<Enrollment> {

    private static final String TAG = "EnrollListAdapter";

    private Context mContext;
    int mResource;

    public ViewSubjectListAdapter(Context context, int resource, ArrayList<Enrollment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String sbc = getItem(position).getSubjectCode();
        String sbn = getItem(position).getSubjectName();

        Enrollment enroll = new Enrollment(sbc,sbn);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView sbcode = (TextView)convertView.findViewById(R.id.viewsubjectathome);

        sbcode.setText(sbc + " -  " + sbn);


        return convertView;
    }
}