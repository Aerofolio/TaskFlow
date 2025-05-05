package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUser, txtMessage, txtTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtTimestamp = itemView.findViewById(R.id.txtTimestamp);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.txtUser.setText(comment.getUser().getName());
        holder.txtMessage.setText(comment.getMessage());
        holder.txtTimestamp.setText(comment.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
