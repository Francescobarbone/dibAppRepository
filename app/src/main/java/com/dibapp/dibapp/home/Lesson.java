package com.dibapp.dibapp.home;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Lesson extends LessonID{

    private String lessonDate;
    private String courseID;
    private String argument;
    private List<String> usersList;

    public Lesson(){}

    public Lesson(String courseID, String degreeCourse, String argument){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String ct = df.format(Calendar.getInstance().getTime());
        lessonDate = "Lezione del " + ct;
        usersList = new ArrayList<>();
        this.courseID = courseID;
        this.argument = argument;
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
