package com.example.taskflow.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskflow.model.complexTypes.TaskPriorityEnum;
import com.example.taskflow.model.complexTypes.TaskStatusEnum;

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

    private Timestamp completedAt;

    @NonNull
    private TaskPriorityEnum priority;

    @NonNull
    private TaskStatusEnum status;

    public Task(@NonNull String title, @NonNull String description, @NonNull Timestamp deadline,
                @NonNull TaskPriorityEnum priority, @NonNull TaskStatusEnum status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
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

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    @NonNull
    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(@NonNull TaskStatusEnum status) {
        this.status = status;
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
