package com.vijay.event_registration_system.resources;

import com.vijay.event_registration_system.domain.Event;
import com.vijay.event_registration_system.services.EventService;
import com.vijay.event_registration_system.services.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventResource {

    @Autowired
    EventService eventService;
    @Autowired
    RegistrationService registrationService;

    @GetMapping("")
    public ResponseEntity<List<Event>> getAllEvents(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<Event> events = eventService.fetchAllEvents(userId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Event> addEvent(HttpServletRequest request, @RequestBody Map<String, String> eventMap) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        int userId = (Integer) request.getAttribute("userId");
        String name = eventMap.get("name");
        String location = eventMap.get("location");
        Event event = eventService.addEvent(userId, name, location);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(HttpServletRequest request, @PathVariable("eventId") Integer eventId) {
        int userId = (Integer) request.getAttribute("userId");
        Event event = eventService.fetchEventById(userId, eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Void> updateEvent(HttpServletRequest request, @PathVariable("eventId") Integer eventId, @RequestBody Map<String, String> eventMap) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        int userId = (Integer) request.getAttribute("userId");
        String name = eventMap.get("name");
        String location = eventMap.get("location");
        Event event = new Event(eventId, name, null, location, userId); // Assuming the Event constructor and date handling
        eventService.updateEvent(userId, eventId, event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> removeEvent(HttpServletRequest request, @PathVariable("eventId") Integer eventId) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        int userId = (Integer) request.getAttribute("userId");
        eventService.removeEventWithRegistration(userId, eventId);
        registrationService.removeRegistrationByEventId(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
