package com.example.jeekota.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class ChemFragment extends Fragment implements ValueEventListener
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   private RecyclerView view;
   private View v;
   private RecylerAdater adapter;


   private List<Model> modellist = new ArrayList<>();

    private DatabaseReference reference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private final String CHEM = "Chemistry";
    public ChemFragment() {
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


        View v= inflater.inflate(R.layout.fragment_chem, container, false);

        view=v.findViewById(R.id.chem_recyler_view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        view.setLayoutManager(manager);
        view.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));


//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        reference = FirebaseDatabase.getInstance().getReference().child("PdfDocuments");

        reference.addValueEventListener(this);
        adapter=new RecylerAdater(getActivity(),2,modellist);
        view.setAdapter(adapter);


          return v;
    }

    @Override
    public void onResume() {
 //       context=getContext();

        super.onResume();
    }



    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
            Model model = postSnapshot.getValue(Model.class);
            if(model.getSubject().equals(CHEM)){
            modellist.add(model);}

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onDestroy() {
        reference.removeEventListener(this);
        super.onDestroy();
    }
}