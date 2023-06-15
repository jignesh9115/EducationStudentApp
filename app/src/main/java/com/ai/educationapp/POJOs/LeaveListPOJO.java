package com.ai.educationapp.POJOs;

public class LeaveListPOJO {

    String id,leave_desc,leave_type,status,start_date,end_date;

    public LeaveListPOJO(String id, String leave_desc, String leave_type, String status, String start_date, String end_date) {
        this.id = id;
        this.leave_desc = leave_desc;
        this.leave_type = leave_type;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeave_desc() {
        return leave_desc;
    }

    public void setLeave_desc(String leave_desc) {
        this.leave_desc = leave_desc;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
