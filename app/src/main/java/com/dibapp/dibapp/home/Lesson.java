package com.dibapp.dibapp.home;

import android.widget.ImageView;

import java.util.List;


/*
 *  Classe che rappresenta la lezione creata dal docente.
 * Ogni lezione è composta dalla data in cui essa viene resa pubblica, il
 * nome dell'argomento scelto dal docente e l'indirizzo univoco del
 * corso di appartenenza e della lezione stessa
 * */

public class Lesson extends LessonID{

    private String lessonDate;
    private String argument;
    private String idCourse;

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
