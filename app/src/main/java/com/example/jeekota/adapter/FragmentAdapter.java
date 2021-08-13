package com.example.jeekota.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jeekota.fragment.ChemFragment;
import com.example.jeekota.fragment.MathFragment;
import com.example.jeekota.fragment.PhyFragment;

public class FragmentAdapter extends FragmentStateAdapter {


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MathFragment();
            case 1:
                return new PhyFragment();
            default:
                return new ChemFragment();


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
