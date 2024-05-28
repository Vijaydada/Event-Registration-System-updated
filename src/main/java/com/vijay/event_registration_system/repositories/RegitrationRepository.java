package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.Registration;
import com.vijay.event_registration_system.exception.EtAuthException;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;

import java.util.List;

public interface RegitrationRepository {
    List<Registration> findAll(int userId) throws EtResourceNotFoundException;
    Registration findById(int registerId) throws EtResourceNotFoundException;
    Integer createRegistration(int eventId,int userId) throws EtBadRequestException;
    void removeRegistrationById(int registrationId) throws EtResourceNotFoundException;
    void removeRegistrationByEventId(int eventId) throws EtResourceNotFoundException;
}
