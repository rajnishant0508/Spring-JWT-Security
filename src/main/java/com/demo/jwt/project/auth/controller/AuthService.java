package com.demo.jwt.project.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.jwt.project.config.JwtService;
import com.demo.jwt.project.repository.RoleRepository;
import com.demo.jwt.project.repository.UserRepository;
import com.demo.jwt.project.service.EmailService;
import com.demo.jwt.project.user.Role;
import com.demo.jwt.project.user.User;

@Service
public class AuthService {
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RoleRepository roleRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
    	Optional<User> findByEmail = userRepository.findByEmail(registerRequest.getEmail());
    	if(!findByEmail.isPresent()) {
    		List<Role> role = registerRequest.getRole();
    		List<Role> roles = new ArrayList<>();
    		if(role.size() != 0) {
    			roles = roleRepository.findAllById(role.stream().map(p -> p.getId()).collect(Collectors.toList()));
    		}else {
    			return RegisterResponse
    	        		.builder()
    	        		.firstName(registerRequest.getFirstName())
    	                .lastName(registerRequest.getLastName())
    	                .email(registerRequest.getEmail())
    	                .verified(false)
    	                .emailMessage("Incorrect Roles!")
    	                .build();
    		}
    		
    		Random random = new Random();
    		String otp = String.format("%06d", random.nextInt(100000));
    		
    		var user = User.builder()
	                .firstName(registerRequest.getFirstName())
	                .lastName(registerRequest.getLastName())
	                .email(registerRequest.getEmail())
	                .password(passwordEncoder.encode(registerRequest.getPassword()))
	                .emailOtp(otp)
	                .emailOtpValidity(new Date(System.currentTimeMillis() + (59 * 1000)))
	                .role(roles)
	                .verified(false)
	                .build();
	       
    		userRepository.save(user);
    		
    		String emailStatus = emailService.sendEmail(
            		registerRequest.getEmail(), 
            		"Email Verification Code", 
            		"Your Verification Code is : " + otp
            		);
	        
	        return RegisterResponse
	        		.builder()
	        		.firstName(registerRequest.getFirstName())
	                .lastName(registerRequest.getLastName())
	                .email(registerRequest.getEmail())
	                .verified(false)
	                .emailMessage(emailStatus)
	                .build();
    	}else {
    		return RegisterResponse
	        		.builder()
	        		.firstName(registerRequest.getFirstName())
	                .lastName(registerRequest.getLastName())
	                .email(registerRequest.getEmail())
	                .verified(false)
	                .emailMessage("User is not verfied or User already exist!")
	                .build();
    	}
    }
    
    public AuthenticationResponse emailOtpVerification(String email, String otp) {
		Optional<User> findByEmail = userRepository.findByEmail(email);
		if(findByEmail.isPresent()) {
			User user = findByEmail.get();
			if(user.getEmailOtp().equals(otp)) {
				if(new Date(System.currentTimeMillis()).before(user.getEmailOtpValidity())) {
					user.setVerified(true);
					userRepository.save(user);
					String jwtToken = jwtService.generateToken(user);
					return AuthenticationResponse
							.builder()
							.accessToken(jwtToken)
							.message("token generated successfully")
							.build();
				}else {
					return AuthenticationResponse
							.builder()
							.accessToken(null)
							.message("otp time expired")
							.build();
				}
			}else {
				return AuthenticationResponse
						.builder()
						.accessToken(null)
						.message("not valid otp")
						.build();
			}
		}else {
			return AuthenticationResponse
					.builder()
					.accessToken(null)
					.message("Invalid User")
					.build();
		}
	}

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //FirstStep
            //We need to validate our request (validate whether password & username is correct)
            //Verify whether user present in the database
            //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
            //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
            //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
            //Verify whether user present in db
            //generateToken
            //Return the token
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if(user.isVerified()) {
        	 String jwtToken = jwtService.generateToken(user);
             return AuthenticationResponse
             		.builder()
             		.accessToken(jwtToken)
             		.message("token generated successfully")
             		.build();
        }else {
        	return AuthenticationResponse
             		.builder()
             		.accessToken(null)
             		.message("User not verified")
             		.build();
        }
       

    }

	public RegisterResponse forgetPassword(String email, String newPassword) {
		Optional<User> user = userRepository.findByEmail(email);
    	if(user.isPresent()) {
    		Random random = new Random();
    		String otp = String.format("%06d", random.nextInt(100000));
       
    		var setUser = User.builder()
    				.id(user.get().getId())
	                .firstName(user.get().getFirstName())
	                .lastName(user.get().getLastName())
	                .email(user.get().getEmail())
	                .password(passwordEncoder.encode(newPassword))
	                .emailOtp(otp)
	                .emailOtpValidity(new Date(System.currentTimeMillis() + (45 * 1000)))
	                .verified(false)
	                .role(user.get().getRole())
	                .build();
	       
    		userRepository.save(setUser);
	      
    		String emailStatus = emailService.sendEmail(
            		email, 
            		"Email Verification Code", 
            		"Your Verification Code is : " + otp
            		);
	        
	        return RegisterResponse
	        		.builder()
	        		.firstName(user.get().getFirstName())
	                .lastName(user.get().getLastName())
	                .email(user.get().getEmail())
	                .verified(false)
	                .emailMessage(emailStatus)
	                .build();
    	}else {
    		return RegisterResponse
	        		.builder()
	        		.firstName(user.get().getFirstName())
	                .lastName(user.get().getLastName())
	                .email(user.get().getEmail())
	                .verified(false)
	                .emailMessage("user does not exist")
	                .build();
    	}
	}

	public User getUserDeatils(String email) {
		Optional<User> findByEmail = userRepository.findByEmail(email);
		if(findByEmail.isPresent()) {
			User user = findByEmail.get();
			return user;
		}else {
			User user = new User();
			return user;
		}
	}
}