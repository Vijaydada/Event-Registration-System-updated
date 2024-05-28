package com.vijay.event_registration_system.domain;

public class Registration {
    private int registerId;
    private int eventId;
    private int userId;

    public Registration(int registerId, int eventId, int userId) {
        this.registerId = registerId;
        this.eventId = eventId;
        this.userId = userId;
    }

    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int registerId) {
        this.registerId = registerId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
