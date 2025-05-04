package com.example.taskflow.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskflow.CommentsFragment;
import com.example.taskflow.HistoryFragment;
import com.example.taskflow.TaskDetailFragment;

public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TaskDetailFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new CommentsFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
