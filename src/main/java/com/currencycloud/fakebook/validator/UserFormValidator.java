package com.currencycloud.fakebook.validator;

import com.currencycloud.fakebook.entity.User;
import com.currencycloud.fakebook.model.UserForm;
import com.currencycloud.fakebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
    @Autowired
    private UserService userService;


    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {
        UserForm user = (UserForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty");

        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Username.length");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Username.duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Password.length");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "PasswordConfirm.mismatch");
        }
    }
}
