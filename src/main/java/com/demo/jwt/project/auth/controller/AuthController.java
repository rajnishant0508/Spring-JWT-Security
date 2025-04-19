package com.demo.jwt.project.auth.controller;

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
import com.demo.jwt.project.auth.exception.GlobalExceptionHandler;
import com.demo.jwt.project.entity.Role;
import com.demo.jwt.project.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/crackit/v1/auth")
public class AuthController {

	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterUserRequest registerUserRequest) {
    	
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
    	 
    	// Validate Email Format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new GlobalExceptionHandler("Invalid email format");
        }
        
        // Validate OTP: Only digits and exactly 6 characters
        if (!otp.matches("^\\d{6}$")) {
        	throw new GlobalExceptionHandler("OTP must be a 6-digit number");
        }
        
    	AuthenticationResponse authResponse = authService.emailOtpVerification(email, otp);
        return  ResponseEntity.ok(authResponse);
    }
    
    @GetMapping("/forgetPassword")
    public ResponseEntity<RegisterResponse> forgetPassword(
    		@RequestParam(name = "email", required = true) String email,
    		@RequestParam(name = "newPassword", required = true) String newPassword) {
    	
    	// Validate Email Format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new GlobalExceptionHandler("Invalid email format");
        }
    	
    	// Validate Password (at least 8 characters, one letter, one digit, one special character)
        if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")) {
        	 throw new GlobalExceptionHandler("Password must be 8-20 characters and include at least one letter, one number, and one special character");
        }
        
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
