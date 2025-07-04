package com.example.taskflow.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long addTask(Task task);

    @Transaction
    @Query("SELECT * FROM Task")
    List<Task> getTasks();

    @Transaction
    @Query("SELECT * FROM Task WHERE id = :taskId")
    Task getTaskById(int taskId);

    @Transaction
    @Query("SELECT Task.* FROM Task INNER JOIN TaskUserCrossRef ON Task.id = TaskUserCrossRef.taskId WHERE TaskUserCrossRef.userId = :userId AND TASK.status < 2 ORDER BY Task.priority DESC, Task.deadline ASC")
    List<Task> getTasksAssignedToUser(int userId);

    @Transaction
    @Query("SELECT Task.* FROM Task INNER JOIN TaskUserCrossRef ON Task.id = TaskUserCrossRef.taskId WHERE TaskUserCrossRef.userId = :userId AND Task.deadline == :date ORDER BY Task.priority DESC")
    List<Task> getTasksAssignedToUserOnDate(int userId, Timestamp date);

    @Query("UPDATE Task SET status = :status WHERE id = :taskId")
    void updateStatus(int taskId, int status);

    @Query("UPDATE Task SET status = :status, completedAt = :completedAt WHERE id = :taskId")
    void completeTask(int taskId, int status, Timestamp completedAt);

    @Update
    void updateTask(Task task);
}
