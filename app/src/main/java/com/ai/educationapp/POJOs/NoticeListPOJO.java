package com.ai.educationapp.POJOs;

public class NoticeListPOJO {

    String notice_id,notice_description,notice_type,notice_status,on_date,notice_date;

    public NoticeListPOJO(String notice_id, String notice_description, String notice_type, String notice_status, String on_date, String notice_date) {
        this.notice_id = notice_id;
        this.notice_description = notice_description;
        this.notice_type = notice_type;
        this.notice_status = notice_status;
        this.on_date = on_date;
        this.notice_date = notice_date;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getNotice_description() {
        return notice_description;
    }

    public void setNotice_description(String notice_description) {
        this.notice_description = notice_description;
    }

    public String getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public String getNotice_status() {
        return notice_status;
    }

    public void setNotice_status(String notice_status) {
        this.notice_status = notice_status;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }
}
