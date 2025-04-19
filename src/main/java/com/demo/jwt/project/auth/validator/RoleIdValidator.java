package com.demo.jwt.project.auth.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleIdValidator implements ConstraintValidator<ValidRoleId, Integer> {

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        return id != null && (id == 1 || id == 3);
    }
}

