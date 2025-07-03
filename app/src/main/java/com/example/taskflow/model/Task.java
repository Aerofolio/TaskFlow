package com.example.taskflow.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskflow.model.complexTypes.TaskPriorityEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Timestamp deadline;

    @NonNull
    private TaskPriorityEnum priority;

    public Task(@NonNull String title, @NonNull String description,@NonNull Timestamp deadline, @NonNull TaskPriorityEnum priority ) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public Timestamp getDeadline() {
        return deadline;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public TaskPriorityEnum getPriority() {
        return priority;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setDeadline(@NonNull Timestamp deadline) {
        this.deadline = deadline;
    }
    public void setPriority(@NonNull TaskPriorityEnum priority) {
        this.priority = priority;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return title.equals(task.title) && description.equals(task.description) && deadline.equals(task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, deadline);
    }
}
