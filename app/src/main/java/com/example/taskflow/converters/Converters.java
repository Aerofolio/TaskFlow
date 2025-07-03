package com.example.taskflow.converters;

import androidx.room.TypeConverter;

import com.example.taskflow.model.complexTypes.TaskStatusEnum;

public class Converters {

    @TypeConverter
    public static TaskStatusEnum toTaskStatusEnum(int code) {
        return TaskStatusEnum.fromCode(code);
    }

    @TypeConverter
    public static int fromTaskStatusEnum(TaskStatusEnum status) {
        return status.getCode();
    }
}