package com.cargotaxi.mvc.validation;

import com.cargotaxi.mvc.controller.form.NewUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements
        ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches passwordMatches) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext
            constraintValidatorContext) {
        NewUserDTO newUserDTO = (NewUserDTO) o;
        return newUserDTO.getPassword().equals(newUserDTO.getMatchingPassword());
    }
}
