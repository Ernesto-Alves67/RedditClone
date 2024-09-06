package com.example.redditclone.fragments.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.redditclone.PopularFragment;
import com.example.redditclone.RecentFragment;
import com.example.redditclone.VideosFragment;
import com.example.redditclone.fragments.HomeFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(); // Fragmento 1
            case 1:
                return new PopularFragment(); // Fragmento 2
            case 2:
                return new VideosFragment(); // Fragmento 3
            case 3:
                return new RecentFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4; // Número de fragmentos definidos em createFragment()
    }
}
