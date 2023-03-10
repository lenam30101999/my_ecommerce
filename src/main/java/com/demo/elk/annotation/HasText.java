package com.demo.elk.annotation;

import com.demo.elk.validator.HasTextValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasTextValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasText {
    String message() default "Yêu cầu nhập!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
