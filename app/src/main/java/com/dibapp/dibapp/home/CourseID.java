package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class CourseID {

    @Exclude
    public String courseID;

    public <T extends  CourseID> T withId(@NonNull final String id){
        this.courseID = id;
        return (T) this;
    }
}
