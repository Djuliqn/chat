package com.chat.server.annotation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserViewPasswordMatchingValidator.class)
public @interface PasswordMatching {

    String password();

    String confirmPassword();

    String message() default "The passwords does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
