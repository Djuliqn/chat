package com.chat.server.annotation.password;

import com.chat.server.view.UserView;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserViewPasswordMatchingValidator implements ConstraintValidator<PasswordMatching, UserView> {

    private String password;
    private String confirmPassword;

    private final BeanWrapperImpl beanWrapper;


    @Autowired
    public UserViewPasswordMatchingValidator(final BeanWrapperImpl beanWrapper) {
        this.beanWrapper = beanWrapper;
    }

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        password = constraintAnnotation.password();
        confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(final UserView userClass, final ConstraintValidatorContext context) {

        beanWrapper.setBeanInstance(userClass);

        final Object firstValue = beanWrapper.getPropertyValue(password);
        final Object secondValue = beanWrapper.getPropertyValue(confirmPassword);

        boolean isValid = Objects.nonNull(firstValue) &&
                Objects.nonNull(secondValue) &&
                firstValue.equals(secondValue);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(confirmPassword).addConstraintViolation();
        }

        return isValid;
    }
}