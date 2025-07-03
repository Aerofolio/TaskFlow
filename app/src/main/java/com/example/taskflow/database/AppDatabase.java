package com.example.taskflow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taskflow.dao.CommentDao;
import com.example.taskflow.dao.HistoryItemDao;
import com.example.taskflow.dao.TaskDao;
import com.example.taskflow.dao.TaskUserDao;
import com.example.taskflow.dao.UserDao;
import com.example.taskflow.model.Comment;
import com.example.taskflow.model.HistoryItem;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskUserCrossRef;
import com.example.taskflow.model.User;

@Database(entities = { User.class, Task.class, Comment.class, HistoryItem.class, TaskUserCrossRef.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract CommentDao commentDao();
    public abstract HistoryItemDao historyItemDao();
    public abstract TaskUserDao taskUserDao();
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
