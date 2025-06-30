package com.example.taskflow.model;

import androidx.room.Embedded;
import androidx.room.Relation;
public class CommentWithUser {

    @Embedded
    private Comment comment;

    @Relation(
            parentColumn = "userId",
            entityColumn = "id"
    )
    private User user;

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
