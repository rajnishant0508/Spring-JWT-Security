package com.demo.jwt.project.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

	private String firstName;
	private String lastName;
	private String email;
	private boolean verified;
	private String emailMessage;
}
