package com.example.fyp_student.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp_student.Adapter.EnrollListAdapter;
import com.example.fyp_student.Adapter.ForecastListAdapter;
import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;


public class Fragment_Task extends Fragment {
    View view;
    ListView listView;
    int totalminsforcursub = 0;
    int totalcount = 1;
    int lasttotal = 0;
    int totalavg = 0;
    int compare1 = 0;
    ArrayList<Integer> minuteslist = new ArrayList<>();
    ArrayList<String> subjectCodeforenroll = new ArrayList<>();
    ArrayList<String> fnalresult = new ArrayList<>();
    ArrayList<Integer> subjectresult2 = new ArrayList<>();
    ArrayList<Enrollment> enrollList = new ArrayList<>();

    String UID;
    ArrayList<Enrollment> secondenrollist = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ForecastListAdapter adapter;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task, container, false);
        UID = fAuth.getCurrentUser().getUid();


        listView = view.findViewById(R.id.forecastlistview);
        //Adapter and listview
        adapter = new ForecastListAdapter(getActivity(), R.layout.adapter_view_viewforecast, secondenrollist);
        adapter.notifyDataSetChanged();

        //End of Adapter and listview

        callresult1();




        return view;
    }

    public void callresult1() {
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

                        minuteslist.add(Integer.valueOf(sub));
                        subjectCodeforenroll.add(subcode);


                        Enrollment err = new Enrollment(enrolid, subcode, subname, ussid, sub);
                        enrollList.add(err);


                    }

                }

                forecastresult();

            }

        });


    }

    public void forecastresult() {
        // start of forecasting
        int index = 0;
        while (subjectCodeforenroll.size() > index) {
            String currentsubcode = subjectCodeforenroll.get(index++);
            fstore.collection("Enrollment").whereEqualTo("subjectCode", currentsubcode).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            String allmins = snapshot.getString("visitedminutes");
                            int curmins = Integer.valueOf(allmins);
                            totalminsforcursub = totalminsforcursub + curmins;
                            totalcount++;

                        }

                        int aaa = totalminsforcursub / totalcount;
                        subjectresult2.add(aaa);
                        totalminsforcursub = 0;
                        totalcount = 1;

                        if (subjectresult2.size() == minuteslist.size()) {
                            calculateresult();
                        }
                    }

                }


            });

        }
        //  end of forecasting


    }

    public void calculateresult() {


//        for (int cursubnumb = 0; cursubnumb < minuteslist.size() ; cursubnumb++) {
        int cursubnumb = 0;
        while (enrollList.size() > cursubnumb)
        {


            if (minuteslist.get(cursubnumb) > subjectresult2.get(cursubnumb)) {
                String sbname2 = enrollList.get(cursubnumb).getSubjectName();
                String erid2 = enrollList.get(cursubnumb).getEnrollID();
                String sbcode2 = enrollList.get(cursubnumb).getSubjectCode();
                String usid2 = enrollList.get(cursubnumb).getUsid();
                String vis2 = enrollList.get(cursubnumb).getVisitedminutes();

                Enrollment er2 = new Enrollment(erid2, sbcode2, sbname2, usid2, vis2, "A");
                secondenrollist.add(er2);

            }
            else if (minuteslist.get(cursubnumb) < subjectresult2.get(cursubnumb)) {
                String sbname2 = enrollList.get(cursubnumb).getSubjectName();
                String erid2 = enrollList.get(cursubnumb).getEnrollID();
                String sbcode2 = enrollList.get(cursubnumb).getSubjectCode();
                String usid2 = enrollList.get(cursubnumb).getUsid();
                String vis2 = enrollList.get(cursubnumb).getVisitedminutes();

                Enrollment er2 = new Enrollment(erid2, sbcode2, sbname2, usid2, vis2, "C");
                secondenrollist.add(er2);
            }
            else if (minuteslist.get(cursubnumb) == subjectresult2.get(cursubnumb)) {
                String sbname2 = enrollList.get(cursubnumb).getSubjectName();
                String erid2 = enrollList.get(cursubnumb).getEnrollID();
                String sbcode2 = enrollList.get(cursubnumb).getSubjectCode();
                String usid2 = enrollList.get(cursubnumb).getUsid();
                String vis2 = enrollList.get(cursubnumb).getVisitedminutes();

                Enrollment er2 = new Enrollment(erid2, sbcode2, sbname2, usid2, vis2, "B");
                secondenrollist.add(er2);
            }
            cursubnumb++;
        }


        listView.setAdapter(adapter);

    }

}
