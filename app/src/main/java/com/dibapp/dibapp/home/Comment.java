package com.dibapp.dibapp.home;

import java.util.Date;

/*
*  Classe che rappresenta il singolo commento effettuato dallo studente.
* Ogni commento è composto da un messaggio esplicativo dello studente a corredo
* della valutazione effettuata, la data in cui il commento è stato effettuato e
* l'indrizzo email che potrà essere reso visibile o meno a discrezione dello
* stesso studente
* */
public class Comment {

    private String message;
    private String timestamp;
    private String lessonID;
    private String userComment;
    private float rate;

    public Comment(){}

    public Comment(String message, String timestamp, String lessonID, String userComment, float stars){
        this.message = message;
        this.timestamp = timestamp;
        this.lessonID = lessonID;
        this.userComment = userComment;
        this.rate = stars;
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

    public float getRate() {
        return rate;
    }
}
