package com.example.taskflow.model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        primaryKeys = {"taskId", "userId"},
        indices = {
                @Index(value = {"taskId"}),
                @Index(value = {"userId"})
        }
)
public class TaskUserCrossRef {
    public int taskId;
    public int userId;

    public TaskUserCrossRef(int taskId, int userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}