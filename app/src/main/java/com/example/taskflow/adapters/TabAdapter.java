package com.example.taskflow.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskflow.CommentsFragment;
import com.example.taskflow.HistoryFragment;
import com.example.taskflow.TaskDetailFragment;

public class TabAdapter extends FragmentStateAdapter {
    private final int taskId;
    public TabAdapter(@NonNull FragmentActivity fragmentActivity, int taskId) {
        super(fragmentActivity);
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("TASK_ID", taskId);
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TaskDetailFragment();
                break;
            case 1:
                fragment = new HistoryFragment();
                break;
            case 2:
                fragment = new CommentsFragment();
                break;
            default:
                throw new IllegalArgumentException("Posição inválida");
        }

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
