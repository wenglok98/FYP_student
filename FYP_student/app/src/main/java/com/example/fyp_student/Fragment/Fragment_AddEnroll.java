package com.example.fyp_student.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp_student.Adapter.SubjectListAdapter;
import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.Class.Subject;
import com.example.fyp_student.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_AddEnroll extends Fragment {
    FirebaseAuth fAuth;
    View view;
    ListView listView;
    FloatingActionButton addbt;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    ArrayList<Subject> subjectList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addenroll, container, false);
        listView = view.findViewById(R.id.subjectListView);
        addbt = view.findViewById(R.id.addEnroll);
        addbt.setVisibility(View.GONE);

        fAuth = FirebaseAuth.getInstance();
        final String UID = fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fStore.collection("Enrollment").document();


        fStore.collection("Subject").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                subjectList.clear();
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String subcode = snapshot.getString("SubjectCode");
                        String subname = snapshot.getString("SubjectName");

                        Subject subject = new Subject(subcode, subname);

                        subjectList.add(subject);
                    }


                    SubjectListAdapter adapter = new SubjectListAdapter(getActivity(), R.layout.adapter_view_addsubject, subjectList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }
        });
        final String useid = fAuth.getCurrentUser().getUid().trim().toString();
        final DocumentReference createNewEnroll = fStore.collection("Enrollment").document("#21");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final Enrollment enr = new Enrollment();
//                enr.setSubjectCode(subjectList.get(i).getSubjectCode());
//                enr.setSubjectName(subjectList.get(i).getSubjectName());
//                enr.setUsid(useid);
//                enr.setEnrollID("321");
                final Integer j = i;
                addbt.setVisibility(View.VISIBLE);
                addbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final String subCode = subjectList.get(j).getSubjectCode();
                        final String subName = subjectList.get(j).getSubjectName();


                        Enrollment ar = new Enrollment(null, subCode, subName, useid, "0");
                        fStore.collection("Enrollment").add(ar);
                        getActivity().getSupportFragmentManager().beginTransaction().remove(Fragment_AddEnroll.this).commit();

                        getFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_ModifySubject()).commit();
                    }
                });

            }
        });


        return view;

    }

}
