package com.example.fyp_student.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.fyp_student.Adapter.EnrollListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ModifySubject extends Fragment {
    View view;
    FloatingActionButton addSubject, removeSubject;
//    ListView listView;
    SwipeMenuListView listView;
    ArrayList<Enrollment> enrollList = new ArrayList<>();
    ArrayList<Enrollment> enrollList2 = new ArrayList<>();
    TextView tx1;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    LottieAnimationView removebt, addbt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modifysubject, container, false);

        //Declare and link to xml
        listView = view.findViewById(R.id.listview1);
        removebt = view.findViewById(R.id.removebt);
        addbt = view.findViewById(R.id.addbt);
        tx1 = view.findViewById(R.id.tt1);
        addSubject = view.findViewById(R.id.floatadd);
        removeSubject = view.findViewById(R.id.removeButton);
        removeSubject.setVisibility(View.GONE);
        //End of declare and link to xml

        fAuth = FirebaseAuth.getInstance();
        //Assign Current UID from firebase
        String UID = fAuth.getCurrentUser().getUid();
        //Adapter and listview
        final EnrollListAdapter adapter = new EnrollListAdapter(getActivity(), R.layout.adapter_view_enroll, enrollList);
        adapter.notifyDataSetChanged();

        //End of Adapter and listview

//Access to firebase firestore enrollment and filter field usid equal to the login-ed user UID
        fstore.collection("Enrollment").whereEqualTo("usid", UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                enrollList.clear();
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String enrolid = snapshot.getId();
                        String subcode = snapshot.getString("subjectCode");
                        String subname = snapshot.getString("subjectName");
                        String ussid = snapshot.getString("usid");
                        String sub = snapshot.getString("visitedminutes");
                        Enrollment err = new Enrollment(enrolid, subcode, subname, ussid, sub);
                        enrollList.add(err);
                    }
                }
                listView.setAdapter(adapter);
            }
        });

//        db.collection("Enrollment").whereEqualTo("userID", UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                enrollList.clear();
//
//                removeSubject.setVisibility(View.GONE);
//                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
//                    String enid = snapshot.getId();
//                    String sbcode = snapshot.getString("SubjectCode");
//                    String sbname = snapshot.getString("SubjectName");
//                    String usid = snapshot.getString("UserID");
//                    Enrollment er = new Enrollment(enid, sbcode, sbname, usid);
////                    String fin = "Subject Code : " + er.getSubject_code() + "\nSubject Name : " + er.getSubject_name();
//                    enrollList.add(er);
//                }
////                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_selectable_list_item, nameslist); // Useable
////                ArrayAdapter<Enrollment> adapter = new ArrayAdapter<Enrollment>(getActivity(), android.R.layout.simple_selectable_list_item, testing);


        //Listview on click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//              tx1.setText(enrollList.get(i).getSubject_code());
                removebt.setVisibility(View.VISIBLE);
                removebt.playAnimation();
                final String eid2 = enrollList.get(i).getEnrollID(); // the enroll id is from firebase.getuid not enrollid
                removebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fstore.collection("Enrollment").document(eid2).delete();
                        getFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_ModifySubject()).commit();
                    }
                });


            }
        });

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        addbt.playAnimation();
        addbt.loop(true);
        addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addbt.resumeAnimation();
                getActivity().getSupportFragmentManager().beginTransaction().remove(Fragment_ModifySubject.this).commit();
                getFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_AddEnroll()).commit();
            }
        });

        //Start of wipe left menu
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(300);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_baseline_delete_24);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        final String eid3 = enrollList.get(position).getEnrollID(); // the enroll id is from firebase.getuid not enrollid
                        fstore.collection("Enrollment").document(eid3).delete();
                        getFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_ModifySubject()).commit();
                        Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

// set creator
        listView.setMenuCreator(creator);

        return view;
    }
}
