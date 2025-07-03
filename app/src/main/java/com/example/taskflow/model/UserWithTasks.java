package com.example.taskflow.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithTasks {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = TaskUserCrossRef.class,
                    parentColumn = "userId",
                    entityColumn = "taskId"
            )
    )
    public List<Task> tasks;
}