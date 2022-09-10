package com.example.userservice.web;

import com.example.userservice.domain.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.UserUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUserController {

    protected final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userRepository.deleteExisted(id);
    }

    protected User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }
}
