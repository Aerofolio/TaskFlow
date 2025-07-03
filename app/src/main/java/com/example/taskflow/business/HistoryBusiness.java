package com.example.taskflow.business;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.HistoryItem;
import com.example.taskflow.utils.PrefsUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryBusiness {

    private final AppDatabase db;
    private final SharedPreferences prefs;

    public HistoryBusiness(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.prefs = context.getSharedPreferences(PrefsUtils.APP_PREFS, Context.MODE_PRIVATE);
    }

    public void registerHistory(long taskId, String action) {

        int userId = prefs.getInt(PrefsUtils.USER_ID, -1);

        HistoryItem historyItem = new HistoryItem(taskId, userId, action, new Timestamp(System.currentTimeMillis()));

        new Thread(() -> {
            db.historyItemDao().insert(historyItem);
        }).start();
    }
}
