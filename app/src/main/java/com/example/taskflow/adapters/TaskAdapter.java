package com.example.taskflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.TaskDetailsActivity;
import com.example.taskflow.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDescription;
        TextView textDeadline;
        CardView cardView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            textDeadline = itemView.findViewById(R.id.textDeadline);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textTitle.setText(task.getTitle());
        holder.textDescription.setText(task.getDescription());
        holder.textDeadline.setText("Prazo: " + task.getDeadline());

        holder.cardView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
