package com.example.taskflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.HistoryAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.HistoryItemWithUser;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private AppDatabase db;
    private int taskId;

    public HistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());

        if (getArguments() != null) {
            taskId = getArguments().getInt("TASK_ID", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadHistoryFromDatabase();
    }

    private void loadHistoryFromDatabase() {
        new Thread(() -> {
            List<HistoryItemWithUser> historyList = db.historyItemDao().getHistoryWithUsersForTask(taskId);

            requireActivity().runOnUiThread(() -> {
                adapter = new HistoryAdapter(historyList);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}
