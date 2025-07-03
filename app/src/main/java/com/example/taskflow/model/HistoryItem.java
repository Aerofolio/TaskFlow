package com.example.taskflow.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class HistoryItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long taskId;
    private int userId;

    @NonNull
    private String action;

    @NonNull
    private Timestamp createdAt;

    public HistoryItem(long taskId, int userId, @NonNull String action, @NonNull Timestamp createdAt) {
        this.taskId = taskId;
        this.userId = userId;
        this.action = action;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getAction() {
        return action;
    }

    public void setAction(@NonNull String action) {
        this.action = action;
    }

    @NonNull
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
