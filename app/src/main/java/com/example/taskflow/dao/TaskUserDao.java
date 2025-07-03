package com.example.taskflow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.TaskUserCrossRef;
import com.example.taskflow.model.TaskWithUsers;
import com.example.taskflow.model.UserWithTasks;

@Dao
public interface TaskUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTaskUserCrossRef(TaskUserCrossRef crossRef);

    @Transaction
    @Query("SELECT * FROM Task WHERE id = :taskId")
    TaskWithUsers getTaskWithUsers(int taskId);

    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    UserWithTasks getUserWithTasks(int userId);
}
