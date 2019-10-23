package com.dibapp.dibapp.home;

/*
* Classe che rappresenta il docente autenticato.
* Ogni docente è identificato univocamente dalla sua email, e dall'ID del corso
* (corrispondente a quello della classe Course)
* */

public class User {

    private String email;
    private String courseId;

    public User(){}

    public User(String email, String courseId) {
        this.email = email;
        this.courseId = courseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
