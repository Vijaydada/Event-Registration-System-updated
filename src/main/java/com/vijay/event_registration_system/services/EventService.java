package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.Event;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;

import java.util.List;

public interface EventService {
    List<Event> fetchAllEvents(Integer userId);
    Event fetchEventById(Integer userId,Integer eventId) throws EtResourceNotFoundException;
    Event addEvent(Integer userId,String name, String location)throws EtBadRequestException;
    void updateEvent(Integer userId, Integer eventId, Event event) throws  EtBadRequestException;
    void removeEventWithRegistration(Integer userId, Integer eventId) throws EtResourceNotFoundException;
}
