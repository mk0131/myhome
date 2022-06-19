package com.springboot.myrest.service;

import com.springboot.myrest.model.Role;
import com.springboot.myrest.model.User;
import com.springboot.myrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword()); // 비밀번호 암호화
        user.setPassword(encodedPassword);

        user.setEnabled(true);

        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
