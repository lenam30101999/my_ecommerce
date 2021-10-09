package com.demo.elk.validator;

import com.demo.elk.annotation.UsernameConstraint;
import com.demo.elk.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    @Override
    public void initialize(UsernameConstraint username) {
    }

    @Override
    public boolean isValid(String usernameField, ConstraintValidatorContext context) {
        if (usernameField == null || usernameField.equals("") || usernameField.length() < 6) {
            return true;
        }

        String afterLast = StringUtils.substringAfterLast(usernameField, "@");
        if (!Helper.regexPhoneNumber(usernameField)) {
            if (Helper.regexEmail(usernameField)) {
                return Helper.regexEmail(usernameField);
            } else {
                return Helper.regexUsername(usernameField);
            }
        } else {
            return Helper.regexPhoneNumber(usernameField);
        }
    }
}
