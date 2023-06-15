package com.ai.educationapp.POJOs;

public class TeacherListPOJO {

    String subject_title,teacher_name;

    public TeacherListPOJO(String subject_title, String teacher_name) {
        this.subject_title = subject_title;
        this.teacher_name = teacher_name;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
}

