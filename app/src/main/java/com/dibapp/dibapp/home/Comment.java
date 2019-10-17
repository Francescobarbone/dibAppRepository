package com.dibapp.dibapp.home;

import java.util.Date;

public class Comment {

    private String message;
    private String timestamp;
    private String lessonID;
    private String userComment;

    public Comment(){}

    public Comment(String message, String timestamp, String lessonID, String userComment){
        this.message = message;
        this.timestamp = timestamp;
        this.lessonID = lessonID;
        this.userComment = userComment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getUserComment() {
        return userComment;
    }
}
