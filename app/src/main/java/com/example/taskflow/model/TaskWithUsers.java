package com.example.taskflow.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TaskWithUsers {
    @Embedded
    public Task task;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = TaskUserCrossRef.class,
                    parentColumn = "taskId",
                    entityColumn = "userId"
            )
    )
    public List<User> users;
}
