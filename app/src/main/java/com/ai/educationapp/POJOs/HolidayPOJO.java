package com.ai.educationapp.POJOs;

public class HolidayPOJO {

    String id,holiday_title,holiday_date_start,holiday_date_end;

    public HolidayPOJO(String id, String holiday_title, String holiday_date_start, String holiday_date_end) {
        this.id = id;
        this.holiday_title = holiday_title;
        this.holiday_date_start = holiday_date_start;
        this.holiday_date_end = holiday_date_end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoliday_title() {
        return holiday_title;
    }

    public void setHoliday_title(String holiday_title) {
        this.holiday_title = holiday_title;
    }

    public String getHoliday_date_start() {
        return holiday_date_start;
    }

    public void setHoliday_date_start(String holiday_date_start) {
        this.holiday_date_start = holiday_date_start;
    }

    public String getHoliday_date_end() {
        return holiday_date_end;
    }

    public void setHoliday_date_end(String holiday_date_end) {
        this.holiday_date_end = holiday_date_end;
    }
}
