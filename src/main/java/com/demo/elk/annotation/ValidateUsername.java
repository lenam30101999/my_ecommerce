package com.demo.elk.annotation;

import com.demo.elk.validator.ValidatedUsername;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatedUsername.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUsername {
  String message() default "Sai định dạng tài khoản !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
