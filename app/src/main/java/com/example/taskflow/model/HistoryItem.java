package com.example.taskflow.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.io.Serializable;

@Entity
public class HistoryItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long taskId;
    private int userId;

    @NonNull
    private String action;

    @NonNull
    private String timestamp;

    public HistoryItem(long taskId, int userId, @NonNull String action, @NonNull String timestamp) {
        this.taskId = taskId;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
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
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }
}
