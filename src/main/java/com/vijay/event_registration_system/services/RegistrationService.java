package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.Registration;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;

import java.util.List;

public interface RegistrationService {
    List<Registration> fetchAllRegistrations(int userId) throws EtResourceNotFoundException;

    Registration fetchRegistrationById(int registrationId) throws EtResourceNotFoundException;
    Registration addRegistration(int eventId, int userId) throws EtBadRequestException;
    void deleteRegistration(int registrationId) throws EtResourceNotFoundException;
    void removeRegistrationByEventId(int eventId) throws EtResourceNotFoundException;
}
