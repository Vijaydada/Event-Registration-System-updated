package com.vijay.event_registration_system.resources;

import com.vijay.event_registration_system.domain.Registration;
import com.vijay.event_registration_system.services.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationResource {
    @Autowired
    RegistrationService registrationService;
    @PostMapping("")
    public ResponseEntity<Registration> addRegistration(HttpServletRequest request, @RequestBody Map<String, String> registrationMap){
        int userId = (Integer) request.getAttribute("userId");
        int eventId = Integer.parseInt(registrationMap.get("eventId"));
        Registration registration = registrationService.addRegistration(eventId,userId);
        return new ResponseEntity<>(registration, HttpStatus.OK);
    }
}
