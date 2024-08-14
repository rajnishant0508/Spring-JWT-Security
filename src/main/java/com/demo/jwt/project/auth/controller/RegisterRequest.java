package com.demo.jwt.project.auth.controller;

import java.util.List;

import com.demo.jwt.project.user.Role;

//import com.demo.jwt.project.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> role;
}
