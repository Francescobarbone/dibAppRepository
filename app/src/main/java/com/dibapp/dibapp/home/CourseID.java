package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

//Classe che permette di accedere al Corso tramite il suo ID
public class CourseID {

    @Exclude
    private String courseID;

    public <T extends  CourseID> T withId(@NonNull final String id){
        this.courseID = id;
        return (T) this;
    }

    public String getCourseID() {
        return courseID;
    }
}
