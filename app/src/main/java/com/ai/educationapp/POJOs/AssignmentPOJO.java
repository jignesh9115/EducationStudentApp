package com.ai.educationapp.POJOs;

public class AssignmentPOJO {

    String id,due_date,assignment_description,title,teacher_id,standard_id,subject_id,assign_date,status;

    public AssignmentPOJO(String id, String due_date, String assignment_description, String title, String teacher_id, String standard_id, String subject_id, String assign_date, String status) {
        this.id = id;
        this.due_date = due_date;
        this.assignment_description = assignment_description;
        this.title = title;
        this.teacher_id = teacher_id;
        this.standard_id = standard_id;
        this.subject_id = subject_id;
        this.assign_date = assign_date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getAssignment_description() {
        return assignment_description;
    }

    public void setAssignment_description(String assignment_description) {
        this.assignment_description = assignment_description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(String assign_date) {
        this.assign_date = assign_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
