package com.example.taskflow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.HistoryItem;
import com.example.taskflow.model.HistoryItemWithUser;

import java.util.List;

@Dao
public interface HistoryItemDao {

    @Insert
    void insert(HistoryItem item);

    @Query("SELECT * FROM HistoryItem WHERE taskId = :taskId ORDER BY timestamp ASC")
    List<HistoryItem> getHistoryForTask(int taskId);

    @Transaction
    @Query("SELECT * FROM HistoryItem WHERE taskId = :taskId ORDER BY timestamp ASC")
    List<HistoryItemWithUser> getHistoryWithUsersForTask(int taskId);
}
