package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.Event;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;

import java.util.List;

public interface EventRepository {
    List<Event> findAll(Integer userId) throws EtResourceNotFoundException;
    Event findById(Integer userId,Integer eventId)throws EtResourceNotFoundException;
    Integer create(Integer userId,String name,String location) throws EtBadRequestException;
    void updateEvent(Integer userId, Integer eventId, Event event) throws EtResourceNotFoundException;
    void removeById (Integer userId,Integer eventId);
}
