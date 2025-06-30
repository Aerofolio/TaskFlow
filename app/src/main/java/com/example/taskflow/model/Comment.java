
package com.example.taskflow.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Task.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE)
})
public class Comment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int taskId;
    private int userId;

    @NonNull
    private String message;

    @NonNull
    private String timestamp;

    public Comment(int taskId, int userId, @NonNull String message, @NonNull String timestamp) {
        this.taskId = taskId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getUserId() {
        return userId;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }
}
