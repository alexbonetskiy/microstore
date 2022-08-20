package com.example.userservice.service;


import com.example.userservice.domain.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUser(int id) {

        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User save(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }
}
