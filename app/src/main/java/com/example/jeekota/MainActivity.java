package com.example.jeekota;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jeekota.adapter.FragmentAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {



    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private final String TAG="MainActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
          viewPager=findViewById(R.id.pager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
          viewPager.setAdapter(fragmentAdapter);

           tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                       if(position==0){
                        tab.setText("Math");}
                    else if(position==1){
                        tab.setText("Physics");}
                    else {
                        tab.setText("Chemistry");
                    }


            }
        }).attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       int id= item.getItemId();

       if(id==R.id.Admin){
           startActivity(new Intent(this, AdminActivity.class));
       }
       if(id==R.id.chat){

                    Snackbar.make(getCurrentFocus(), "Group Chat feature will be added soon.Notes and Pdfs can be shared with each other.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

       }
        return super.onOptionsItemSelected(item);
    }





}