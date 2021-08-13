package com.example.jeekota.fragment;

import android.os.Bundle;
import android.util.Log;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhyFragment extends Fragment implements ValueEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String TAG = "phyfragmnet";
    private final String PHYSICS = "Physics";
    RecyclerView view;
    private DatabaseReference reference;

    List<Model> modellist = new ArrayList<>();
    RecylerAdater adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG_ = "PhyFragment";

    public PhyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhyFragment newInstance(String param1, String param2) {
        PhyFragment fragment = new PhyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: .");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phy, container, false);


        view = v.findViewById(R.id.phy_recyler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        view.setLayoutManager(manager);
        view.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("PdfDocuments");
        //     reference.addChildEventListener(this);
        reference.addValueEventListener(this);
        adapter = new RecylerAdater(getActivity(), 1, modellist);
        view.setAdapter(adapter);
        return view;
    }


    @Override
    public void onDestroy() {
        reference.removeEventListener(this);
        super.onDestroy();
    }







    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

            Model model = postSnapshot.getValue(Model.class);
            if(model.getSubject().equals(PHYSICS)){
            modellist.add(model);}

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}