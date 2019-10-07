package com.dibapp.dibapp.home;

import java.util.ArrayList;
import java.util.List;

public class Course extends CourseID{

    private String courseName, courseTime;
    private List<Lesson> lessonList = new ArrayList<>();

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


    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void addLesson(Lesson lesson){
        this.lessonList.add(lesson);
    }
}
