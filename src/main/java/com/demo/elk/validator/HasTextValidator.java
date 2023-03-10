package com.demo.elk.validator;

import com.demo.elk.annotation.HasText;
import com.demo.elk.exception.ErrorException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HasTextValidator implements ConstraintValidator<HasText, String> {

    @Override
    public void initialize(HasText text) {
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(text)) {
            throw new ErrorException(constraintValidatorContext.getDefaultConstraintMessageTemplate());
        }
        return true;
    }
}
