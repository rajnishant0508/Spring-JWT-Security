package com.demo.jwt.project.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.jwt.project.user.User;

@RestController
@RequestMapping("/crackit/v1/auth")
public class AuthController {

	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest registerRequest) {
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
