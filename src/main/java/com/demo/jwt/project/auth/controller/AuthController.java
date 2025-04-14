package com.demo.jwt.project.auth.controller;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.jwt.project.auth.dto.RegisterUserRequest;
import com.demo.jwt.project.auth.dto.UserRolesRequest;
import com.demo.jwt.project.entity.Role;
import com.demo.jwt.project.entity.User;

@RestController
@RequestMapping("/crackit/v1/auth")
public class AuthController {

	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterUserRequest registerUserRequest) {
    	
    	List<UserRolesRequest> userRequestRoles = registerUserRequest.getRole();
    	List<Role> roles = new ArrayList<>();
    	for(UserRolesRequest role : userRequestRoles) {
    		Role newRole = new Role();
    		newRole.setId(role.getId());
    		newRole.setRoleType(role.getRoleType());
    		roles.add(newRole);
    	}
    	
    	RegisterRequest registerRequest = RegisterRequest.builder()
    			.email(registerUserRequest.getEmail())
    			.firstName(registerUserRequest.getFirstName())
    			.lastName(registerUserRequest.getLastName())
    			.password(registerUserRequest.getPassword())
    			.role(roles)
    			.build();
    	RegisterResponse authResponse = authService.register(registerRequest);
        return  ResponseEntity.ok(authResponse);
    }
    
    @GetMapping("/otpverification")
    public ResponseEntity<AuthenticationResponse> emailOtpVerification(
    		@RequestParam(name = "email", required = true) String email,
    		@RequestParam(name = "otp", required = true) String otp) {
    	AuthenticationResponse authResponse = authService.emailOtpVerification(email, otp);
        return  ResponseEntity.ok(authResponse);
    }
    
    @GetMapping("/forgetPassword")
    public ResponseEntity<RegisterResponse> forgetPassword(
    		@RequestParam(name = "email", required = true) String email,
    		@RequestParam(name = "newPassword", required = true) String newPassword) {
    	return new ResponseEntity<RegisterResponse>(authService.forgetPassword(email, newPassword), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
       return ResponseEntity.ok(authService.authenticate(request));
    }
    
    @GetMapping("/user")
    public ResponseEntity<User> getUserDeatils(
    		@RequestParam(name = "email", required = true) String email) {
    	return new ResponseEntity<User>(authService.getUserDeatils(email), HttpStatus.OK);
    }
}
