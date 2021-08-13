package com.example.jeekota.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeekota.Model;
import com.example.jeekota.R;
import com.example.jeekota.adapter.RecylerAdater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MathFragment extends Fragment implements ValueEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView view;

    private final String MATHS = "Maths";

    List<Model> modellist = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference reference;
    private RecylerAdater adapter;

    public MathFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_math, container, false);

        view = v.findViewById(R.id.math_recyler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        view.setLayoutManager(manager);
       view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference().child("PdfDocuments");

        reference.addValueEventListener(this);
        adapter = new RecylerAdater(getActivity(), 0, modellist);
        view.setAdapter(adapter);


        return v;
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

            Model model = postSnapshot.getValue(Model.class);
            if (model.getSubject().equals(MATHS)) {
                modellist.add(model);
            }

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
             error.getDetails();
    }

    @Override
    public void onDestroy() {
        reference.removeEventListener(this);
        super.onDestroy();
    }
}