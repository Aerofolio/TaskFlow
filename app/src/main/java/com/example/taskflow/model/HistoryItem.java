package com.example.taskflow.model;

public class HistoryItem {

    private String user;
    private String action;
    private String timestamp;

    public HistoryItem(String user, String action, String timestamp) {
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
