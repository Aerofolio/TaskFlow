package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.HistoryItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> items;

    public HistoryAdapter(List<HistoryItem> items) {
        this.items = items;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textUser;
        TextView textAction;
        TextView textTimestamp;

        public HistoryViewHolder(View view) {
            super(view);
            textUser = view.findViewById(R.id.textUser);
            textAction = view.findViewById(R.id.textAction);
            textTimestamp = view.findViewById(R.id.textTimestamp);
        }
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryItem item = items.get(position);
        holder.textUser.setText("Usuário: " + item.getUser());
        holder.textAction.setText("Ação: " + item.getAction());
        holder.textTimestamp.setText("Data: " + item.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}