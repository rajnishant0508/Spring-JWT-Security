package com.demo.jwt.project.auth.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleId {
    String message() default "RoleId must be either 1 or 3";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
