package com.ai.educationapp.POJOs;

public class EventPOJO {

    String id,event_title,event_date,event_description;

    public EventPOJO(String id, String event_title, String event_date, String event_description) {
        this.id = id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_description = event_description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }
}
