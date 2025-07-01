package com.example.taskflow.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class HistoryItemWithUser {
    @Embedded
    public HistoryItem historyItem;

    @Relation(parentColumn = "userId", entityColumn = "id")
    public User user;
}
