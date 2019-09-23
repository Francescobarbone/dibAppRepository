package com.dibapp.dibapp.home;

import java.util.Date;

public class Comment {

    private String message;
    private Date timestamp;
    private String lessonID;

    public Comment(){}

    public Comment(String message, Date timestamp, String lessonID){
        this.message = message;
        this.timestamp = timestamp;
        this.lessonID = lessonID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }
}
