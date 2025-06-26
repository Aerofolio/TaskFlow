package com.example.taskflow.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long addTask(Task task);

    @Transaction
    @Query("SELECT * FROM Task")
    List<Task> getTasks();
}
