package com.example.taskflow.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private User user;
    private String message;
    private String timestamp;

    public Comment(User user, String message, String timestamp) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
