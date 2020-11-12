package com.example.fyp_student.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fyp_student.Activity.PDFView;
import com.example.fyp_student.Adapter.EnrollListAdapter;
import com.example.fyp_student.Adapter.ViewSubjectListAdapter;
import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {
    View view;
    AnyChartView anyChartView;
    ArrayList<Enrollment> enrollList = new ArrayList<>();
    ListView listView;
    String totaltimes;
    String enrolluid;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ArrayList<String> subsss = new ArrayList<>();
    ArrayList<Integer> timesss = new ArrayList<>();
    String[] Subject = {"Chinese", "English", "Mathematics"};
    int[] Time_Spent = {12, 19, 34};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.viewSubject);

        String UID = fAuth.getCurrentUser().getUid();

        final ViewSubjectListAdapter adapter = new ViewSubjectListAdapter(getActivity(), R.layout.adapter_view_viewsubject, enrollList);
        adapter.notifyDataSetChanged();

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
                        String totalcur = snapshot.getString("visitedminutes");

                        enrolluid = snapshot.getId();
                        Enrollment err = new Enrollment(enrolid, subcode, subname, ussid, totalcur);
                        subsss.add(subcode);
                        timesss.add(Integer.valueOf(totalcur));
                        enrollList.add(err);
                    }
                }
                listView.setAdapter(adapter);
                anyChartView = view.findViewById(R.id.any_chart_view);
                setupPieChart();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                String abc = enrollList.get(i).getSubjectCode();
                totaltimes = enrollList.get(i).getVisitedminutes();
                String eeid = enrollList.get(i).getEnrollID();
                bundle.putString("key", abc);
                bundle.putString("enrolluid", enrollList.get(i).getEnrollID());
                bundle.putString("totalminutes", totaltimes);
                bundle.putString("eid", eeid);
                Fragment_SubjectDetail frag = new Fragment_SubjectDetail();
                frag.setArguments(bundle);


                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
            }
        });


        return view;
    }


    public void setupPieChart() {

        Pie pie = AnyChart.pie();
        pie.background().fill("#2e2e2e");

        List<DataEntry> dataEntryList = new ArrayList<>();

        for (int i = 0; i < enrollList.size(); i++) {
            dataEntryList.add(new ValueDataEntry(subsss.get(i), timesss.get(i)));
        }
        pie.data(dataEntryList);

        anyChartView.setChart(pie);


    }

}
