package com.example.taskflow.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.Comment;
import com.example.taskflow.model.CommentWithUser;

import java.util.List;

@Dao
public interface CommentDao {

    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM Comment WHERE taskId = :taskId ORDER BY timestamp ASC")
    List<Comment> getCommentsForTask(int taskId);

    @Transaction
    @Query("SELECT * FROM Comment WHERE taskId = :taskId ORDER BY timestamp DESC")
    List<CommentWithUser> getCommentsWithUsersForTask(int taskId);
}
