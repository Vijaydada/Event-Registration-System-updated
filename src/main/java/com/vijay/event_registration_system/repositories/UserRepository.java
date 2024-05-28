package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.User;
import com.vijay.event_registration_system.exception.EtAuthException;



public interface UserRepository {
    Integer create(String username,String email, String role, String password) throws EtAuthException;
    User findByEmailAndPassword(String email, String password) throws EtAuthException;
    Integer getCountByEmail(String email);
    User findById(Integer userId);

}
