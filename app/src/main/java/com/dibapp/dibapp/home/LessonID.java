package com.dibapp.dibapp.home;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class LessonID {

    public String getLessonID() {
        return lessonID;
    }

    @Exclude
    private String lessonID;

    public <T extends LessonID> T withID(@NonNull final String id){
        this.lessonID = id;
        return (T) this;
    }
}
