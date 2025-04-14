package com.demo.jwt.project.auth.dto;

import java.util.List;

import com.demo.jwt.project.auth.controller.RegisterRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String password;
	    private List<UserRolesRequest> role;
}
