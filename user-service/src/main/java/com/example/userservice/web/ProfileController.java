package com.example.userservice.web;


import com.example.userservice.domain.AuthUser;
import com.example.userservice.domain.User;
import com.example.userservice.dto.UserTO;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.example.userservice.util.ValidationUtil.assureIdConsistent;
import static com.example.userservice.util.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController extends AbstractUserController {

    public static final String REST_URL = "/profile";

    @Autowired
    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, passwordEncoder);
    }

    @GetMapping
    public ResponseEntity<User> getUser(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getUser id={}", authUser.id());
        return ResponseEntity.ok(authUser.getUser());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody @Valid UserTO userTO, @AuthenticationPrincipal AuthUser authUser) {
        assureIdConsistent(userTO, authUser.id());
        User user = authUser.getUser();
        prepareAndSave(UserUtil.updateFromTo(user, userTO));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody UserTO userTO) {
        log.info("create {}", userTO);
        checkNew(userTO);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTO));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }



}
