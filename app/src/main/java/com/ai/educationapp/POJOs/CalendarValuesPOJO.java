package com.ai.educationapp.POJOs;

public class CalendarValuesPOJO {

    String day,status,holiday,date;

    public CalendarValuesPOJO(String day, String status, String holiday, String date) {
        this.day = day;
        this.status = status;
        this.holiday = holiday;
        this.date = date;
    }

    public CalendarValuesPOJO(String day, String status, String holiday) {
        this.day = day;
        this.status = status;
        this.holiday = holiday;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
