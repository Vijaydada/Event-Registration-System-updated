package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.User;
import com.vijay.event_registration_system.exception.EtAuthException;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;
    User registorUser(String username, String email,String role,String password) throws EtAuthException;
}
