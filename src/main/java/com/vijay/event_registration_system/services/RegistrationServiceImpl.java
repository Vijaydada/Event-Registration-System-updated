package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.Registration;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;
import com.vijay.event_registration_system.repositories.RegitrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    RegitrationRepository registrationRepository;

    @Override
    public List<Registration> fetchAllRegistrations(int userId) throws EtResourceNotFoundException {
        try {
            return registrationRepository.findAll(userId);
        } catch (EtResourceNotFoundException e) {
            throw new EtResourceNotFoundException("No registrations found for user ID: " + userId);
        }
    }


    @Override
    public Registration fetchRegistrationById(int registrationId) throws EtResourceNotFoundException {
        try {
            return registrationRepository.findById(registrationId);
        } catch (EtResourceNotFoundException e) {
            throw new EtResourceNotFoundException("Registration not found with ID: " + registrationId);
        }
    }

    @Override
    public Registration addRegistration(int eventId, int userId) throws EtBadRequestException {
        try {
            int registrationId = registrationRepository.createRegistration(eventId, userId);
            return registrationRepository.findById(registrationId);
        } catch (EtBadRequestException e) {
            throw new EtBadRequestException("Unable to create registration for event ID: " + eventId + " and user ID: " + userId);
        }
    }

    @Override
    public void deleteRegistration(int registrationId) throws EtResourceNotFoundException {
        try {
            registrationRepository.removeRegistrationById(registrationId);
        } catch (EtResourceNotFoundException e) {
            throw new EtResourceNotFoundException("Unable to delete registration with ID: " + registrationId);
        }
    }

    @Override
    public void removeRegistrationByEventId(int eventId) throws EtResourceNotFoundException {
        registrationRepository.removeRegistrationByEventId(eventId);
    }
}
