package com.demo.jwt.project.auth.exception;

public class GlobalExceptionHandler extends RuntimeException{

	public GlobalExceptionHandler(String msg) {
		super(msg);
	}
}
