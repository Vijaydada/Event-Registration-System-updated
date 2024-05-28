package com.vijay.event_registration_system.services;

import com.vijay.event_registration_system.domain.User;
import com.vijay.event_registration_system.exception.EtAuthException;
import com.vijay.event_registration_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email!=null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registorUser(String username, String email, String role,String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid Email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count>0)
            throw new EtAuthException("Email is already used");
        Integer userId = userRepository.create(username,email,role,password);
        return userRepository.findById(userId);
    }
}
