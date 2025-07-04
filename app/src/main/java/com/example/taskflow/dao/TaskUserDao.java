package com.example.taskflow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.TaskUserCrossRef;
import com.example.taskflow.model.TaskWithUsers;
import com.example.taskflow.model.User;
import com.example.taskflow.model.UserWithTasks;

import java.util.List;

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

    @Query("DELETE FROM TaskUserCrossRef WHERE taskId = :taskId")
    void deleteByTaskId(int taskId);

    @Transaction
    @Query("SELECT User.* FROM User " +
            "INNER JOIN TaskUserCrossRef ON User.id = TaskUserCrossRef.userId " +
            "WHERE TaskUserCrossRef.taskId = :taskId")
    List<User> getUsersByTaskId(int taskId);
}

