package com.example.kioskapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment1();
//            case 1: return new Fragment2();
            case 2: return new Fragment3();
            case 3: return new Fragment4();
            case 4: return new Fragment5();
            default: return new Fragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // 총 프래그먼트 수
    }
}