package com.example.taskflow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taskflow.dao.CommentDao;
import com.example.taskflow.dao.TaskDao;
import com.example.taskflow.dao.UserDao;
import com.example.taskflow.model.Comment;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;

@Database(entities = { User.class, Task.class, Comment.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract CommentDao commentDao();
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "task_flow_db"
                    )
                    .fallbackToDestructiveMigration(true)
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
