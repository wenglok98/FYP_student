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
import com.example.fyp_student.Class.Material;
import com.example.fyp_student.Class.Subject;
import com.example.fyp_student.R;

import java.util.ArrayList;

public class MaterialListAdapter extends ArrayAdapter<Material> {
    private static final String TAG = "SubjectListAdapter";

    private Context mContext;
    int mResource;
    public MaterialListAdapter(Context context, int resource, ArrayList<Material> objects)
    {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String fn = getItem(position).getFileName();


        Material mt = new Material(fn);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView fnn = (TextView)convertView.findViewById(R.id.filename);

        fnn.setText(mt.getFileName());



        return convertView;
    }
}
