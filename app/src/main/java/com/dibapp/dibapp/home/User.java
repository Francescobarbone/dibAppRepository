package com.dibapp.dibapp.home;

public class User {

    private String email;
    private static String courseId;

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

    @Override
    public String toString() {
        return "User{" + "email='" + email + '\'' + ", courseId='" + courseId + '\'' + '}';
    }


}
