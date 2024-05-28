package com.vijay.event_registration_system.domain;

import java.util.Date;

public class Event {
    private Integer eventId;
    private String name;
    private Date date;
    private String location;
    private Integer userId;

    public Integer getEventId() {
        return eventId;
    }

    public Event(Integer eventId, String name, Date date, String location, Integer userId) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.location = location;
        this.userId = userId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
