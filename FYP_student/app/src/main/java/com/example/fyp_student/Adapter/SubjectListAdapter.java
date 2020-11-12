package com.example.fyp_student.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.Class.Subject;
import com.example.fyp_student.R;

import java.util.ArrayList;

public class SubjectListAdapter extends ArrayAdapter<Subject> {

    private static final String TAG = "SubjectListAdapter";

    private Context mContext;
    int mResource;
    public SubjectListAdapter(Context context, int resource, ArrayList<Subject> objects)
    {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sbc = getItem(position).getSubjectCode();
        String sbn = getItem(position).getSubjectName();

        Subject subject = new Subject(sbc,sbn);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView sbcode = (TextView)convertView.findViewById(R.id.subjectCode);
        TextView sbname = (TextView)convertView.findViewById(R.id.subjectName);

        sbcode.append(sbc);
        sbname.append(sbn);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in);
        convertView.startAnimation(animation);

        return convertView;
    }
}
