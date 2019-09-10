package com.dibapp.dibapp.home;

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
