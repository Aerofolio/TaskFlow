package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.HistoryItem;
import com.example.taskflow.model.HistoryItemWithUser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItemWithUser> historyList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    public HistoryAdapter(List<HistoryItemWithUser> historyList) {
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryItemWithUser item = historyList.get(position);

        holder.userTextView.setText(item.user.getName());
        holder.actionTextView.setText(item.historyItem.getAction());
        holder.timestampTextView.setText(sdf.format(item.historyItem.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView, actionTextView, timestampTextView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.textUser);
            actionTextView = itemView.findViewById(R.id.textAction);
            timestampTextView = itemView.findViewById(R.id.textTimestamp);
        }
    }
}
