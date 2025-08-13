package com.trio.Elitecar.service;

import com.trio.Elitecar.repository.UserRepository;
import com.trio.Elitecar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public User saveUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return repo.save(user);
//    }
    public User saveUser(User user) {
        // Default role if not provided or invalid
        String role = user.getRole();
        if (role == null || (!role.equals("USER") && !role.equals("ADMIN"))) {
            role = "USER";
        }
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

}
