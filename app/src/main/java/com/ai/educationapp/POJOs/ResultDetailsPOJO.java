package com.ai.educationapp.POJOs;

public class ResultDetailsPOJO {

    String exam_title,exam_standard,exam_date,subject_title,exam_total_marks,exam_type_title,exam_id,mark_obtain;

    public ResultDetailsPOJO(String exam_title, String exam_standard, String exam_date, String subject_title, String exam_total_marks, String exam_type_title, String exam_id, String mark_obtain) {
        this.exam_title = exam_title;
        this.exam_standard = exam_standard;
        this.exam_date = exam_date;
        this.subject_title = subject_title;
        this.exam_total_marks = exam_total_marks;
        this.exam_type_title = exam_type_title;
        this.exam_id = exam_id;
        this.mark_obtain = mark_obtain;
    }

    public String getExam_title() {
        return exam_title;
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
    }

    public String getExam_standard() {
        return exam_standard;
    }

    public void setExam_standard(String exam_standard) {
        this.exam_standard = exam_standard;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public String getExam_total_marks() {
        return exam_total_marks;
    }

    public void setExam_total_marks(String exam_total_marks) {
        this.exam_total_marks = exam_total_marks;
    }

    public String getExam_type_title() {
        return exam_type_title;
    }

    public void setExam_type_title(String exam_type_title) {
        this.exam_type_title = exam_type_title;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getMark_obtain() {
        return mark_obtain;
    }

    public void setMark_obtain(String mark_obtain) {
        this.mark_obtain = mark_obtain;
    }
}
