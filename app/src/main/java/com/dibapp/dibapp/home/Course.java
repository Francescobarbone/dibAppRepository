package com.dibapp.dibapp.home;

import java.util.ArrayList;
import java.util.List;


/*
 *  Classe che rappresenta il corso tenuto dal docente.
 * Ogni corso è rappresentato univocamente dal suo ID (corrispondente a quello del docente),
 * il nome del corso e le date in cui esso si svolge
 * */

public class Course extends CourseID{

    private String courseName, courseTime;

    public Course(){}

    public Course(String courseName, String courseTime){
        this.courseName = courseName;
        this.courseTime = courseTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }
}
