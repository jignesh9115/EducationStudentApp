package com.ai.educationapp.POJOs;

public class ExamTypePOJO {

    String exam_type_title,exam_type_id;

    public ExamTypePOJO(String exam_type_title, String exam_type_id) {
        this.exam_type_title = exam_type_title;
        this.exam_type_id = exam_type_id;
    }

    public String getExam_type_title() {
        return exam_type_title;
    }

    public void setExam_type_title(String exam_type_title) {
        this.exam_type_title = exam_type_title;
    }

    public String getExam_type_id() {
        return exam_type_id;
    }

    public void setExam_type_id(String exam_type_id) {
        this.exam_type_id = exam_type_id;
    }
}
