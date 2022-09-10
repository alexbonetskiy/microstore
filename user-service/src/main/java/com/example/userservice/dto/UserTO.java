package com.example.userservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTO extends BaseTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;


    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    String name;

    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 256)
    String password;

}
