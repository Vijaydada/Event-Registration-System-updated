package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.Event;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;
import com.vijay.event_registration_system.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EventServiceImpl implements EventService{
    public EventServiceImpl() {
        super();
    }
    @Autowired
    EventRepository eventRepository;
    @Override
    public List<Event> fetchAllEvents(Integer userId) {
        return eventRepository.findAll(userId);
    }

    @Override
    public Event fetchEventById(Integer userId, Integer eventId) throws EtResourceNotFoundException {
        return eventRepository.findById(userId,eventId);
    }

    @Override
    public Event addEvent(Integer userId, String name, String location) throws EtBadRequestException {
        int eventId = eventRepository.create(userId,name,location);
        return eventRepository.findById(userId,eventId);
    }

    @Override
    public void updateEvent(Integer userId, Integer eventId, Event event) throws EtBadRequestException {
            eventRepository.updateEvent(userId, eventId, event);
    }

    @Override
    public void removeEventWithRegistration(Integer userId, Integer eventId) throws EtResourceNotFoundException {
        eventRepository.removeById(userId,eventId);
    }
}
