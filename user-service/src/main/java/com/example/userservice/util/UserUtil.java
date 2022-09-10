package com.example.userservice.util;


import com.example.userservice.domain.Role;
import com.example.userservice.domain.User;
import com.example.userservice.dto.UserTO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.userservice.config.OAuth2AuthorizationServerSecurityConfiguration.PASSWORD_ENCODER;


@UtilityClass
public class UserUtil {


    public static User createNewFromTo(UserTO userTO) {
        return new User(null, userTO.getName(), userTO.getEmail().toLowerCase(), userTO.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setEmail(userTO.getEmail().toLowerCase());
        user.setPassword(userTO.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        return user;
    }
}