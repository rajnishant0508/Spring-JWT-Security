package com.demo.jwt.project.auth.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
	
	@NotEmpty(message = "FirstName must not be empty")
	private String firstName;
	
	@NotEmpty(message = "LastName must not be empty")
	private String lastName;
	
	@NotEmpty(message = "Email must not be empty")
	@Schema(example = "john.doe@example.com")
	@Pattern(
		    regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
		    message = "Invalid email format"
		)
	private String email;
	
	@NotEmpty(message = "Password must not be empty")
	@Schema(example = "StrongP@ss123")
	@Pattern(
	        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
	        message = "Password must be 8-20 characters and include at least one letter, one number, and one special character"
	    )
	private String password;
	
	@NotEmpty(message = "Role must not be empty")
	@Valid
	private List<UserRolesRequest> role;
}
