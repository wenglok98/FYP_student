package com.example.fyp_student.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fyp_student.Activity.PDFView;
import com.example.fyp_student.Activity.Register;
import com.example.fyp_student.Adapter.MaterialListAdapter;
import com.example.fyp_student.Adapter.SubjectListAdapter;
import com.example.fyp_student.Class.Material;
import com.example.fyp_student.Class.Subject;
import com.example.fyp_student.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Fragment_SubjectDetail extends Fragment {


    public Fragment_SubjectDetail() {
    }

    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    View view;
    TextView tx1, tx2;
    String idoffirst, totalmins, viewtime;
    ListView listView;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    ArrayList<Material> materiallist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subjectdetail, container, false);
        Bundle bundle = this.getArguments();
        final String data = bundle.getString("key");
        final String enrolluid = bundle.getString("enrolluid");
        final String totaltime = bundle.getString("totalminutes");
        final String eid = bundle.getString("eid");
        final MaterialListAdapter adapter = new MaterialListAdapter(getActivity(), R.layout.adapter_view_material, materiallist);
        adapter.notifyDataSetChanged();
        listView = view.findViewById(R.id.view_material_list);
        tx1 = view.findViewById(R.id.subcodee);
        tx2 = view.findViewById(R.id.totalspent);
        tx1.setText(data);

        fstore.collection("Enrollment").document(eid).addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    viewtime = documentSnapshot.getString("visitedminutes");
                    tx2.setText("Total Minutes Spent " + viewtime);
                    String sfasdfsdf = viewtime;
                    String fdsfd = sfasdfsdf;
                }
            }
        });


//        tx1.setText(data);

//        DocumentReference documentReference = fstore.collection("Subject").document().collection("Material")

        fstore.collection("Subject").whereEqualTo("SubjectCode", data)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null)
                        {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
//                            tx1.setText(snapshot.getId());
                            idoffirst = snapshot.getId();
                            totalmins = snapshot.getString("visitedminutes");
                            fstore.collection("Subject").document(idoffirst).collection("Material")
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                            if (queryDocumentSnapshots != null) {
                                                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {


                                                    Material mt = new Material(snapshot.getString("fileName"));
                                                    materiallist.add(mt);

                                                }
                                            }
                                            listView.setAdapter(adapter);
                                        }

                                    });

                        }
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                materiallist.get(i).getFileName();

                Intent intent = new Intent(getActivity(), PDFView.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", materiallist.get(i).getFileName());
                bundle.putString("code", data);
                bundle.putString("enrolluid", enrolluid);
                intent.putExtras(bundle);
//                intent.putExtra("name",materiallist.get(i).getFileName());
//                intent.putExtra("code",data);
                startActivity(intent);


                //Start of using Fragment
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("name",materiallist.get(i).getFileName());
//                bundle.putString("code",data);
//                Fragment_ViewMaterial fr = new Fragment_ViewMaterial();
//                fr.setArguments(bundle);
//
//                fragmentTransaction.replace(R.id.fragment,fr);
//                fragmentTransaction.commit();
                //End of using fragment
            }
        });


        //Start of subcollection
//        fstore.collection("Subject").document("EDqsU3oasqmWNNevD0U8").collection("Material").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//                for (DocumentSnapshot snapshot : queryDocumentSnapshots)
//                {
//                    String ab = snapshot.getString("fileName");
//                    tx1.append(ab);
//
//                }
//            }
//        });
        //End of subcollection
//


//        ds.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//
//                tx1.setText(documentSnapshot.getString("fileName"));
//            }
//        });


        return view;
    }

}
