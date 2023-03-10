package com.demo.elk.validator;

import com.demo.elk.annotation.ValidateUsername;
import com.demo.elk.exception.ErrorException;
import com.demo.elk.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidatedUsername implements ConstraintValidator<ValidateUsername, String> {

    @Override
    public void initialize(ValidateUsername username) {
    }

    @Override
    public boolean isValid(String usernameField, ConstraintValidatorContext context) {
        boolean isValidated = false;
        if (Helper.regexPhoneNumber(usernameField)) {
            isValidated = true;
        } else if (Helper.regexEmail(usernameField)) {
            isValidated = true;
        } else if (Helper.regexUsername(usernameField)) {
            isValidated = true;
        }

        if (!isValidated) {
            throw new ErrorException(context.getDefaultConstraintMessageTemplate());
        }

        return true;
    }
}
