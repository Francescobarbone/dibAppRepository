package com.dibapp.dibapp.home;

import java.util.List;

public class Lesson extends LessonID{

    private String lessonDate;
    private String argument;
    private List<String> usersList;

    public Lesson(){}

    public Lesson(String lessonDate, String argument){
        this.lessonDate = lessonDate;
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
