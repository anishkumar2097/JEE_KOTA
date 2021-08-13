package com.example.jeekota.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeekota.Model;
import com.example.jeekota.R;
import com.example.jeekota.util;
import com.example.jeekota.webviewActivity;

import java.util.List;

public class RecylerAdater extends RecyclerView.Adapter<RecylerAdater.holder> {

    // implements MainActivity.recyclerData
    private final String MATHS = "Maths";
    private final String PHYSICS = "Physics";
    private final String CHEM = "Chemistry";
    public final Context context;
    //  OnRecylerData onRecylerData;
    List<Model> models;
    int id;

    public RecylerAdater(Context context, int id, List<Model> m) {
        this.context = context;
        id = id;
        this.models = m;
        //  MainActivity.initializeListener(this);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rowlayout, parent, false);

        view.getId();
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

    //    if (id == 0 && models.get(position).getSubject().equals(MATHS)) {
            holder.txt.setText(models.get(position).getName());
            holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentJump(models.get(position),v);
                }
            });
       // }
     //   else if (id == 1 && models.get(position).getSubject().equals(PHYSICS)) {
       //     holder.txt.setText(models.get(position).getName());

        //}
       // else if(id == 2 && models.get(position).getSubject().equals(CHEM))
         //   holder.txt.setText(models.get(position).getName());

    }

    private void fragmentJump(Model model,View v) {
        String muri=model.getUrl();
   //     ViewFragment webViewfragment=new ViewFragment();
        Intent i=new Intent(context, webviewActivity.class);
        i.putExtra("muri",muri);
        i.putExtra("pdf_name",model.getName());
        if( util.isInternetAvailable(context)){
            context.startActivity(i);
        }

   //     Bundle bundle=new Bundle();
     //  bundle.putString("muri",muri);
       //webViewfragment.setArguments(bundle);


      //  AppCompatActivity activity = (AppCompatActivity) v.getContext();
        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.pager,webViewfragment).addToBackStack(null).commit();
    }


    @Override
    public int getItemCount() {
        return models.size() ;
    }

  /*  @Override
    public void setData(List<Model> m) {
        this.models=m;
        notifyDataSetChanged();

    }

   */

 //   void setListener(OnRecylerData onRecylerData){
   //     this.onRecylerData=onRecylerData;
//
  //  }


    public class holder extends RecyclerView.ViewHolder{

        TextView txt;
        public holder(@NonNull View itemView) {
            super(itemView);
                    txt=itemView.findViewById(R.id.textView);

        }
    }
}
