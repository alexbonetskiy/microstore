package com.example.userservice.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    @Override
    public String toString() {
        return String.valueOf(user.id());
    }
}