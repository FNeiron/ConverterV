package org.example.service;

import org.example.domain.Role;
import org.example.domain.User;
import org.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    UserRepo userRepo;

    public boolean register(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb != null)
            return false;
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return true;
    }
}
