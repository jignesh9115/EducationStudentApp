package com.ai.educationapp.POJOs;

public class StudentFeeDetailPOJO {

    String id,student_id,title,fee_type_id,due_date,paid_date,comments,base_amount,receipt_url;

    public StudentFeeDetailPOJO(String id, String student_id, String title, String fee_type_id, String due_date, String paid_date, String comments, String base_amount, String receipt_url) {
        this.id = id;
        this.student_id = student_id;
        this.title = title;
        this.fee_type_id = fee_type_id;
        this.due_date = due_date;
        this.paid_date = paid_date;
        this.comments = comments;
        this.base_amount = base_amount;
        this.receipt_url = receipt_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFee_type_id() {
        return fee_type_id;
    }

    public void setFee_type_id(String fee_type_id) {
        this.fee_type_id = fee_type_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(String paid_date) {
        this.paid_date = paid_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBase_amount() {
        return base_amount;
    }

    public void setBase_amount(String base_amount) {
        this.base_amount = base_amount;
    }

    public String getReceipt_url() {
        return receipt_url;
    }

    public void setReceipt_url(String receipt_url) {
        this.receipt_url = receipt_url;
    }
}
