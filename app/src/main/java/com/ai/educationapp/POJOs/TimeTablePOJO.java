package com.ai.educationapp.POJOs;

public class TimeTablePOJO {

    String subject_title,teacher_name,start_time,end_time,day_id;

    public TimeTablePOJO(String subject_title, String teacher_name, String start_time, String end_time, String day_id) {
        this.subject_title = subject_title;
        this.teacher_name = teacher_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.day_id = day_id;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }
}
