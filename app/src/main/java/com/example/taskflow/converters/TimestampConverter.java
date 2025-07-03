package com.example.taskflow.converters;

import androidx.room.TypeConverter;
import java.sql.Timestamp;

public class TimestampConverter {
    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long timestampToLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}