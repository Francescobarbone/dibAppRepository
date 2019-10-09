package com.dibapp.dibapp.home;

import com.dibapp.dibapp.QRCode;

import java.util.List;

public class Lesson extends LessonID{

    private String lessonDate;
    private String argument;
    private List<String> usersList;
    private String idCourse;
    private QRCode qrCode;

    public Lesson(){}

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public Lesson(String idCourse, String lessonDate, String argument){
        this.lessonDate = lessonDate;
        this.argument = argument;
        this.idCourse = idCourse;
        this.qrCode = new QRCode(lessonID);
    }

    public void addUser(String email){
        usersList.add(email);
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

}
